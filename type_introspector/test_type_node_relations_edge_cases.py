#!/usr/bin/env python3
"""
Edge case tests for type node relations implementation.
"""

import sys
import os
from unittest.mock import MagicMock

# Add project root to path
sys.path.insert(0, os.path.dirname(__file__))

def test_missing_parent_in_cache():
    """Test handling when parent class is not found in cache"""
    
    print("Testing missing parent in cache...")
    
    cursor = MagicMock()
    
    # Create a class that inherits from a parent not in the cache
    class MockParentClass:
        pass
    
    class MockChildClass(MockParentClass):
        pass
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Only return the child, not the parent
                child_node = MagicMock()
                child_node.obj = MockChildClass
                child_node.name = "MockChildClass"
                child_node.kind = "base"
                return [(2, child_node)]
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
        
        # Should make no calls since parent is not in cache
        assert len(insert_calls) == 0, f"Expected 0 insert calls, got {len(insert_calls)}"
        
        print("Missing parent test passed!")
        
    finally:
        dal.type_elements.insert_type_node_relation = original_insert


def test_multiple_inheritance():
    """Test handling of multiple inheritance"""
    
    print("Testing multiple inheritance...")
    
    cursor = MagicMock()
    
    class MockParentA:
        pass
        
    class MockParentB:
        pass
    
    class MockChildClass(MockParentA, MockParentB):
        pass
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Return all three classes
                parent_a_node = MagicMock()
                parent_a_node.obj = MockParentA
                parent_a_node.name = "MockParentA"
                
                parent_b_node = MagicMock()
                parent_b_node.obj = MockParentB
                parent_b_node.name = "MockParentB"
                
                child_node = MagicMock()
                child_node.obj = MockChildClass
                child_node.name = "MockChildClass"
                
                return [(1, parent_a_node), (2, parent_b_node), (3, child_node)]
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
        
        # Should make 2 calls - one for each parent
        print(f"Multiple inheritance calls: {insert_calls}")
        assert len(insert_calls) == 2, f"Expected 2 insert calls, got {len(insert_calls)}"
        
        # Both parents should be linked to child
        relationships = {(parent, child) for parent, child in insert_calls}
        assert (1, 3) in relationships, "ParentA -> Child relationship not found"
        assert (2, 3) in relationships, "ParentB -> Child relationship not found"
        
        print("Multiple inheritance test passed!")
        
    finally:
        dal.type_elements.insert_type_node_relation = original_insert


def test_node_without_obj_attribute():
    """Test handling of nodes that don't have obj attribute"""
    
    print("Testing nodes without obj attribute...")
    
    cursor = MagicMock()
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Return a node without obj attribute
                broken_node = MagicMock()
                del broken_node.obj  # Remove obj attribute
                broken_node.name = "BrokenNode"
                
                return [(1, broken_node)]
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
        
        # Should make no calls since node has no obj attribute
        assert len(insert_calls) == 0, f"Expected 0 insert calls, got {len(insert_calls)}"
        
        print("Nodes without obj test passed!")
        
    finally:
        dal.type_elements.insert_type_node_relation = original_insert


if __name__ == '__main__':
    test_missing_parent_in_cache()
    test_multiple_inheritance()
    test_node_without_obj_attribute()