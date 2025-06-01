

import csv
from typing import List, Tuple, Optional
from config import CLASSIFIER_MAP_PATH

import logging

logger = logging.getLogger(__name__)

def read_csv_lines(file_path: str) -> List[Tuple[str, str]]:
    """
    Reads (distribution, version) pairs from a CSV file.

    Returns:
        A list of valid (distribution, version) tuples, and a list of rows that failed validation.
    """
    results = []

    with open(file_path, newline='', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile)
        _ = next(reader, None)

        for row in reader:
            if len(row) < 2:
                continue

            dist, ver = row[0].strip(), row[1].strip()
            if not dist or not ver:
                continue

            results.append((dist, ver))

    return results


def find_classifier_tag_ids(classifiers: Optional[List[str]]) -> Optional[List[int]]:
    """
    Reads classifiers from a CSV file and returns the tag_id of the tag corresponding to the classifier
    """
    if not classifiers:
        return None

    with open(CLASSIFIER_MAP_PATH, newline='', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile)
        _ = next(reader, None)
        tag_ids = []
        for classifier in classifiers:
            for row in reader:
                if classifier == row[1].strip():
                    tag_ids.append(int(row[0]))

        return tag_ids


def find_classifier_id(classifier: str) -> Optional[int]:
    """
    Find the classifier ID from the classifier map.
    Returns None if not found.
    """
    with open(CLASSIFIER_MAP_PATH, newline='', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile)
        _ = next(reader, None)  # Skip header
        for row in reader:
            if classifier == row[1].strip():
                return int(row[0])
    return None


def get_associated_tag_ids(distribution: str, version_id: int, classifiers: List[str]) -> Optional[List[int]]:
    """
    Fetch associated tag IDs for a given distribution and version ID.
    Returns a list of tag IDs.
    """
    if not classifiers:
        return None

    with open(CLASSIFIER_MAP_PATH, newline='', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile)
        _ = next(reader, None)

        tag_ids = []

        for classifier in classifiers:
            classifier_id = find_classifier_id(classifier.strip())
            if classifier_id is not None:
                tag_ids.append(classifier_id)
            else:
                logger.warning(f"Classifier '{classifier}' not found in classifier map.")

        return tag_ids

