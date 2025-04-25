package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.PyPackageRepo
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PyPyPackageEntityRepoApiTest : BaseLongPkApiTest<PyPackageRepo, PyPackageRepoApi>() {

    override val route: String = "/api/package-repo"

    override fun getDto(id: Long): PyPackageRepo = DtoTestInstanceFactory.packageRepoDto(
        id = id
    )

    override val serializer: KSerializer<PyPackageRepo> = PyPackageRepo.serializer()
    
    override val listSerializer: KSerializer<List<PyPackageRepo>> = ListSerializer(PyPackageRepo.serializer())

    override fun getApiInstance(
        dll: DLL<Long, PyPackageRepo>,
        validator: BaseValidator<PyPackageRepo>
    ): PyPackageRepoApi = PyPackageRepoApi(dll, validator)

}