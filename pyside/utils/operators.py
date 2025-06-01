"""
Utilities for handling package version operators and comparisons.

This module provides functions for normalizing and working with
package version operators (==, !=, >=, etc.) used in dependency specifications.
"""

from typing import Literal

OperatorEnum = Literal['EQ', 'NE', 'GE', 'LE', 'GT', 'LT', 'COMPATIBLE', 'EXACT']

def normalize_operator(op_string: str) -> OperatorEnum:
    """
    Convert packaging library operators to standardized enum values.
    
    Args:
        op_string: A string operator from packaging library (==, !=, etc.)
        
    Returns:
        Normalized operator enum value ('EQ', 'NE', etc.)
        
    Raises:
        ValueError: If the operator is not recognized
    """
    if op_string == '==':
        return 'EQ'
    elif op_string == '!=':
        return 'NE'
    elif op_string == '>=':
        return 'GE'
    elif op_string == '<=':
        return 'LE'
    elif op_string == '>':
        return 'GT'
    elif op_string == '<':
        return 'LT'
    elif op_string == '~=':
        return 'COMPATIBLE'
    elif op_string == '===':
        return 'EXACT'
    else:
        raise ValueError(f"Unknown operator: {op_string!r}")

def operator_to_symbol(op_enum: OperatorEnum) -> str:
    """
    Convert operator enum back to symbol representation.
    
    Args:
        op_enum: Normalized operator enum value ('EQ', 'NE', etc.)
        
    Returns:
        String operator symbol (==, !=, etc.)
        
    Raises:
        ValueError: If the operator enum is not recognized
    """
    if op_enum == 'EQ':
        return '=='
    elif op_enum == 'NE':
        return '!='
    elif op_enum == 'GE':
        return '>='
    elif op_enum == 'LE':
        return '<='
    elif op_enum == 'GT':
        return '>'
    elif op_enum == 'LT':
        return '<'
    elif op_enum == 'COMPATIBLE':
        return '~='
    elif op_enum == 'EXACT':
        return '==='
    else:
        raise ValueError(f"Unknown operator enum: {op_enum!r}") 


def operator_to_sql_comparison(op: str) -> str:
    return {
        'LT': '<',
        'LE': '<=',
        'GT': '>',
        'GE': '>='
    }[op]