package com.wini.owle.mapper

import com.wini.owle.dto.DecimalValue
import java.math.BigDecimal


fun BigDecimal.toDecimalValue(): DecimalValue {
    return DecimalValue(this.toPlainString())
}

fun DecimalValue.toBigDecimal(): BigDecimal {
    return BigDecimal(this.value)
}