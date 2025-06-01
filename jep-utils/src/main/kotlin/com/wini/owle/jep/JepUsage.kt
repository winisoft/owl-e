package com.wini.owle.data

import jep.SharedInterpreter


fun comparePythonVersions(): Boolean {
    SharedInterpreter().use { interp ->
        interp.eval("from packaging.version import Version")
        interp.set("v1", "1.2.3a1")
        interp.set("v2", "1.2.3")
        return interp.getValue("Version(v1) < Version(v2)") as Boolean
    }
}

fun parseRequires(requiresDist: List<String>) {
    SharedInterpreter().use { interp ->
        interp.exec("""
                from packaging.requirements import Requirement
                
                def parse_requires_dist(requirement_strs):
                    results = []
                    for req_str in requirement_strs:
                        try:
                            req = Requirement(req_str)
                            name = req.name
                            version_specifiers = str(req.specifier) if req.specifier else ""
                            extra = None
                            if req.marker:
                                for marker in req.marker._markers:
                                    if isinstance(marker, tuple) and marker[0].value == "extra":
                                        extra = marker[2].value
                                        break
                            results.append({
                                "name": name,
                                "version_specifiers": version_specifiers.split(',') if version_specifiers else [],
                                "extra": extra,
                            })
                        except Exception as e:
                            results.append({
                                "name": None,
                                "version_specifiers": [],
                                "extra": None,
                                "error": str(e),
                            })
                    return results
            """)

        interp.set("input_list", requiresDist)
        interp.exec("parsed = parse_requires_dist(input_list)")

        val parsed = interp.getValue("parsed") as MutableList<MutableMap<String?, Any?>?>

        for (entry in parsed) {
            println(entry)
        }
    }
}

fun parseMarkers() {
    SharedInterpreter().use { interp ->
        interp.exec(
            """
from packaging.requirements import Requirement
from packaging.markers import Marker
import ast
import operator as op

# Supported marker operations
OPERATORS = {
    "==": op.eq,
    "!=": op.ne,
    "<": op.lt,
    "<=": op.le,
    ">": op.gt,
    ">=": op.ge,
    "in": lambda a, b: a in b,
    "not in": lambda a, b: a not in b,
}

def parse_marker_expression(marker_str: str) -> ast.AST:
    ""${'"'}
    Parses a marker string into an AST using Python's `ast` module.
    This does not execute the logic but gives a structure you can walk.
    ""${'"'}
    # E.g. marker_str = 'extra == "dev" and python_version < "3.11"'
    expr = ast.parse(marker_str, mode='eval')
    return expr.body

def extract_conditions(expr: ast.expr) -> list[tuple[str, str, str]]:
    ""${'"'}
    Recursively walks the AST and extracts all comparison conditions.
    Returns a list of (lhs, op, rhs) triplets.
    ""${'"'}
    if isinstance(expr, ast.BoolOp):
        return sum((extract_conditions(value) for value in expr.values), [])
    elif isinstance(expr, ast.Compare):
        left = expr.left.s if isinstance(expr.left, ast.Str) else expr.left.id
        right = expr.comparators[0].s if isinstance(expr.comparators[0], ast.Str) else expr.comparators[0].id
        op_str = {
            ast.Eq: "==",
            ast.NotEq: "!=",
            ast.Lt: "<",
            ast.LtE: "<=",
            ast.Gt: ">",
            ast.GtE: ">=",
            ast.In: "in",
            ast.NotIn: "not in",
        }[type(expr.ops[0])]
        return [(left, op_str, right)]
    elif isinstance(expr, ast.UnaryOp) and isinstance(expr.op, ast.Not):
        return [("NOT", *extract_conditions(expr.operand)[0])]
    else:
        raise NotImplementedError(f"Unsupported marker expression node: {expr}")

def parse_environment_markers(req_string: str):
    req = Requirement(req_string)
    if req.marker is None:
        return []
    ast_expr = parse_marker_expression(str(req.marker))
    return extract_conditions(ast_expr)""")
    }
}