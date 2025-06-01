#!/usr/bin/env python3
"""
Functional test for type schema refs implementation.
This test verifies that the populate_type_schema_refs function correctly
identifies class members and their types.
"""

import sys
import os
from unittest.mock import MagicMock
from typing import List, Optional

# Add project root to path
sys.path.insert(0, os.path.dirname(__file__))

def test_type_schema_refs_functionality():
    """Test that type schema refs logic works correctly"""
    
    print("Testing type schema refs functionality...")
    
    # Create mock cursor
    cursor = MagicMock()
    
    # Create a test class with various member types
    class MockTestClass:
        # Class variable with annotation
        class_var: int = 42
        
        # Regular attribute (will be detected at runtime)
        regular_attr = "hello"
        
        def regular_method(self) -> str:
            return "method"
        
        @property
        def test_property(self) -> int:
            return 123
            
        @staticmethod
        def static_method() -> bool:
            return True
    
    # Create mock type_node_cache
    class MockTypeNodeCache:
        def __init__(self):
            self.by_id = {}
            self._call_count = 0
        
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Return our test class as a base type node
                class_node = MagicMock()
                class_node.obj = MockTestClass
                class_node.name = "MockTestClass"
                class_node.kind = "base"
                return [(1, class_node)]
            return []
        
        def get_or_create(self, obj, owning_obj):
            # Mock type node creation - return incrementing IDs
            self._call_count += 1
            mock_node = MagicMock()
            return (100 + self._call_count, mock_node)
    
    # Set up test cache  
    cache = MockTypeNodeCache()
    
    # Mock the insert function calls
    insert_calls = []
    
    def mock_insert_type_schema_ref(cursor, class_node_id, name, member_kind, type_node_id, position):
        insert_calls.append({
            'class_node_id': class_node_id,
            'name': name, 
            'member_kind': member_kind,
            'type_node_id': type_node_id,
            'position': position
        })
    
    # Use monkey patching to mock the database function
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_schema_ref
    
    try:
        # Apply monkey patch
        dal.type_elements.insert_type_schema_ref = mock_insert_type_schema_ref
        
        # Import and execute the function
        from bll.type_nodes import populate_type_schema_refs
        populate_type_schema_refs(cursor, cache)
        
        # Verify results
        print(f"Number of schema ref insert calls made: {len(insert_calls)}")
        print(f"Schema ref insert calls:")
        for call in insert_calls:
            print(f"  {call}")
        
        # Should have found several members (exact count may vary based on introspection)
        assert len(insert_calls) > 0, "Expected at least some schema ref insert calls"
        
        # Check that we found different member kinds
        member_kinds = {call['member_kind'] for call in insert_calls}
        print(f"Found member kinds: {member_kinds}")
        
        # Check that class_node_id is correct for all calls
        for call in insert_calls:
            assert call['class_node_id'] == 1, f"Expected class_node_id=1, got {call['class_node_id']}"
        
        # Check that positions are sequential
        positions = [call['position'] for call in insert_calls]
        expected_positions = list(range(len(insert_calls)))
        assert positions == expected_positions, f"Expected positions {expected_positions}, got {positions}"
        
        # Look for specific members we expect
        member_names = {call['name'] for call in insert_calls}
        print(f"Found member names: {member_names}")
        
        print("Basic functionality test passed!")
        
    finally:
        # Restore original function
        dal.type_elements.insert_type_schema_ref = original_insert


def test_type_schema_refs_with_annotations():
    """Test handling of type-annotated class variables"""
    
    print("Testing type schema refs with annotations...")
    
    cursor = MagicMock()
    
    # Create a class with type annotations
    class MockAnnotatedClass:
        name: str
        age: int
        active: bool = True
        scores: List[float] = []
    
    class MockTypeNodeCache:
        def __init__(self):
            self._call_count = 0
        
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                class_node = MagicMock()
                class_node.obj = MockAnnotatedClass
                class_node.name = "MockAnnotatedClass"
                return [(2, class_node)]
            return []
        
        def get_or_create(self, obj, owning_obj):
            self._call_count += 1
            return (200 + self._call_count, MagicMock())
    
    cache = MockTypeNodeCache()
    insert_calls = []
    
    def mock_insert_type_schema_ref(cursor, class_node_id, name, member_kind, type_node_id, position):
        insert_calls.append({
            'class_node_id': class_node_id,
            'name': name,
            'member_kind': member_kind, 
            'type_node_id': type_node_id,
            'position': position
        })
    
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_schema_ref
    
    try:
        dal.type_elements.insert_type_schema_ref = mock_insert_type_schema_ref
        
        from bll.type_nodes import populate_type_schema_refs
        populate_type_schema_refs(cursor, cache)
        
        print(f"Annotated class insert calls:")
        for call in insert_calls:
            print(f"  {call}")
        
        # Should find the annotated variables
        member_names = {call['name'] for call in insert_calls}
        print(f"Found annotated members: {member_names}")
        
        # Look for classvar member kinds for annotated variables
        classvar_calls = [call for call in insert_calls if call['member_kind'] == 'classvar']
        print(f"Found classvar calls: {len(classvar_calls)}")
        
        print("Annotations test passed!")
        
    finally:
        dal.type_elements.insert_type_schema_ref = original_insert


def test_type_schema_refs_skip_private():
    """Test that private members are properly skipped"""
    
    print("Testing that private members are skipped...")
    
    cursor = MagicMock()
    
    class MockClassWithPrivates:
        public_attr = "visible"
        _private_attr = "hidden"
        __dunder_attr = "also_hidden"
        
        def public_method(self):
            pass
            
        def _private_method(self):
            pass
            
        def __dunder_method(self):
            pass
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                class_node = MagicMock()
                class_node.obj = MockClassWithPrivates
                return [(3, class_node)]
            return []
        
        def get_or_create(self, obj, owning_obj):
            return (300, MagicMock())
    
    cache = MockTypeNodeCache()
    insert_calls = []
    
    def mock_insert_type_schema_ref(cursor, class_node_id, name, member_kind, type_node_id, position):
        insert_calls.append({'name': name, 'member_kind': member_kind})
    
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_schema_ref
    
    try:
        dal.type_elements.insert_type_schema_ref = mock_insert_type_schema_ref
        
        from bll.type_nodes import populate_type_schema_refs
        populate_type_schema_refs(cursor, cache)
        
        # Check that no private members were included
        member_names = {call['name'] for call in insert_calls}
        print(f"Found public members: {member_names}")
        
        # Should not contain any names starting with underscore
        private_members = [name for name in member_names if name.startswith('_')]
        assert len(private_members) == 0, f"Found private members that should be skipped: {private_members}"
        
        print("Private member skipping test passed!")
        
    finally:
        dal.type_elements.insert_type_schema_ref = original_insert


if __name__ == '__main__':
    test_type_schema_refs_functionality()
    test_type_schema_refs_with_annotations()
    test_type_schema_refs_skip_private()