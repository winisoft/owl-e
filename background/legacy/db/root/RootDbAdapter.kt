package com.wini.owle.air.db.root

import com.wini.owle.air.db.InlineAdapters
import com.squareup.sqldelight.ColumnAdapter

object RootDbAdapter {
    val instance = object {

    }
//        .Adapter(
//        idAdapter = InlineAdapters.Long.loRootItemId,
//        isExpandedAdapter = InlineAdapters.Bool.isExpanded,
//        runningStateAdapter = object: ColumnAdapter<RunningState, Long>,
//            app.cash.sqldelight.ColumnAdapter<RunningState, Long> {
//            override fun encode(value: RunningState): Long = value.key.toLong()
//            override fun decode(databaseValue: Long): RunningState = when (databaseValue) {
//                 0L -> RunningState.Stopped
//                 1L -> RunningState.Running
//                else -> error("Invalid key value for RunningState")
//            }
//        }
//    )
}