package com.wini.owle.dto.tags

import com.wini.owle.dto.CompositeDto

interface TagSubject: CompositeDto {

    val subjectId: ULong

    val tagId: ULong

}