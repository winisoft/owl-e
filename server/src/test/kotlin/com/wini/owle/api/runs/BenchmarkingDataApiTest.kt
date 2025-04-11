package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.BenchmarkingDataDto
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class BenchmarkingDataApiTest : BaseLongPkApiTest<BenchmarkingDataDto, BenchmarkingDataApi>() {

    override val route: String = "/api/benchmarking-data"

    override fun getDto(id: Long): BenchmarkingDataDto = DtoTestInstanceFactory.benchmarkingDataDto(
        id = id
    )

    override val serializer: KSerializer<BenchmarkingDataDto> = BenchmarkingDataDto.serializer()
    
    override val listSerializer: KSerializer<List<BenchmarkingDataDto>> = ListSerializer(BenchmarkingDataDto.serializer())

    override fun getApiInstance(
        dll: DLL<Long, BenchmarkingDataDto>,
        validator: BaseValidator<BenchmarkingDataDto>
    ): BenchmarkingDataApi = BenchmarkingDataApi(dll, validator)

}