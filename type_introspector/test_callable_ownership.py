import unittest
from unittest.mock import MagicMock, patch, call
import sys
import types

from bll.type_node_relations import populate_callable_ownership
from bll.type_node_cache import TypeNodeCache
from bll.type_node_models import (
    ClassScopeCallableTypeNode, 
    ModuleScopeCallableTypeNode, 
    TypeNode,
    CallableParameter
)
from bll.dto_models import IntrospectedModule


class TestCallableOwnership(unittest.TestCase):
    
    def setUp(self):
        """Set up test fixtures"""
        self.cursor = MagicMock()
        self.py_version_id = 228
        
        # Create mock modules
        self.mock_module = MagicMock(spec=types.ModuleType)
        self.mock_module.__name__ = "test_module"
        
        self.introspected_module = IntrospectedModule(
            type=self.mock_module,
            name="test_module", 
            py_version_id=self.py_version_id
        )
        
        # Create mock class
        self.mock_class = MagicMock(spec=type)
        self.mock_class.__name__ = "TestClass"
        
    def test_populate_callable_ownership_module_scope(self):
        """Test that module-scoped callables are correctly associated with their module owners"""
        
        # Create a TypeNodeCache with test data
        type_node_cache = TypeNodeCache(self.py_version_id, self.cursor)
        
        # Create a module type node (owner)
        module_node = TypeNode(
            module=self.introspected_module,
            obj=self.mock_module,
            name="test_module",
            kind="base",
            is_generic=False,
            py_version_id=self.py_version_id,
            namespace="test_module"
        )
        module_node_id = 1
        type_node_cache.by_id[module_node_id] = module_node
        
        # Create a module-scoped callable
        callable_node = ModuleScopeCallableTypeNode(
            module=self.introspected_module,
            obj=MagicMock(),
            name="test_function",
            py_version_id=self.py_version_id,
            docstring="Test function",
            callable_params=[],
            parameter_type_node_ids=[],
            return_type_obj=None,
            return_type_id=None
        )
        callable_node_id = 2
        type_node_cache.by_id[callable_node_id] = callable_node
        
        # Mock the find method to return the module owner
        with patch.object(type_node_cache, 'find') as mock_find:
            mock_find.return_value = [(module_node_id, module_node)]
            
            # Mock the insert_callable_owners function
            with patch('bll.type_node_relations.insert_callable_owners') as mock_insert:
                # Execute the function
                populate_callable_ownership(self.cursor, type_node_cache)
                
                # Verify that find was called correctly
                mock_find.assert_called_with(
                    kind="base",
                    name="test_module",
                    namespace="test_module"
                )
                
                # Verify that insert_callable_owners was called with correct parameters
                mock_insert.assert_called_once_with(self.cursor, module_node_id, callable_node_id)
    
    def test_populate_callable_ownership_class_scope(self):
        """Test that class-scoped callables are correctly associated with their class owners"""
        
        # Create a TypeNodeCache with test data
        type_node_cache = TypeNodeCache(self.py_version_id, self.cursor)
        
        # Create a class type node (owner)
        class_node = TypeNode(
            module=self.introspected_module,
            obj=self.mock_class,
            name="TestClass",
            kind="base",
            is_generic=False,
            py_version_id=self.py_version_id,
            namespace="test_module"
        )
        class_node_id = 1
        type_node_cache.by_id[class_node_id] = class_node
        
        # Create a class-scoped callable
        callable_node = ClassScopeCallableTypeNode(
            module=self.introspected_module,
            obj=MagicMock(),
            name="test_method",
            py_version_id=self.py_version_id,
            class_type=self.mock_class,
            class_name="TestClass",
            callable_params=[],
            parameter_type_node_ids=[],
            docstring="Test method",
            return_type_obj=None,
            return_type_id=None,
            is_staticmethod=False,
            is_classmethod=False
        )
        callable_node_id = 2
        type_node_cache.by_id[callable_node_id] = callable_node
        
        # Mock the find method to return the class owner
        with patch.object(type_node_cache, 'find') as mock_find:
            mock_find.return_value = [(class_node_id, class_node)]
            
            # Mock the insert_callable_owners function
            with patch('bll.type_node_relations.insert_callable_owners') as mock_insert:
                # Execute the function
                populate_callable_ownership(self.cursor, type_node_cache)
                
                # Verify that find was called correctly
                mock_find.assert_called_with(
                    kind="base",
                    name="TestClass",
                    namespace="test_module"
                )
                
                # Verify that insert_callable_owners was called with correct parameters
                mock_insert.assert_called_once_with(self.cursor, class_node_id, callable_node_id)
    
    def test_populate_callable_ownership_no_owner_found(self):
        """Test that callables without found owners are skipped"""
        
        # Create a TypeNodeCache with test data
        type_node_cache = TypeNodeCache(self.py_version_id, self.cursor)
        
        # Create a module-scoped callable without a corresponding owner node
        callable_node = ModuleScopeCallableTypeNode(
            module=self.introspected_module,
            obj=MagicMock(),
            name="orphan_function",
            py_version_id=self.py_version_id,
            docstring="Orphan function",
            callable_params=[],
            parameter_type_node_ids=[],
            return_type_obj=None,
            return_type_id=None
        )
        callable_node_id = 1
        type_node_cache.by_id[callable_node_id] = callable_node
        
        # Mock the find method to return no owners
        with patch.object(type_node_cache, 'find') as mock_find:
            mock_find.return_value = []  # No owners found
            
            # Mock the insert_callable_owners function
            with patch('bll.type_node_relations.insert_callable_owners') as mock_insert:
                # Execute the function
                populate_callable_ownership(self.cursor, type_node_cache)
                
                # Verify that find was called
                mock_find.assert_called_once()
                
                # Verify that insert_callable_owners was NOT called
                mock_insert.assert_not_called()
    
    def test_populate_callable_ownership_mixed_nodes(self):
        """Test that only callable nodes are processed, other nodes are ignored"""
        
        # Create a TypeNodeCache with mixed node types
        type_node_cache = TypeNodeCache(self.py_version_id, self.cursor)
        
        # Add a non-callable node (should be ignored)
        regular_node = TypeNode(
            module=self.introspected_module,
            obj=str,
            name="str",
            kind="base",
            is_generic=False,
            py_version_id=self.py_version_id
        )
        type_node_cache.by_id[1] = regular_node
        
        # Add a callable node (should be processed)
        callable_node = ModuleScopeCallableTypeNode(
            module=self.introspected_module,
            obj=MagicMock(),
            name="test_function",
            py_version_id=self.py_version_id,
            docstring="Test function",
            callable_params=[],
            parameter_type_node_ids=[],
            return_type_obj=None,
            return_type_id=None
        )
        type_node_cache.by_id[2] = callable_node
        
        # Mock the find method
        with patch.object(type_node_cache, 'find') as mock_find:
            mock_find.return_value = [(1, regular_node)]  # Mock owner found
            
            # Mock the insert_callable_owners function
            with patch('bll.type_node_relations.insert_callable_owners') as mock_insert:
                # Execute the function
                populate_callable_ownership(self.cursor, type_node_cache)
                
                # Verify that find was called only once (for the callable node)
                mock_find.assert_called_once()
                
                # Verify that insert_callable_owners was called only once
                mock_insert.assert_called_once()


if __name__ == '__main__':
    unittest.main()