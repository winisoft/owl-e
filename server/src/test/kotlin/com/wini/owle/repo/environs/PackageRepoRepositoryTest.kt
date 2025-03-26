package com.wini.owle.repo.environs

import com.wini.owle.ExposedTestUtils
import com.wini.owle.entity.environs.PackageRepoDao
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.jetbrains.exposed.dao.LongEntityClass
import org.junit.jupiter.api.BeforeEach


open class PackageRepoRepositoryTest {
    
    @MockK
    private lateinit var entityClassMock: LongEntityClass<PackageRepoDao>
    
    private lateinit var repository: PackageRepoRepository
    
    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        ExposedTestUtils.setupMockTransactions()
        
        repository = object : PackageRepoRepository() {
            override val entityClass: LongEntityClass<PackageRepoDao> 
                get() = entityClassMock
        }
    }


}