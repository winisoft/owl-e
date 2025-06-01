
Obviously this module is nothing it's just keeping the functionality available until it's determined when and where it will be used and how to architect best for that.

*Priority 3*: Jep should not be relied on -- too much greentext -- but it likely will have its uses. For example -- the VersionOverflow type exists as a compromise that ameliorates python's arbitrary version segment length by abstracting values that proved too large to store with a ulong into a separate table that would be subject to secondary indexing / processing of this more manageable subset