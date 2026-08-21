package com.wini.owle.dto.testing

import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.tags.TagRelation
import com.wini.owle.dto.tags.enums.TagSource

/**
 * Canned [tags] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object TagsDtoFactory {

    fun tagDto(
        id: ULong = 1UL,
        name: String = "Tag Name",
        description: String? = "Tag Description",
        source: TagSource = TagSource.SYSTEM,
        userId: ULong  = 1UL
    ) : Tag {
        return Tag(
            id = id,
            name = name,
            description = description,
            source = source
        )
    }

    fun tagRelationDto(
        parentTag: Tag = tagDto(id = 1UL),
        childTag: Tag = tagDto(id = 2UL)
    ) : TagRelation {
        return TagRelation(
            parentTag = parentTag,
            childTag = childTag
        )
    }
}
