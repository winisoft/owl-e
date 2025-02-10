package com.wini.owle.services.environs

import com.wini.owle.dll.typing.TypeDeclarationDll
import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.services.BaseOwleServiceTest
import com.wini.owle.services.Service
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject


open class PackageRepoServiceTest {

    @Nested
    @DisplayName("Repo Succeeds")
    inner class PackageRepoServiceSuccessTest: BaseOwleServiceTest<Long, PackageRepoDto>() {
        private val service: PackageRepoService by inject()
        override val testModule: Module
            get() = module {
                single<PackageRepoService> { PackageRepoService(get(), get()) }
            }
        //        stopKoin()
        //        startKoin {
        //            modules(
        //                serviceModule,         // from main
        //                repositoryModule,      // from main
        //                mapperModule,          // from main
        //                //dllModule,             // from main, if you need it
        //                module {               // The purely test-tailored bits
        //                    single<TypeDeclarationDll> { TypeDeclarationDll(get(), get(), get()) }
        //                }
        //            )
        ////            modules(module {
        ////                includes(serviceModule)
        ////                single<TypeDeclarationDll> { TypeDeclarationDll(get(), get(), get()) }
        ////            })
        //        }
        //        transaction {
        //            exec("SET FOREIGN_KEY_CHECKS=0;")
        //            SchemaUtils.drop(*implicatedTables.toTypedArray())
        //            exec("SET FOREIGN_KEY_CHECKS=1;")
        //            SchemaUtils.create(*implicatedTables.toTypedArray())
        //        }
        //    }

        override fun getInstance(): PackageRepoDto {
            TODO("Not yet implemented")
        }

        override fun getService(): Service<Long, PackageRepoDto> {
            TODO("Not yet implemented")
        }

    }

    @Nested
    @DisplayName("Repo Fails")
    inner class PackageRepoServiceFailureTest: BaseOwleServiceTest<Long, PackageRepoDto>() {

        override val testModule: Module
            get() = TODO("Not yet implemented")

        override fun getInstance(): PackageRepoDto {
            TODO("Not yet implemented")
        }

        override fun getService(): Service<Long, PackageRepoDto> {
            TODO("Not yet implemented")
        }

    }

}