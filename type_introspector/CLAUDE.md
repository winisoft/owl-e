# CLAUDE.md                                                                                                    
                                                                                                                
 This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.         
                                                                                                                
 ## Overview                                                                                                    
                                                                                                                
 This is a Python type introspection system that dynamically analyzes CPython source code across multiple       
 Python versions to build a comprehensive type system database. The system uses AST parsing and runtime         
 introspection to discover classes, type aliases, callables, and their relationships, then stores this          
 information in a MySQL database.                                                                               
                                                                                                                
 ## Development Commands                                                                                        
                                                                                                                
 ### Running the Application                                                                                    
 ```bash                                                                                                        
 python main.py                                                                                                 
 ```                                                                                                            
                                                                                                                
 ### Installing Dependencies                                                                                    
 ```bash                                                                                                        
 pip install -r requirements.txt                                                                                
 ```                                                                                                            
                                                                                                                
 ### Database Setup                                                                                             
 The application requires a MySQL database with the following configuration:                                    
 - Host: localhost                                                                                              
 - Database: owle_dev                                                                                           
 - User: wini                                                                                                   
 - Password: the_eru_shrug_0!                                                                                   
                                                                                                                
 Note: Database credentials are currently hardcoded in `db_connection.py:12-17`. 
 At present, rectifying this is a low priority. 
                                                                                                                
 ## Architecture                                                                                                
                                                                                                                
 ### High-Level Flow                                                                                            
 The system operates in three main phases:                                                                      
 1. **Module & Class Discovery** (`main.py:37-45`): AST-based discovery of modules and classes                  
 2. **Type Node Population** (`main.py:47-52`): Introspection and database insertion of type information        
 3. **Relationship Mapping** (`main.py:54-57`): Establishing ownership, composition, and inheritance            
 relationships                                                                                                  
                                                                                                                
 ### Layer Architecture                                                                                         
 - **Entry Point**: `main.py` - orchestrates the complete introspection pipeline                                
 - **Business Logic Layer (bll/)**: Core introspection and type analysis logic                                  
 - **Data Access Layer (dal/)**: Database interaction components                                                
 - **Data Sources**: `cypython_srcs/` - contains CPython standard library source files for multiple Python      
 versions                                                                                                       
                                                                                                                
 ### Key Components                                                                                             
                                                                                                                
 #### Business Logic Layer (bll/)                                                                               
 - `module_introspect.py`: Discovers and loads Python modules from filesystem, handles class discovery          
 - `type_nodes.py`: Populates base type node information in database                                            
 - `type_aliases.py`: Discovers and processes type aliases from modules                                         
 - `callable_types.py`: Introspects callable objects (functions, methods)                                       
 - `type_node_relations.py`: Establishes relationships between type nodes                                       
 - `typevars.py`: Handles TypeVar discovery and processing                                                      
 - `synthetic_inserts.py`: Handles synthetic/special type insertions                                            
 - `type_node_cache.py`: Caching layer for type node operations                                                 
 - `dto_models.py`: Data transfer objects and model definitions                                                 
                                                                                                                
 #### Data Access Layer (dal/)                                                                                  
 - `type_elements.py`: Database operations for type elements                                                    
 - `callable_elements.py`: Database operations for callable elements                                            
 - `type_generic_elements.py`: Database operations for generic type elements                                    
 - `typevar_elements.py`: Database operations for TypeVar elements                                              
                                                                                                                
 #### Configuration                                                                                             
 - `cpython_targets.py`: Defines which CPython versions to process (currently only 3.12.9 is active)            
 - `db_connection.py`: Database connection management                                                           
                                                                                                                
 ### Data Processing Pipeline                                                                                   
 1. Load modules from `cypython_srcs/` directory structure                                                      
 2. Discover classes using AST parsing and runtime introspection                                                
 3. Build inheritance hierarchies and type relationships                                                        
 4. Store type information in MySQL database with proper relationships                                          
 5. Handle special cases like generic types, type aliases, and TypeVars                                         
                                                                                                                
 ### Key Data Structures                                                                                        
 - `IntrospectedModule`: Represents a discovered Python module                                                  
 - `ClassDiscovery`: Raw class discovery information from AST                                                   
 - `IntrospectedClass`: Fully resolved class with inheritance information                                       
 - `DiscoveredAlias`: Type alias discovery information                                                          
 - `TypeNodeCache`: Caching mechanism for database operations                                                   
                                                                                                                
 ## Important Notes     

 ### Synthetic Type Inserts
  > While the separate client application depending on the database will enable users to define an unpredictable complexity
  > of types they will interact with, it is prudent to prepopulate a contingent of types that are most likely to be useful 
  > to the broadest number of users.
 
 - Unions are populated to a maximum depth of five, using builtin types as members. These account for permutations of, 
 e.g.: 
   - `str` | `int`
   - `str` | `int` | `float` 
   - `str` | `int` | `float` | `bool` 
   - `str` | `int` | `float` | `bool` | `set`
   - `str` | `int` | `float` | `bool` | `NoneType`
 - Type instantiations are populated such that the one-argument generic types are predefined, as well as pairs of 
types for two-arg `dict`, e.g.:
 - `list`[`str`], `list`[`float`], `list`[`bool`] ...
 - `set`[`str`], `set`[`float`], `set`[`bool`] ...
 - `dict`[`str`, `str`], `dict`[`str`, `float`], `dict`[`str`, `bool`] ...

 - Type instantiations reflecting definitions of unions are populated from the previously synthesized elements 
preserved in the TypeNodeCache, e.g.:
 - `list`[`str` | `int`]
 - `list`[`str` | `int` | `float`]
 - `list`[`str` | `int` | `float` | `bool`]
                                                                                                                
 ### Security Considerations                                                                                    
 - Database credentials are hardcoded and should be externalized to environment variables. This will be addressed 
after the primary objective's completion.            
 - The system loads and executes Python modules dynamically, which could pose security risks with untrusted     
 code. Only a cpython release's ./Lib is supported to be introspected on and applying it to any other source set is
 at present not under consideration for development.
                                                                                                                
 ### Python Version Support                                                                                     
 The system is designed to work with multiple CPython versions simultaneously. Currently configured versions    
 are in `cpython_targets.py`, with only Python 3.12.9 active. Following the project's completion and the validation of
its result with respect to 3.12.9 as a pilot data set, a number of additional versions will be considered as candidates
for support. It is not, at present, predictable what depth of version history the present approach will allow, owing
to API changes in the core library itself, it will be addressed at the appropriate time.
                                                                                                                
 ### Database Dependencies                                                                                      
 This is a database-heavy application that requires MySQL connectivity. All type information is persisted to    
 support complex queries and relationships.


## Development Priorities

### Type Callable Ownership


### Type Schema Refs

The type_schema_refs table is implemented with the purpose of defining a type's composition; this will prove useful 
for handling tasks like serialization that require a clear picture of what fields exist on a type. Note that the
insertion logic is somewhat distinct here. While the type_nodes table has a unique index on a deduplication_hash,
type_schema_refs may hold references to separate type_schema_ref records, which articulates the shape of types 
compositional hierarchy, which is separate from the inheritance hierarchy of the types themselves.
and 

```sql
CREATE TABLE `type_schema_refs` (
 `id` bigint unsigned NOT NULL AUTO_INCREMENT,
 `parent_ref_id` bigint unsigned DEFAULT NULL,
 `class_node_id` bigint unsigned NOT NULL,
 `name` varchar(255) NOT NULL,
 `member_kind` enum('attribute','method','property','classvar') NOT NULL,
 `type_node_id` bigint unsigned DEFAULT NULL,
 `description` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
 `position` int NOT NULL DEFAULT '0',
PRIMARY KEY (`id`),
KEY `fk_parent_schema` (`parent_ref_id`),
KEY `fk_type_node` (`type_node_id`),
KEY `fk_class_node` (`class_node_id`),
CONSTRAINT `fk_class_node` FOREIGN KEY (`class_node_id`) REFERENCES `type_nodes` (`id`) ON DELETE CASCADE,
CONSTRAINT `fk_parent_schema` FOREIGN KEY (`parent_ref_id`) REFERENCES `type_schema_refs` (`id`),
CONSTRAINT `fk_type_node` FOREIGN KEY (`type_node_id`) REFERENCES `type_nodes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2960290 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

Please proceed in a similar fashion to what has heretofore been achieved working with types. I do not expect it 
will be necessary to select records from the database as everything that has been inserted into it thus far is
preserved in the TypeNodeCache in the full detail we require to be preserved of it.


### Improve Version Targeting

Modify the main script to accept as an argument (whose meaning will soon become clear) `--update` :

A configuration data point should be defined for: 
 - python version '3.9.22' to serve as `CPY_VERSION_MIN` -- the lowest version for which we currently support
the collection of type data from the associated cpython tag release's Lib sources.
 - the url 'https://github.com/python/cpython' to serve as `CPYTHON_GIT_REPO`

When the main script is passed the --update tag, the script should check the repo to determine whether a version 
exists for which we do not have sources in the ./cpython_srcs dir (considering only candidate or final release versions), 
referencing the most recent version found as the name of its child dir.

At present, for example, our most recent version is 3.13.5 and a release exists for

**v3.14.0rc1**

Discovering this, we need to download those sources from `https://github.com/python/cpython/archive/refs/tags/v3.14.0rc1.zip` 
create a new directory in cpython_srcs, named simple to reflect the version, e.g. `3.14.0rc1`. Unzipping the sources,
a few modifications are needed to excise irrelevant data. Where a typical release will look like:

- cpython-3.14.0rc1
  - cpython-3.14.0rc1
       - Lib
         - asyncio
           - test 
         - distutils
           - tests
         - idlelib
         - test 
         - turtledemo
         - unittest
           - test 
         - venv

Only the files that are within the scope of Lib are potentially useful -- begin by deleting every file or directory that
is sibling to the Lib dir. Among the subdirectories of Lib shown above: idlelib, test, turtledemo, and venv are 
not helpful to this app's goal. Delete those as well. Delete a dir named 'test' or 'tests' that is positioned grandchild
of Lib (such as asyncio, distutils, and unittest demonstrate) disturbing nothing else with overly eager matchers and the like.

Finally, pull the files and directories within the Lib directory up to the root directory for the version, then delete the 
redundant child dir with its same name (which only contains Lib since it contents were moved).

Rename the dir to simply its version name, place it in ./cpython_srcs, and allow for the normal process to continue 
with this the targeted version.
