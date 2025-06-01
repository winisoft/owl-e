

Gets the data straight from the PyPI API metadata by iterating over the csv dist,package combinations and forming the urls to scrape on that basis.

*Priority 2*: Moving forward, we will want this process to be governed by a fresh new csv dataset of recent downloads from the API. It will remain worthwhile to reprocess the full data set and perform updates where changes to the model and resolved bugs would dictate that we represent the metadata differently. That would, however, require considerable attention paid to what updates should occur and when -- at this early phase, 'insert' commands assuredly outnumber updates by a wide margin