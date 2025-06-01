import requests
from bs4 import BeautifulSoup
import mysql.connector
from config import DB_CONFIG  # Assuming DB_CONFIG is defined in a config module

def fetch_classifiers():
    url = 'https://pypi.org/classifiers/'
    response = requests.get(url)
    response.raise_for_status()
    soup = BeautifulSoup(response.text, 'html.parser')
    main = soup.find("main")
    ul = main.find("ul")
    
    a_text = []
    for li in ul.find_all('li'):
        a = li.find('a')
        if a and a.get_text(strip=True):
            a_text.append(a.get_text(strip=True))

    return a_text

def insert_tags_and_relations(classifiers):
    conn = mysql.connector.connect(**DB_CONFIG)
    cursor = conn.cursor()
    tag_cache = {}  # Cache to store tag names and their corresponding IDs

    for classifier in classifiers:
        parts = [part.strip() for part in classifier.split('::')]
        parent_id = None
        path = ''
        for part in parts:
            path = f"{path}::{part}" if path else part
            if path in tag_cache:
                tag_id = tag_cache[path]
            else:
                # Check if the tag already exists
                cursor.execute("SELECT id FROM tags WHERE name = %s AND source = 'pypi'", (part,))
                result = cursor.fetchone()
                if result:
                    tag_id = result[0]
                else:
                    # Insert the new tag
                    cursor.execute(
                        "INSERT INTO tags (name, source, description, userId) VALUES (%s, 'pypi', NULL, NULL)",
                        (part,)
                    )
                    tag_id = cursor.lastrowid
                tag_cache[path] = tag_id

            # Insert the parent-child relationship
            if parent_id and tag_id != parent_id:
                cursor.execute(
                    "SELECT 1 FROM tag_relations WHERE parentTagId = %s AND childTagId = %s",
                    (parent_id, tag_id)
                )
                if not cursor.fetchone():
                    cursor.execute(
                        "INSERT INTO tag_relations (parentTagId, childTagId) VALUES (%s, %s)",
                        (parent_id, tag_id)
                    )
            parent_id = tag_id

    conn.commit()
    cursor.close()
    conn.close()

def main():
    classifiers = fetch_classifiers()
    insert_tags_and_relations(classifiers, DB_CONFIG)

if __name__ == '__main__':
    main()
