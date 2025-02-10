package com.wini.owle.datapoints

import androidx.compose.runtime.*
//import com.wini.owle.view.ViewState
//
//@Composable
//fun DataPointListScreen() {
//    val scope = rememberCoroutineScope()
//    //val dataPointService: IService<DataPointModel> = DataPointService()
//    var viewState by remember { mutableStateOf<ViewState<List<*>>>(ViewState.Loading) }
//
//    LaunchedEffect(Unit) {
//        scope.launch {
//            viewState = ViewState.Loading
//            //val result = dataPointService.getAll()
//            //viewState = result.toViewState()
//        }
//    }
//
//    when (val state = viewState) {
//        is ViewState.Loading -> {
//            // Show loading indicator
//        }
//
//        is ViewState.Success<*> -> {
//            // Display list of DataPoints
//            val dataPoints = state.data
//            // ...
//        }
//
//        is ViewState.Error -> {
//            // Show error message
//
//        }
//    }
//}