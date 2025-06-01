#!/usr/bin/env python3
"""
Edge case tests for type schema refs implementation.
"""

import sys
import os
from unittest.mock import MagicMock

# Add project root to path
sys.path.insert(0, os.path.dirname(__file__))

def test_non_class_objects():
    """Test that non-class objects are properly skipped"""
    
    print("Testing non-class objects are skipped...")
    
    cursor = MagicMock()
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                # Return mix of class and non-class nodes
                class_node = MagicMock()
                class_node.obj = type  # This is a class
                class_node.name = "type"
                
                non_class_node = MagicMock()
                non_class_node.obj = "not_a_class"  # This is not a class
                non_class_node.name = "str_literal"
                
                none_obj_node = MagicMock()
                none_obj_node.obj = None  # This has no obj
                none_obj_node.name = "none_obj"
                
                return [(1, class_node), (2, non_class_node), (3, none_obj_node)]
            return []
        
        def get_or_create(self, obj, owning_obj):
            return (400, MagicMock())
    
    cache = MockTypeNodeCache()
    insert_calls = []
    
    def mock_insert_type_schema_ref(cursor, class_node_id, name, member_kind, type_node_id, position):
        insert_calls.append({'class_node_id': class_node_id, 'name': name})
    
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_schema_ref
    
    try:
        dal.type_elements.insert_type_schema_ref = mock_insert_type_schema_ref
        
        from bll.type_nodes import populate_type_schema_refs
        populate_type_schema_refs(cursor, cache)
        
        # Should only process the actual class (id=1), not the non-class or None objects
        class_ids = {call['class_node_id'] for call in insert_calls}
        print(f"Processed class node IDs: {class_ids}")
        
        # Should only contain class_node_id=1 (the actual class)
        assert len(class_ids) <= 1, f"Expected at most 1 class ID, got {class_ids}"
        if class_ids:
            assert 1 in class_ids, f"Expected class_node_id=1, got {class_ids}"
        
        print("Non-class objects test passed!")
        
    finally:
        dal.type_elements.insert_type_schema_ref = original_insert


def test_introspection_errors():
    """Test handling of members that cause introspection errors"""
    
    print("Testing introspection error handling...")
    
    cursor = MagicMock()
    
    class ProblematicClass:
        normal_attr = "fine"
        
        @property 
        def broken_property(self):
            # This property will cause issues during introspection
            raise RuntimeError("This property is broken")
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                class_node = MagicMock()
                class_node.obj = ProblematicClass
                return [(5, class_node)]
            return []
        
        def get_or_create(self, obj, owning_obj):
            if "broken" in str(obj):
                # Simulate error during type node creation
                from custom_errors import UnrepresentableTypeException
                raise UnrepresentableTypeException("Cannot represent broken type")
            return (500, MagicMock())
    
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
        
        # Should still process the normal attributes despite errors with others
        member_names = {call['name'] for call in insert_calls}
        print(f"Successfully processed members: {member_names}")
        
        # Should have at least processed some members without errors
        assert len(member_names) > 0, "Expected to process at least some members"
        
        print("Introspection error handling test passed!")
        
    finally:
        dal.type_elements.insert_type_schema_ref = original_insert


def test_empty_class():
    """Test handling of classes with no meaningful members"""
    
    print("Testing empty class handling...")
    
    cursor = MagicMock()
    
    class EmptyClass:
        pass  # No members except inherited ones
    
    class MockTypeNodeCache:
        def find(self, **kwargs):
            if kwargs.get('kind') == 'base':
                class_node = MagicMock()
                class_node.obj = EmptyClass
                return [(6, class_node)]
            return []
        
        def get_or_create(self, obj, owning_obj):
            return (600, MagicMock())
    
    cache = MockTypeNodeCache()
    insert_calls = []
    
    def mock_insert_type_schema_ref(cursor, class_node_id, name, member_kind, type_node_id, position):
        insert_calls.append({'name': name})
    
    import dal.type_elements
    original_insert = dal.type_elements.insert_type_schema_ref
    
    try:
        dal.type_elements.insert_type_schema_ref = mock_insert_type_schema_ref
        
        from bll.type_nodes import populate_type_schema_refs
        populate_type_schema_refs(cursor, cache)
        
        # May have few or no calls since empty class has few public members
        print(f"Empty class processed {len(insert_calls)} members")
        
        # Should not crash - that's the main test
        print("Empty class test passed!")
        
    finally:
        dal.type_elements.insert_type_schema_ref = original_insert


if __name__ == '__main__':
    test_non_class_objects()
    test_introspection_errors()
    test_empty_class()