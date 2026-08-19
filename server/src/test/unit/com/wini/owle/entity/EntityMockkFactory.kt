package com.wini.owle.entity

import com.wini.owle.entity.testing.EnvironsMockkFactory
import com.wini.owle.entity.testing.RunsMockkFactory
import com.wini.owle.entity.testing.StepsMockkFactory
import com.wini.owle.entity.testing.TagsMockkFactory
import com.wini.owle.entity.testing.TransformsMockkFactory
import com.wini.owle.entity.testing.TypingMockkFactory
import com.wini.owle.entity.testing.UserMockkFactory
import com.wini.owle.entity.testing.WorkflowsMockkFactory


/**
 * Canned DTO instances for tests.
 *
 * The instances themselves live in one factory per DTO package (see
 * [com.wini.owle.dto.testing]); this object only composes them and resolves a DTO class to the
 * factory that builds it. Splitting it that way keeps each file responsible for a single
 * package's types instead of one file tracking every type in the domain.
 *
 * Every branch below is keyed by a [BaseDto] subtype. That is load-bearing: the registry used to
 * carry an entry keyed by an *enum* of a similar name, which made [createDtoInstance] fail to
 * compile in a way that pointed nowhere useful.
 */
object EntityMockkFactory {


    val environs = EnvironsMockkFactory
    val runs = RunsMockkFactory
    val steps = StepsMockkFactory
    val tags = TagsMockkFactory
    val transforms = TransformsMockkFactory
    val typing = TypingMockkFactory
    val user = UserMockkFactory
    val workflows = WorkflowsMockkFactory


}