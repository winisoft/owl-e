package com.wini.owle.air.domain.aa

import com.wini.owle.air.db.cal.CalibrationEntity
import com.wini.owle.air.db.library.cal.CalibrationRepository
import com.wini.owle.air.domain.cal.Calibration
import kotlinx.coroutines.flow.Flow


class CalibrationAccessor(
    private val repository: CalibrationRepository
): DomainAccessor<Calibration, CalibrationEntity>(repository),
    EntityToModelMapper<CalibrationEntity, Calibration> {

    fun getAll(): Flow<List<Calibration>> {
        return repository.selectAll().mapToModel()
    }

    fun getById(id: String): Flow<Calibration?> {
        return repository.selectById(id).mapToModelNullable()
    }

    override fun Calibration.mapToEntity(): CalibrationEntity {
        return CalibrationEntity(
            calId,
            overridden,
            calDefFileName,
            calDefFileVersion,
            calName,
            calVersion,
            calType,
            calValue,
            calMin,
            calMax,
            arrayLen,
            encoding,
            enumId,
        )
    }

    override fun CalibrationEntity.mapToModel(): Calibration {
        return Calibration(
            calId,
            overridden,
            calDefFileName,
            calDefFileVersion,
            calName,
            calVersion,
            calType,
            calValue,
            min,
            max,
            arrayLen,
            encoding,
            enumId
        )
    }
}