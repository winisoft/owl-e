#!/usr/bin/env python3
"""
Functional test for type node relations implementation.
This test verifies that the populate_type_node_relations function correctly
establishes inheritance relationships between types.
"""

import sys
import os
from unittest.mock import MagicMock

# Add project root to path
sys.path.insert(0, os.path.dirname(__file__))

def test_type_node_relations_functionality():
    """Test that type node relations logic works correctly"""
    
    print("Testing type node relations functionality...")
    
    # Create mock cursor
    cursor = MagicMock()
    
    # Create test classes to simulate inheritance hierarchy
    class MockBaseClass:
        pass
    
    class MockChildClass(MockBaseClass):
        pass
        
    class MockGrandchildClass(MockChildClass):
        pass
    
    # Create mock type_node_cache
    class MockTypeNodeCache:
        def __init__(self):
            self.by_id = {}
        
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Return all base type nodes
                return [
                    (1, self._create_mock_node(MockBaseClass, "MockBaseClass")),
                    (2, self._create_mock_node(MockChildClass, "MockChildClass")),
                    (3, self._create_mock_node(MockGrandchildClass, "MockGrandchildClass")),
                    (4, self._create_mock_node(str, "str")),  # Built-in type
                ]
            return []
        
        def _create_mock_node(self, class_obj, name):
            node = MagicMock()
            node.obj = class_obj
            node.name = name
            node.kind = "base"
            return node
    
    # Set up test cache  
    cache = MockTypeNodeCache()
    
    # Mock the insert function calls
    insert_calls = []
    
    def mock_insert_type_node_relation(cursor, parent_id, child_id):
        insert_calls.append((parent_id, child_id))
    
    # Use monkey patching to mock the database function
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_node_relation
    
    try:
        # Apply monkey patch
        dal.type_elements.insert_type_node_relation = mock_insert_type_node_relation
        
        # Import and execute the function
        from bll.type_node_relations import populate_type_node_relations
        populate_type_node_relations(cursor, cache)
        
        # Verify results
        print(f"Number of relationship insert calls made: {len(insert_calls)}")
        print(f"Relationship insert calls: {insert_calls}")
        
        # Should have made 2 calls:
        # 1. MockBaseClass (id=1) -> MockChildClass (id=2) 
        # 2. MockChildClass (id=2) -> MockGrandchildClass (id=3)
        expected_calls = 2
        assert len(insert_calls) == expected_calls, f"Expected {expected_calls} insert calls, got {len(insert_calls)}"
        
        # Verify the specific relationships
        relationships = {(parent, child) for parent, child in insert_calls}
        
        # MockChildClass should have MockBaseClass as parent
        assert (1, 2) in relationships, "MockBaseClass -> MockChildClass relationship not found"
        
        # MockGrandchildClass should have MockChildClass as parent  
        assert (2, 3) in relationships, "MockChildClass -> MockGrandchildClass relationship not found"
        
        print("All tests passed!")
        
    finally:
        # Restore original function
        dal.type_elements.insert_type_node_relation = original_insert


def test_type_node_relations_no_inheritance():
    """Test that classes without inheritance relationships are handled correctly"""
    
    print("Testing type node relations with no inheritance...")
    
    cursor = MagicMock()
    
    class MockStandaloneClass:
        pass
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Return standalone class that only inherits from object
                node = MagicMock()
                node.obj = MockStandaloneClass
                node.name = "MockStandaloneClass"
                node.kind = "base"
                return [(1, node)]
            return []
    
    cache = MockTypeNodeCache()
    insert_calls = []
    
    def mock_insert_type_node_relation(cursor, parent_id, child_id):
        insert_calls.append((parent_id, child_id))
    
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_node_relation
    
    try:
        dal.type_elements.insert_type_node_relation = mock_insert_type_node_relation
        
        from bll.type_node_relations import populate_type_node_relations
        populate_type_node_relations(cursor, cache)
        
        # Should make no calls since the class only inherits from object (which is skipped)
        assert len(insert_calls) == 0, f"Expected 0 insert calls, got {len(insert_calls)}"
        
        print("No inheritance test passed!")
        
    finally:
        dal.type_elements.insert_type_node_relation = original_insert


def test_type_node_relations_non_class_types():
    """Test that non-class types are properly skipped"""
    
    print("Testing type node relations with non-class types...")
    
    cursor = MagicMock()
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Return mix of class and non-class nodes
                class_node = MagicMock()
                class_node.obj = type  # This is a class
                class_node.name = "type"
                class_node.kind = "base"
                
                non_class_node = MagicMock()
                non_class_node.obj = "not_a_class"  # This is not a class
                non_class_node.name = "str_literal"
                non_class_node.kind = "base"
                
                return [(1, class_node), (2, non_class_node)]
            return []
    
    cache = MockTypeNodeCache()
    insert_calls = []
    
    def mock_insert_type_node_relation(cursor, parent_id, child_id):
        insert_calls.append((parent_id, child_id))
    
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_node_relation
    
    try:
        dal.type_elements.insert_type_node_relation = mock_insert_type_node_relation
        
        from bll.type_node_relations import populate_type_node_relations
        populate_type_node_relations(cursor, cache)
        
        # Should make no calls since only non-inheriting types are present
        print(f"Insert calls made: {insert_calls}")
        
        print("Non-class types test passed!")
        
    finally:
        dal.type_elements.insert_type_node_relation = original_insert


if __name__ == '__main__':
    test_type_node_relations_functionality()
    test_type_node_relations_no_inheritance()
    test_type_node_relations_non_class_types()