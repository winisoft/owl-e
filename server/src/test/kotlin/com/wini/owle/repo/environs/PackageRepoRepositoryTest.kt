package com.wini.owle.repo.environs

import com.wini.owle.ExposedTestUtils
import com.wini.owle.entity.environs.PackageRepoDao
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.MockKAnnotations
import io.mockk.*
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.jetbrains.exposed.sql.Transaction


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