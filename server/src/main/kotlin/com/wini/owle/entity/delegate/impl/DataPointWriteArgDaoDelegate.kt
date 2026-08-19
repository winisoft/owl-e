package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.steps.DataPointWriteArg
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.steps.DataPointWriteArgEntity
import com.wini.owle.entity.steps.DataPointWriteArgs
import com.wini.owle.entity.steps.DataPointWriteArgs.dataPointWrite
import com.wini.owle.entity.steps.DataPointWriteArgs.position
import com.wini.owle.entity.steps.DataPointWriteEntity
import com.wini.owle.entity.steps.DataPointWrites
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ColumnType
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


object DataPointWriteArgDaoDelegate: CompositeIdDaoDelegate<
        DataPointWriteArg,
        DataPointWriteArgEntity,
        >(DataPointWriteArgEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {
            it[dataPointWrite] = DataPointWriteEntity[params["data_point_write_id"] as ULong].id
            //it[position] =  Column<UInt>(table = DataPointWriteArgEntity.table, name = "position", columnType = ColumnType<UInt>)
        }
    }

    override fun extractId(dto: DataPointWriteArg): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}