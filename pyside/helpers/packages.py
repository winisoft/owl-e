

def format_keywords(keywords: list[str]) -> str:
    """
    Formats a list of keywords into a pipe ( '|' )  delimited string.
    """
    return '|'.join(keywords).strip() if keywords else ''
