
import kotlinx.datetime.LocalDateTime as KLocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect
import org.jetbrains.exposed.sql.vendors.MysqlDialect
import org.jetbrains.exposed.sql.vendors.PostgreSQLDialect
import org.jetbrains.exposed.sql.vendors.H2Dialect
import org.jetbrains.exposed.sql.vendors.SQLServerDialect
import org.jetbrains.exposed.sql.vendors.OracleDialect
import org.jetbrains.exposed.sql.vendors.SQLiteDialect
import org.jetbrains.exposed.sql.vendors.MariaDBDialect
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import org.jetbrains.exposed.sql.ColumnType
import java.sql.Types
import java.time.LocalDateTime
//
//class KotlinxLocalDateTimeColumnType : ColumnType<KLocalDateTime>() {
//    override fun sqlType(): String = when (currentDialect) {
//        is PostgreSQLDialect -> "TIMESTAMP"
//        is MysqlDialect -> "DATETIME"
//        is H2Dialect -> "TIMESTAMP"
//        is SQLServerDialect -> "DATETIME2"
//        is OracleDialect -> "TIMESTAMP"
//        is SQLiteDialect -> "TEXT"
//        is MariaDBDialect -> "DATETIME"
//        else -> error("Unsupported dialect: ${currentDialect.name}")
//    }
//
//    override fun valueFromDB(value: Any): KLocalDateTime = when (value) {
//        is LocalDateTime -> value.toKotlinLocalDateTime()
//        is java.sql.Timestamp -> value.toLocalDateTime().toKotlinLocalDateTime()
//        is String -> LocalDateTime.parse(value).toKotlinLocalDateTime()
//        else -> error("Unexpected value of type ${value::class.simpleName} in valueFromDB: $value")
//    }
//
//    override fun notNullValueToDB(value: Any): Any = when (value) {
//        is KLocalDateTime -> value.toJavaLocalDateTime()
//        else -> error("Unexpected value of type ${value::class.simpleName} in notNullValueToDB: $value")
//    }
//
//    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
//        if (value == null) {
//            stmt.setNull(index, Types.TIMESTAMP)
//        } else {
//            stmt[index] = notNullValueToDB(value)
//        }
//    }
//}



//class KotlinxLocalDateTimeColumnType : ColumnType<KLocalDateTime>() {
//
//    override fun sqlType(): String = "DATETIME"
//
//    override fun valueFromDB(value: Any): KLocalDateTime {
//        return when (value) {
//            is LocalDateTime -> value.toKotlinLocalDateTime() // Convert from java.time
//            is String -> LocalDateTime.parse(value).toKotlinLocalDateTime() // Parse ISO string
//            else -> throw IllegalArgumentException("Unexpected type for valueFromDB: $value")
//        }
//    }
//
//    override fun valueToDB(value: Any?): Any? {
//        return when (value) {
//            is KLocalDateTime -> value.toJavaLocalDateTime() // Convert to java.time
//            null -> null // Handle null explicitly
//            else -> throw IllegalArgumentException("Unexpected type for valueToDB: $value")
//        }
//    }

//    override fun valueFromDB(value: Any): KLocalDateTime {
//        return when (value) {
//            is LocalDateTime -> value.toKotlinLocalDateTime()
//            is String -> LocalDateTime.parse(value).toKotlinLocalDateTime()
//            else -> throw IllegalArgumentException("Unexpected type for valueFromDB: $value")
//        }
//    }
//
//    override fun nullableValueToDB(value: Any?): Any? {
//        return when (value) {
//            is kotlinx.datetime.LocalDateTime -> value.toJavaLocalDateTime() // Convert to java.time.LocalDateTime
//            null -> null // Handle null values
//            else -> throw IllegalArgumentException("Unexpected type for nullableValueToDB: $value")
//        }
//    }



//}