import builtins
import json
import random
import types
from typing import Any, Dict, List, Tuple, Callable
import typing
import MySQLdb
import re
from data_source import phases, workflows, folders, data_points
from helpers.post_to_ollama import post_to_ollama
from utils.prompt_utils import prompt_for_cleanup
from utils.prompt_utils import get_random_row

DB_CONFIG = {
    "host": "localhost",
    "user": "wini",
    "password": "the_eru_shrug_0!",
    "database": "owle_dev",
    "autocommit": False
}

from builtins import object

d: builtins.list = []
e: typing.Any
f = Int32

a = Callable.__getitem__()

def provide() -> int:
    return 42

def accept(f: Callable[[], int]):
    result = f()
    args: builtins.list[builtins.type] = result.__args__
    reveal_type(result)
    builtins.Exception

def prompt_for_steps():
    step_id = 1

    with open("steps.json", "a") as steps_file, open("failed_prompts.json", "a") as failed_prompts_file:
        for wf in workflows.workflows['items']:
            workflow_id = wf['id']
            workflow_name = wf['name']
            workflow_desc = wf['description']

            root_phases = []
            for phase in phases.phases['items']:
                if phase['workflow_id'] != workflow_id:
                    root_phases.append(phase)
            
            for phase in root_phases:
            # Collect up to 4 sample phases for this workflow
                workflow_phases = [p for p in phases.phases['items'] if p['workflow_id'] == workflow_id]
            if len(workflow_phases) > 4:
                workflow_phases = random.sample(workflow_phases, 4)

            if not workflow_phases:
                continue  # Skip if no usable phases

            # Generate human-readable list for prompt
            phases_texts = []
            for i, p in enumerate(workflow_phases):
                entry = f"'{p['name']}', that is, {p['description']}"
                if i == len(workflow_phases) - 1 and len(workflow_phases) > 1:
                    entry = f"and {entry}"
                phases_texts.append(entry)
            phases_text = ", ".join(phases_texts)

            prereqs = []
            dp_texts = []
            for dp in data_points.data_points['items']:
                if dp['workflow_id'] == workflow_id:
                    prereqs.append(dp)
            for i, dp in enumerate(prereqs):
                if i < len(prereqs) - 1:
                    if (len(prereqs) > 1):
                        dp_texts.append(f"'{dp['name']}', which is a {dp['data_type']}, {dp['description']}, ")
                    else:
                        dp_texts.append(f"'{dp['name']}', which is a {dp['data_type']}, {dp['description']}. ")
                else:
                    if len(prereqs) > 1:
                        entry = f"and '{dp['name']}', which is a {dp['data_type']}, {dp['description']}. "
                    else:
                        entry = f"'{dp['name']}', which is a {dp['data_type']}, {dp['description']}. "
                
                    
                dp_texts.append(entry)

            prompt = f"""Suppose someone has an assignment to write a python app to accomplish a specific task: {workflow_name}. They're instructed: {workflow_desc}
Their app would need to perform a number of steps: {phases_text}. Assume there is already a python environment configured with any required dependencies installed, and the app has access to the following preset data points: {''.join(dp_texts)}
What data points would be needed to be passed between individual steps of this workflow that *don't* have some predetermined value when we begin? Beyond the scope of local fields internal to some function, what result of some function might we use as a passed argment to another?  What might the app persist to access again later? Is there a data type in Python that would be especially well suited to representing that data's likely structure, or is it more practical for it to be expressed as a file and referenced by its path?

List any such data element as a member of a JSON array of object. It should contain 'name', 'description', 'data_type', and 'format' -- where format has a string value, either 'file' or 'field'; data_type has a string value, either the fully-namespaced python type suitable to it (e.g. typing.List[builtins.int], typing.Dict[builtins.str, typing.Any]]) -- or if the data_point is a file, 'builtins.str' to represent its path.

RESPOND WITH ONLY THE RAW JSON ARRAY. NO OTHER TEXT. NO FORMATTING. NO ASSERTIONS OF COMPLIANCE AS A PREFACE.
"""


def prompt_for_data_points():
    data_point_id = 897

    with open("data_points.json", "a") as data_points_file, open("failed_prompts.json", "a") as failed_prompts_file:
        for wf in workflows.workflows['items']:
            workflow_id = wf['id']
            if workflow_id < 142:
                continue
            workflow_name = wf['name']
            workflow_desc = wf['description']

            # Collect up to 4 sample phases for this workflow
            workflow_phases = [p for p in phases.phases['items'] if p['workflow_id'] == workflow_id]
            if len(workflow_phases) > 4:
                workflow_phases = random.sample(workflow_phases, 4)

            if not workflow_phases:
                continue  # Skip if no usable phases

            # Generate human-readable list for prompt
            phases_texts = []
            for i, p in enumerate(workflow_phases):
                entry = f"'{p['name']}', that is, {p['description']}"
                if i == len(workflow_phases) - 1 and len(workflow_phases) > 1:
                    entry = f"and {entry}"
                phases_texts.append(entry)
            phases_text = ", ".join(phases_texts)

            prereqs = []
            dp_texts = []
            for dp in data_points.data_points['items']:
                if dp['workflow_id'] == workflow_id:
                    prereqs.append(dp)
            for i, dp in enumerate(prereqs):
                if i < len(prereqs) - 1:
                    if (len(prereqs) > 1):
                        dp_texts.append(f"'{dp['name']}', which is a {dp['data_type']}, {dp['description']}, ")
                    else:
                        dp_texts.append(f"'{dp['name']}', which is a {dp['data_type']}, {dp['description']}. ")
                else:
                    if len(prereqs) > 1:
                        entry = f"and '{dp['name']}', which is a {dp['data_type']}, {dp['description']}. "
                    else:
                        entry = f"'{dp['name']}', which is a {dp['data_type']}, {dp['description']}. "
                
                    
                dp_texts.append(entry)

            prompt = f"""Suppose someone has an assignment to write a python app to accomplish a specific task: {workflow_name}. They're instructed: {workflow_desc}
Their app would need to perform a number of steps: {phases_text}. Assume there is already a python environment configured with any required dependencies installed, and the app has access to the following preset data points: {''.join(dp_texts)}
What data points would be needed to be passed between individual steps of this workflow that *don't* have some predetermined value when we begin? Beyond the scope of local fields internal to some function, what result of some function might we use as a passed argment to another?  What might the app persist to access again later? Is there a data type in Python that would be especially well suited to representing that data's likely structure, or is it more practical for it to be expressed as a file and referenced by its path?

List any such data element as a member of a JSON array of object. It should contain 'name', 'description', 'data_type', and 'format' -- where format has a string value, either 'file' or 'field'; data_type has a string value, either the fully-namespaced python type suitable to it (e.g. typing.List[builtins.int], typing.Dict[builtins.str, typing.Any]]) -- or if the data_point is a file, 'builtins.str' to represent its path.

RESPOND WITH ONLY THE RAW JSON ARRAY. NO OTHER TEXT. NO FORMATTING. NO ASSERTIONS OF COMPLIANCE AS A PREFACE.
"""

            response = post_to_ollama(prompt)

            # Clean malformed backslashes
            safe_response = re.sub(r'\\([^"\\/bfnrtu])', r'\1', response)

            try:
                parsed = json.loads(safe_response)
            except json.JSONDecodeError as e:
                error_obj = {
                    "error": str(e),
                    "workflow_id": workflow_id,
                    "raw_response": response
                }
                json.dump(error_obj, failed_prompts_file)
                failed_prompts_file.write(",\n")
                continue

            # Enrich data points with workflow_id and id
            for dp in parsed:
                dp['id'] = data_point_id
                dp['workflow_id'] = workflow_id
                dp['is_prerequisite'] = False
                data_point_id += 1

            # Append to output
            json.dump(parsed, data_points_file)
            data_points_file.write(",\n")


def prompt_for_data_point_prerequisites():
    data_point_id = 1

    with open("data_points.json", "a") as data_points_file, open("failed_prompts.json", "a") as failed_prompts_file:
        for wf in workflows.workflows['items']:
            workflow_id = wf['id']
            workflow_name = wf['name']
            workflow_desc = wf['description']

            # Collect up to 4 sample phases for this workflow
            workflow_phases = [p for p in phases.phases['items'] if p['workflow_id'] == workflow_id]
            if len(workflow_phases) > 4:
                workflow_phases = random.sample(workflow_phases, 4)

            if not workflow_phases:
                continue  # Skip if no usable phases

            # Generate human-readable list for prompt
            phases_texts = []
            for i, p in enumerate(workflow_phases):
                entry = f"'{p['name']}', that is, {p['description']}"
                if i == len(workflow_phases) - 1 and len(workflow_phases) > 1:
                    entry = f"and {entry}"
                phases_texts.append(entry)
            phases_text = ", ".join(phases_texts)

            prompt = f"""Suppose someone has an assignment to write a python app to accomplish a specific task: {workflow_name}. They're instructed: {workflow_desc}
Assume there is already a python environment configured with any required dependencies installed. Their app would need to perform a number of steps: {phases_text}
But before any of that begins, what data points would they need to gather or generate to support the work? Is there a data type in Python that would be especially well suited to representing that prerequisite data, or is that data point in practice almost certainly initially in the form of a file, for which the application would require access rights and a file path?

List them as a JSON array of objects containing 'name', 'description', 'data_type', and 'format' -- where format has a string value, either 'file' or 'field'; data_type has a string value, either the fully-namespaced python type suitable to it (e.g. typing.List[builtins.int], typing.Dict[builtins.str, typing.Any]]) -- or if the data_point is a file, 'builtins.str' to represent its path.

RESPOND WITH ONLY THE RAW JSON ARRAY. NO OTHER TEXT. NO FORMATTING. NO ASSERTIONS OF COMPLIANCE AS A PREFACE.
"""

            response = post_to_ollama(prompt)

            # Clean malformed backslashes
            safe_response = re.sub(r'\\([^"\\/bfnrtu])', r'\1', response)

            try:
                parsed = json.loads(safe_response)
            except json.JSONDecodeError as e:
                error_obj = {
                    "error": str(e),
                    "workflow_id": workflow_id,
                    "raw_response": response
                }
                json.dump(error_obj, failed_prompts_file)
                failed_prompts_file.write(",\n")
                continue

            # Enrich data points with workflow_id and id
            for dp in parsed:
                dp['id'] = data_point_id
                dp['workflow_id'] = workflow_id
                dp['is_prerequisite'] = True
                data_point_id += 1

            # Append to output
            json.dump(parsed, data_points_file)
            data_points_file.write(",\n")
            

def prompt_for_workflow(cursor):
    folder = random.choice(folders.workflow_folders["items"])
    #folder = get_random_row(cursor, "workflow_folders", "id IS NOT NULL")
    if not folder:
        return

    folder_id = folder["id"]
    folder_name = folder["name"]
    folder_desc = folder["description"]

    prompt = f"""Propose a programming challenge that will achieve some automatable process that can be performed entirely using python functions.
    
    The challenge must fit into a category called '{folder_name}'. If that seems ambiguous, the category is described as being "{folder_desc}".

    Provide a name that serves as a brief summary of the task and a slightly more detailed description. Format them with a pipe char delimiter between the two, i.e. name|description

    TO REPEAT: Only one character for the purpose of formatting is desired or permitted. Do not provide unrequested formatting elements. Do not make conversation. Simply respond with the name, a pipe char, and a description. You will be held liable for any damage to the system infrastructure resulting from your noncompliance, and a series of crushing layoffs will be initiated across the company.
    """
#  
    response = post_to_ollama(prompt)
    splitted = response.strip().split("|")
    if len(splitted) == 2:
        name = ''.join(char for char in splitted[0] if (char.isalnum() or char in (' ', '-')))
        desc = ''.join(char for char in splitted[1] if (char.isalnum() or char in (' ', '-')))
        try:
            cursor.execute(
                """
                INSERT INTO workflows (user_id, name, description, python_version_id, parent_folder_id) VALUES (%s, %s, %s, %s, %s)
                """,
                (1, name, desc, 215, folder_id),
            )
        except Exception as e:
            print("Error inserting workflow:", e)


def prompt_for_phase(cursor):
    phase_id = 1
    hierarchy_links: List[Tuple[int, int]] = []

    with open("phases.json", "a") as phases_file, open("failed_prompts.json", "a") as failed_prompts_file, open("phase_relations.sql", 'a') as relations_file:
        for wf in workflows.workflows['items']:
            workflow_id = wf['id']
            workflow_name = wf['name']
            workflow_desc = wf['description']
            all_phases: List[Dict[str, Any]] = []

            prompt = f"""Suppose someone has an assignment to write a python app to accomplish a specific task: {workflow_name}. They're instructed: {workflow_desc}
Assume there is already a python environment configured with any required dependencies installed, and any required input data is already prepared and available. 
How would you group the likely individual python executing 'steps' associated with completing the task described? In other words, what phases would lend conceptual clarity and 
bridge the gap between the high-level task and the low-level steps? If it lends clarity, you are permitted to organize these phase objects hierarchically. 

Format your response as a JSON Array of 'phase' objects, each with a name and description string, as well as a child_phases JSON array containing 'phase' objects if any are present.

RESPOND WITH ONLY THE RAW JSON ARRAY. NO OTHER TEXT. NO FORMATTING. NO ASSERTIONS OF COMPLIANCE AS A PREFACE.
"""

            response = post_to_ollama(prompt)

            # Clean invalid escape sequences, e.g., "A\*" => "A*"
            safe_response = re.sub(r'\\([^"\\/bfnrtu])', r'\1', response)

            try:
                parsed = json.loads(safe_response)
            except json.JSONDecodeError as e:
                # Emit what we got
                error_obj = {
                    "error": str(e),
                    "raw_response": response,
                    "workflow_id": workflow_id
                }
                json.dump(error_obj, failed_prompts_file)
                failed_prompts_file.write(",\n")
                continue  # Skip further processing for this workflow

            def enrich_phase_tree(phases: List[Dict[str, Any]], parent_id: int = None) -> List[Dict[str, Any]]:
                nonlocal phase_id
                local_results: List[Dict[str, Any]] = []

                for display_order, phase in enumerate(phases):
                    this_id = phase_id
                    phase_id += 1

                    phase['id'] = this_id
                    phase['workflow_id'] = workflow_id
                    phase['display_order'] = display_order

                    if parent_id is not None:
                        hierarchy_links.append((parent_id, this_id))

                    children = phase.get('child_phases', [])
                    phase['child_phases'] = children  # Always present

                    local_results.append(phase)

                    if children:
                        local_results.extend(enrich_phase_tree(children, parent_id=this_id))

                return local_results

            enriched = enrich_phase_tree(parsed)
            all_phases.extend(enriched)

            # Emit JSON output per workflow
            json.dump({"workflow_id": workflow_id, "phases": all_phases}, phases_file)
            phases_file.write(",\n")

            # Emit relations for the current workflow
            if hierarchy_links:
                relations_file.write("INSERT INTO phase_hierarchy (parent_id, child_id) VALUES ")
                for i, (p_id, c_id) in enumerate(hierarchy_links):
                    relations_file.write(f"({p_id}, {c_id})")
                    if i != len(hierarchy_links) - 1:
                        relations_file.write(", ")
                    else:
                        relations_file.write(";\n")
                hierarchy_links.clear()  # Reset for next workflow
            




    with open("phases.jsonl", "a") as f:
        json.dump({ "response": response }, f)
        f.write("\n")


def process_prompts():
    #conn = MySQLdb.connect(**DB_CONFIG)
    #cursor = conn.cursor()

    prompt_for_data_points()

    #conn.commit()

    # for _ in range(250):
    #     prompt_for_workflow(cursor)
    #     conn.commit()


    #conn.close()


if __name__ == "__main__":
    process_prompts()
