#!/usr/bin/env python3
"""
Functional test for callable ownership implementation.
This test verifies that the populate_callable_ownership function works correctly
by testing its behavior with minimal dependencies.
"""

import sys
import os
from unittest.mock import MagicMock

# Add project root to path
sys.path.insert(0, os.path.dirname(__file__))

def test_callable_ownership_functionality():
    """Test that callable ownership logic works correctly"""
    
    print("Testing callable ownership functionality...")
    
    # Create mock cursor
    cursor = MagicMock()
    
    # Create mock type_node_cache that simulates the real structure
    class MockTypeNodeCache:
        def __init__(self):
            self.by_id = {}
        
        def find(self, **kwargs):
            # Simulate finding owner nodes
            if kwargs.get('name') == 'test_module' and kwargs.get('kind') == 'base':
                return [(100, MagicMock())]  # Module owner node
            elif kwargs.get('name') == 'TestClass' and kwargs.get('kind') == 'base':
                return [(200, MagicMock())]  # Class owner node
            return []
    
    # Create mock callable nodes
    class MockModuleCallable:
        def __init__(self):
            self.kind = 'callable'
            self.module = MagicMock()
            self.module.name = 'test_module'
    
    class MockClassCallable:
        def __init__(self):
            self.kind = 'callable'
            self.class_name = 'TestClass'
            self.module = MagicMock()
            self.module.name = 'test_module'
    
    class MockRegularNode:
        def __init__(self):
            self.kind = 'base'
    
    # Set up test cache
    cache = MockTypeNodeCache()
    cache.by_id = {
        1: MockRegularNode(),         # Should be ignored
        2: MockModuleCallable(),      # Should create ownership relation 
        3: MockClassCallable()        # Should create ownership relation
    }
    
    # Mock the insert function calls
    insert_calls = []
    
    def mock_insert_callable_owners(cursor, owner_id, callable_id):
        insert_calls.append((owner_id, callable_id))
    
    # Use monkey patching instead of import mocking
    import bll.type_node_models
    import dal.callable_elements
    
    # Save original functions
    original_module_callable = bll.type_node_models.ModuleScopeCallableTypeNode
    original_class_callable = bll.type_node_models.ClassScopeCallableTypeNode  
    original_insert = dal.callable_elements.insert_callable_owners
    
    try:
        # Apply monkey patches
        bll.type_node_models.ModuleScopeCallableTypeNode = MockModuleCallable
        bll.type_node_models.ClassScopeCallableTypeNode = MockClassCallable
        dal.callable_elements.insert_callable_owners = mock_insert_callable_owners
        
        # Import and execute the function
        from bll.type_node_relations import populate_callable_ownership
        populate_callable_ownership(cursor, cache)
        
        # Verify results
        print(f"Number of insert calls made: {len(insert_calls)}")
        print(f"Insert calls: {insert_calls}")
        
        # Should have made 2 calls - one for module callable, one for class callable
        assert len(insert_calls) == 2, f"Expected 2 insert calls, got {len(insert_calls)}"
        
        # Check the owner IDs are correct
        owner_ids = [call[0] for call in insert_calls]
        callable_ids = [call[1] for call in insert_calls]
        
        assert 100 in owner_ids, "Module owner ID (100) not found in calls"
        assert 200 in owner_ids, "Class owner ID (200) not found in calls" 
        assert 2 in callable_ids, "Module callable ID (2) not found in calls"
        assert 3 in callable_ids, "Class callable ID (3) not found in calls"
        
        print("All tests passed!")
        
    finally:
        # Restore original functions
        bll.type_node_models.ModuleScopeCallableTypeNode = original_module_callable
        bll.type_node_models.ClassScopeCallableTypeNode = original_class_callable
        dal.callable_elements.insert_callable_owners = original_insert


if __name__ == '__main__':
    test_callable_ownership_functionality()