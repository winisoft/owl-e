

from processors.infos import process_info_data

import logging

from processors.prompts import process_prompts
from processors.version_aware_types import process_all_supported_versions
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def main():
   process_prompts()


if __name__ == "__main__":
    main()