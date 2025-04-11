package com.wini.owle.models



interface OwleGroup {
    val groupType: GroupType
    val allChildren: List<GroupSubject>
}

interface GroupSubject {
    val id: Long
    val name: String
    val description: String?
    val elementType: GroupedElementType
    val parent: OwleGroup?
}