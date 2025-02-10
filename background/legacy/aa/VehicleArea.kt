package com.wini.owle.air.domain.aa

sealed class VehicleArea(val id: Char) {

    object Global: VehicleArea('1') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }
    object Window: VehicleArea('3') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }
    object Mirror: VehicleArea('4') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }
    object Seat: VehicleArea('5') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }
    object Door: VehicleArea('6') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }
    object Wheel: VehicleArea('7') {
        override fun toString(): String = this::class.simpleName!!.toString()
    }

    companion object {
        fun fromHex(value: String): VehicleArea = when(value.toCharArray()[3]) {
            Global.id -> Global
            Window.id -> Window
            Mirror.id -> Mirror
            Seat.id -> Seat
            Door.id -> Door
            Wheel.id -> Wheel
            else -> error("Not among expected values for VehiclePropertyGroups")
        }
    }
}