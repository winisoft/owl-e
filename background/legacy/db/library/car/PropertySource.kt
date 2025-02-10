package com.wini.owle.air.db.library.car

enum class PropertySource(val key: Long) {
    VEHICLE_PROPERTY_ID(0L),
    VENDOR_PROP_ID(1L);

    companion object {
        fun fromKey(value: Long) = when(value) {
            com.wini.owle.air.db.library.car.PropertySource.VEHICLE_PROPERTY_ID.key -> com.wini.owle.air.db.library.car.PropertySource.VEHICLE_PROPERTY_ID
            com.wini.owle.air.db.library.car.PropertySource.VENDOR_PROP_ID.key -> com.wini.owle.air.db.library.car.PropertySource.VENDOR_PROP_ID
            else -> error("Unanticipated value")
        }
    }
}