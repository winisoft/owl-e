package com.wini.owle.repo

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


abstract class UnconstrainedDeleteRepoTest<ID: Any, T: Entity<ID>> {


}