package com.wini.owle.api.runs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.api.runs.test.BenchmarkReportApi
import com.wini.owle.dll.DLL
import com.wini.owle.dto.runs.test.BenchmarkReport
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.DtoValidator


open class BenchmarkReportApiTest : BaseLongPkApiTest<BenchmarkReport, BenchmarkReportApi>() {

    override val route: String = "/api/benchmarking-data"

    override fun getDto(id: Long): BenchmarkReport = DtoTestInstanceFactory.benchmarkingDataDto(
        id = id
    )

    override val serializer: KSerializer<BenchmarkReport> = BenchmarkReport.serializer()
    
    override val listSerializer: KSerializer<List<BenchmarkReport>> = ListSerializer(BenchmarkReport.serializer())

    override fun getApiInstance(
        dll: DLL<Long, BenchmarkReport>,
        validator: DtoValidator<BenchmarkReport>
    ): BenchmarkReportApi = BenchmarkReportApi(dll, validator)

}