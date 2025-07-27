package com.wini.owle.schema.wrapper.declaration


data class ConstraintAttributeImpl(
    val delegate: ConstraintAttribute
): ConstraintAttribute by delegate {


}