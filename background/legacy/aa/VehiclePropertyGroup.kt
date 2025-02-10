package com.wini.owle.air.domain.aa

sealed class VehiclePropertyGroup(val id: Char) {

    object System: VehiclePropertyGroup('1') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }

    object Vendor: VehiclePropertyGroup('2') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }

    companion object {
        fun fromHex(value: String): VehiclePropertyGroup = when(value.toCharArray()[2]) {
            System.id -> System
            Vendor.id -> Vendor
            else -> error("Not among expected values for VehiclePropertyGroups")
        }
    }
}