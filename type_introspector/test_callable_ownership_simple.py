import unittest
from unittest.mock import MagicMock, patch
import sys
import os

# Add the project root to Python path
sys.path.insert(0, os.path.dirname(__file__))

class TestCallableOwnershipSimple(unittest.TestCase):
    """
    Simple test for callable ownership functionality without complex imports
    """
    
    def test_populate_callable_ownership_logic(self):
        """Test the core logic of populate_callable_ownership function"""
        
        # Mock the cursor
        cursor = MagicMock()
        
        # Create a mock type_node_cache with minimal structure
        type_node_cache = MagicMock()
        
        # Mock nodes - one regular node, one module-scoped callable, one class-scoped callable
        mock_regular_node = MagicMock()
        mock_regular_node.kind = "base"
        
        mock_module_callable = MagicMock()
        mock_module_callable.kind = "callable" 
        mock_module_callable.module.name = "test_module"
        
        mock_class_callable = MagicMock()
        mock_class_callable.kind = "callable"
        mock_class_callable.class_name = "TestClass"
        mock_class_callable.module.name = "test_module"
        
        # Set up the by_id dictionary
        type_node_cache.by_id = {
            1: mock_regular_node,
            2: mock_module_callable, 
            3: mock_class_callable
        }
        
        # Mock the find method to return owner nodes
        def mock_find(**kwargs):
            if kwargs.get('name') == "test_module":
                return [(10, MagicMock())]  # Mock module owner
            elif kwargs.get('name') == "TestClass":  
                return [(20, MagicMock())]  # Mock class owner
            return []
        
        type_node_cache.find = mock_find
        
        # Import and test the function with mocked dependencies
        with patch('dal.callable_elements.insert_callable_owners') as mock_insert:
            # Set up isinstance mocks
            with patch('bll.type_node_models.ModuleScopeCallableTypeNode') as MockModuleCallable:
                with patch('bll.type_node_models.ClassScopeCallableTypeNode') as MockClassCallable:
                    
                    # Configure isinstance behavior
                    def mock_isinstance(obj, cls):
                        if cls == MockModuleCallable:
                            return obj == mock_module_callable
                        elif cls == MockClassCallable:
                            return obj == mock_class_callable
                        return False
                    
                    with patch('builtins.isinstance', side_effect=mock_isinstance):
                        # Import the function here to avoid circular import issues
                        from bll.type_node_relations import populate_callable_ownership
                        
                        # Execute the function
                        populate_callable_ownership(cursor, type_node_cache)
                        
                        # Verify that insert_callable_owners was called twice
                        # Once for module callable, once for class callable
                        self.assertEqual(mock_insert.call_count, 2)
                        
                        # Verify the calls were made with correct parameters
                        calls = mock_insert.call_args_list
                        
                        # First call should be for module callable (owner_id=10, callable_id=2)
                        self.assertEqual(calls[0][0], (cursor, 10, 2))
                        
                        # Second call should be for class callable (owner_id=20, callable_id=3) 
                        self.assertEqual(calls[1][0], (cursor, 20, 3))
    
    def test_callable_ownership_no_owners(self):
        """Test that callables without owners are skipped"""
        
        cursor = MagicMock()
        type_node_cache = MagicMock()
        
        # Mock a callable node without owner
        mock_callable = MagicMock()
        mock_callable.kind = "callable"
        mock_callable.module.name = "orphan_module"
        
        type_node_cache.by_id = {1: mock_callable}
        type_node_cache.find = MagicMock(return_value=[])  # No owners found
        
        with patch('dal.callable_elements.insert_callable_owners') as mock_insert:
            with patch('bll.type_node_models.ModuleScopeCallableTypeNode') as MockModuleCallable:
                with patch('builtins.isinstance', return_value=True):
                    from bll.type_node_relations import populate_callable_ownership
                    
                    populate_callable_ownership(cursor, type_node_cache)
                    
                    # Verify that insert_callable_owners was NOT called
                    mock_insert.assert_not_called()


if __name__ == '__main__':
    unittest.main()