package com.wini.owle.mapper.environs

import com.wini.owle.dto.PackageRepoDto
import com.wini.owle.entity.PackageRepoType.Companion.toDto
import com.wini.owle.entity.environs.PackageRepoDao
import org.jetbrains.exposed.sql.Transaction
import org.junit.jupiter.api.TestInstance
import org.koin.java.KoinJavaComponent
import kotlin.test.assertEquals
import com.wini.owle.dto.enums.PackageRepoType as PackageRepoTypeDto
import com.wini.owle.entity.PackageRepoType as PackageRepoTypeEntity
