from typing import List, Set, Tuple

from bll.type_node_cache import TypeNodeCache
from bll.type_node_models import InstantiatedTypeNode, TypeNode, UnionTypeNode



BUILTIN_PRIMITIVE_NS_NAMES = {
    ('builtins', 'any'),
    ('builtins', 'BaseException'),
    ('builtins', 'bool'),
    ('builtins', 'bytearray'),
    ('builtins', 'bytes'),
    ('builtins', 'complex'),
    ('builtins', 'float'),
    ('builtins', 'int'),
    ('builtins', 'memoryview'),
    ('builtins', 'module'),
    ('builtins', 'NoneType'),
    ('builtins', 'object'),
    ('builtins', 'str'),
    ('builtins', 'traceback'),
    ('builtins', 'type'),
    ('datetime', 'time'),
    ('datetime', 'datetime'),
    ('decimal', 'Decimal'),
}

BUILTIN_GENERIC_PRIMITIVE_1ARG_NS_NAMES = {
    ('builtins', 'list'),
    ('builtins', 'set'),
    ('builtins', 'frozenset')
}

BUILTIN_GENERIC_PRIMITIVE_2ARG_NS_NAMES = {
    ('builtins', 'dict')
}



def populate(py_version_id: int, type_node_cache: TypeNodeCache) -> TypeNodeCache:
    type_node_cache = populate_simple_unions(py_version_id, type_node_cache)
    type_node_cache = populate_simple_instantiations(py_version_id, type_node_cache)
    type_node_cache = populate_union_instantiations(py_version_id, type_node_cache)

    return type_node_cache



def populate_simple_unions(py_version_id: int, 
    type_node_cache: TypeNodeCache) -> TypeNodeCache:

    core_types: Set[Tuple[int, TypeNode]] = set()
    pending_inserts: Set[UnionTypeNode] = set()

    for ns, name in BUILTIN_PRIMITIVE_NS_NAMES:
        tups = type_node_cache.find(kind='base', namespace=ns, name=name)
        if len(tups) == 0:
            print("create ns: " + ns + " name: " + name)
        for id1, _ in tups:
            for (id2, _) in core_types_except([id1], core_types):
                union_node = UnionTypeNode(obj=None, py_version_id=py_version_id, member_type_node_ids={id1, id2}, )
                pending_inserts.add(union_node)

                for (id3, _) in core_types_except([id1, id2], core_types):
                    union_node = UnionTypeNode(obj=None, py_version_id=py_version_id, member_type_node_ids={id1, id2, id3})
                    pending_inserts.add(union_node)

                    for (id4, _) in core_types_except([id1, id2, id3], core_types):
                        union_node = UnionTypeNode(obj=None, py_version_id=py_version_id, member_type_node_ids={id1, id2, id3, id4})
                        pending_inserts.add(union_node)

                        for (id5, _) in core_types_except([id1, id2, id3, id4], core_types):
                            union_node = UnionTypeNode(obj=None, py_version_id=py_version_id, member_type_node_ids={id1, id2, id3, id4, id5})
                            pending_inserts.add(union_node)

    for pending_insert in pending_inserts:
        type_node_cache.register(pending_insert)

    return type_node_cache



def populate_simple_instantiations(py_version_id: int, 
    type_node_cache: TypeNodeCache) -> TypeNodeCache:

    core_types: Set[int] = set()
    pending_inserts: Set[InstantiatedTypeNode] = set()

    for ns, name in BUILTIN_PRIMITIVE_NS_NAMES:
        tups: List[Tuple[int, TypeNode]] = type_node_cache.find(kind='base', namespace=ns, name=name)    
        for tn_id, _ in tups:
            core_types.add(tn_id)

    for ns, name in BUILTIN_GENERIC_PRIMITIVE_1ARG_NS_NAMES:
        for (gen_1arg_id, _) in type_node_cache.find(kind='base', namespace=ns, name=name):
            for t_ns, t_name in BUILTIN_PRIMITIVE_NS_NAMES:
                for (t_id, _) in type_node_cache.find(kind='base', namespace=t_ns, name=t_name):
                    inst_node = InstantiatedTypeNode(None, py_version_id, gen_1arg_id, [t_id])
                    pending_inserts.add(inst_node)

    for ns, name in BUILTIN_GENERIC_PRIMITIVE_2ARG_NS_NAMES:
        for (gen_2arg_id, _) in type_node_cache.find(kind='base', namespace=ns, name=name):
            for t1_id in core_types:
                for t2_id in core_types:
                    pending_inserts.add(InstantiatedTypeNode(None, py_version_id, gen_2arg_id, [t1_id, t2_id]))

    for pending_insert in pending_inserts:
        type_node_cache.register(pending_insert)

    return type_node_cache



def populate_union_instantiations(py_version_id: int, 
    type_node_cache: TypeNodeCache) -> TypeNodeCache:

    all_types: Set[int] = set()
    union_types: Set[int] = set()
    pending_inserts: Set[InstantiatedTypeNode] = set()

    for ns, name in BUILTIN_PRIMITIVE_NS_NAMES:
        tups: List[Tuple[int, TypeNode]] = type_node_cache.find(kind='base', namespace=ns, name=name)    
        for builtin_id, _ in tups:
            all_types.add(builtin_id)

    union_tups: List[Tuple[int, TypeNode]] = type_node_cache.find(node_type = type(UnionTypeNode))
    for union_tup in union_tups:
        for u_id, _ in union_tup:
            union_types.add(u_id)
            all_types.add(u_id)

    for ns, name in BUILTIN_GENERIC_PRIMITIVE_1ARG_NS_NAMES:
        gen_1arg_tups = type_node_cache.find(kind='base', namespace=ns, name=name)
        for (base_1arg_id, _) in gen_1arg_tups:
            for t_id in union_types:
                inst_node = InstantiatedTypeNode(None, py_version_id, base_1arg_id, [t_id])
                pending_inserts.add(inst_node)

    for ns, name in BUILTIN_GENERIC_PRIMITIVE_2ARG_NS_NAMES:
        tups = type_node_cache.find(kind='base', namespace=ns, name=name)
        for (gen_2arg_id, _) in tups:
            for t1_id, _ in all_types:
                for t2_id, _ in all_types:
                    inst_node = InstantiatedTypeNode(None, py_version_id, gen_2arg_id, [t1_id, t2_id])
                    pending_inserts.add(inst_node)

    for pending_insert in pending_inserts:
        type_node_cache.register(pending_insert)

    return type_node_cache



def core_types_except(excluded_ids: List[int], core_types: Set[Tuple[int, TypeNode]]) -> Set[Tuple[int, TypeNode]]:
    all_except: Set[Tuple[int, TypeNode]] = set()

    for (tn_id, node) in core_types:
        if tn_id not in excluded_ids:
            all_except.add((tn_id, node))
    
    return all_except
