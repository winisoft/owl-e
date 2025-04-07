package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.runs.BenchmarkingDataDto
import kotlinx.serialization.KSerializer


class BenchmarkingDataApiTest : BaseLongPkApiTest<Long, BenchmarkingDataDto, BenchmarkingDataApi>() {
    override val route: String
        get() = "/api/benchmarking-data"

    override fun getDto(): BenchmarkingDataDto = DtoTestInstanceFactory.benchmarkingDataDto()

    override val serializer: KSerializer<BenchmarkingDataDto>
        get() = TODO("Not yet implemented")
    override val listSerializer: KSerializer<List<BenchmarkingDataDto>>
        get() = TODO("Not yet implemented")

    override fun getApiInstance(dll: DLL<Long, BenchmarkingDataDto>): BenchmarkingDataApi {
        TODO("Not yet implemented")
    }

}