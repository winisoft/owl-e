package com.wini.owle.api.environs

import com.wini.owle.api.BaseLongPkApiTest
import com.wini.owle.dll.DLL
import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.environs.PyPackage
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import com.wini.owle.validators.BaseValidator


open class PyPyPackageEntityApiTest : BaseLongPkApiTest<PyPackage, PyPackageApi>() {

    override val route: String = "/api/package"
    
    override fun getDto(id: Long): PyPackage = DtoTestInstanceFactory.packageDto(
        id = id
    )
    
    override val serializer: KSerializer<PyPackage> = PyPackage.serializer()
    
    override val listSerializer: KSerializer<List<PyPackage>> = ListSerializer(PyPackage.serializer())
    
    override fun getApiInstance(
        dll: DLL<Long, PyPackage>,
        validator: BaseValidator<PyPackage>
    ): PyPackageApi = PyPackageApi(dll, validator)

}