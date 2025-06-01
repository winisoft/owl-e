import requests
import json


HEADERS = {"Content-Type": "application/json"}
OLLAMA_URL = "http://localhost:11434/api/generate"  # Adjust if needed
OLLAMA_MODEL = "mistral-nemo:latest"

def post_to_ollama(prompt: str) -> str:
    data = {
        "model": OLLAMA_MODEL,
        "prompt": prompt,
        "stream": False
    }
    response = requests.post(OLLAMA_URL, headers=HEADERS, data=json.dumps(data))
    response.raise_for_status()
    return response.json().get("response", "")