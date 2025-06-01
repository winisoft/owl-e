


def populate_type_node_relations(cursor, type_node_cache):
    from dal.type_elements import insert_type_node_relation
    
    # Iterate through all base type nodes in the cache to find inheritance relationships
    base_type_nodes = type_node_cache.find(kind="base")
    
    for child_node_id, child_node in base_type_nodes:
        if not hasattr(child_node, 'obj') or child_node.obj is None:
            continue
            
        # Get the Python class object
        child_class = child_node.obj
        
        # Skip non-class types that don't have inheritance
        if not isinstance(child_class, type):
            continue
            
        # Get the base classes (direct parents)
        if hasattr(child_class, '__bases__'):
            for parent_class in child_class.__bases__:
                # Skip object class as it's the universal base
                if parent_class is object:
                    continue
                    
                # Find the parent node in the cache by matching the class object
                parent_nodes = []
                for parent_node_id, parent_node in base_type_nodes:
                    if (hasattr(parent_node, 'obj') and 
                        parent_node.obj is parent_class):
                        parent_nodes.append((parent_node_id, parent_node))
                        break
                
                # Insert the relationship if we found the parent node
                if parent_nodes:
                    parent_node_id = parent_nodes[0][0]
                    insert_type_node_relation(cursor, parent_node_id, child_node_id)


def populate_callable_ownership(cursor, type_node_cache):
    from bll.type_node_models import ClassScopeCallableTypeNode, ModuleScopeCallableTypeNode
    from dal.callable_elements import insert_callable_owners
    
    # Iterate through all callable type nodes in the cache
    for node_id, node in type_node_cache.by_id.items():
        if node.kind != "callable":
            continue
        
        # Determine the owner based on the callable type
        if isinstance(node, ModuleScopeCallableTypeNode):
            # Find the module type node that owns this callable
            module_owner_nodes = type_node_cache.find(
                kind="base",
                name=node.module.name,
                namespace=node.module.name
            )
            if module_owner_nodes:
                owner_node_id = module_owner_nodes[0][0]  # Get the first match's ID
                insert_callable_owners(cursor, owner_node_id, node_id)
        
        elif isinstance(node, ClassScopeCallableTypeNode):
            # Find the class type node that owns this callable
            class_owner_nodes = type_node_cache.find(
                kind="base",
                name=node.class_name,
                namespace=node.module.name
            )
            if class_owner_nodes:
                owner_node_id = class_owner_nodes[0][0]  # Get the first match's ID
                insert_callable_owners(cursor, owner_node_id, node_id)