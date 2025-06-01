import random
from data_source.workflows import workflows
from helpers.post_to_ollama import post_to_ollama


import re


def prompt_for_cleanup(cursor):
    for wf in workflows["items"]:
        desc = wf["description"]
        prompt = f"""What follows is a description of an automated workflow process. If necessary, rewrite it to be a simple plain-English summary that uses proper grammar.
        
        YOUR MESSAGE MUST CONTAIN ONLY THE REVISED DESCRIPTION. NO OTHER TEXT. NO FORMATTING. NO ASSERTIONS OF COMPLIANCE AS A PREFACE.
        
        YOUR MESSAGE MUST CONTAIN ONLY THE REVISED DESCRIPTION!!!!! 
        
        Here is the description:
        {desc}"""
        response = post_to_ollama(prompt)
        if response.startswith('"') and response.endswith('"'):
            response = response[1:-1]
        match = re.match(r"\. [A-Z]", response.strip())
        if not match and response.endswith('.'):
            response = response[:-1]
        try:
            cursor.execute(
                """
                UPDATE workflows SET description = %s WHERE id = %s
                """,
                (response.strip(), wf['id']),
            )
        except Exception as e:
            print("Error inserting workflow:", e)


def cleanup(cursor):
    for wf in workflows["items"]:
        id = wf['id']
        name = wf['name']
        desc = wf["description"]
        try:
            cursor.execute(
                """
                SELECT name, description FROM workflows WHERE id = %s
                """,
                (id,),
            )

            row = cursor.fetchone()
            if row is None:
                return None
            else:
                old_name, old_desc = row

            cursor.execute(
                """
                UPDATE workflows SET name = %s, description = %s WHERE id = %s
                """,
                (name, desc, id),
            )
        except Exception as e:
            print("Error inserting workflow:", e)


def get_random_row(cursor, table_name, where_clause="1=1"):
    cursor.execute(f"SELECT * FROM {table_name} WHERE {where_clause}")
    rows = cursor.fetchall()
    return random.choice(rows) if rows else None