package me.jovica.app.health.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.jovica.app.health.ui.nav.OneNightDataScreeen
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale


@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    openSettings: () -> Unit,
    openOneNight: (OneNightDataScreeen) -> Unit,
    homeViewModel: HomeViewModel
) {

    val dateRangePickerState = rememberDateRangePickerState()
    var showDateRangePicker by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val formatter = SimpleDateFormat("MMM dd, h:mm:ss a", Locale.getDefault())

    val list by homeViewModel.list.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Js Heath")
                    IconButton(onClick = { openSettings() }) {
                        Icon(Icons.Outlined.Settings, "Settings")
                    }
                }
            })
        }

    ) { padding ->
        Column(Modifier.padding(padding)) {
            Row(Modifier.padding(16.dp, 0.dp)) {
                Button(
                    onClick = {
                        showDateRangePicker = true //changing the visibility state
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Date Picker")
                }
            }
            LazyColumn {
                items(list) {
                    Row(Modifier.padding(16.dp, 8.dp)) {
                        val strat = formatter.format(it.startTime.toEpochMilli())
                        val end = formatter.format(it.endTime.toEpochMilli())

                        Text("$strat - $end ")
                    }
                }
            }
            if (showDateRangePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDateRangePicker = false
                    },
                    confirmButton = {
                        Button({
                            if (dateRangePickerState.selectedEndDateMillis != null && dateRangePickerState.selectedStartDateMillis != null) {
                                showDateRangePicker = false
                                scope.launch {
                                    homeViewModel.getSleepData(
                                        Instant.ofEpochMilli(
                                            dateRangePickerState.selectedStartDateMillis!!
                                        ),
                                        Instant.ofEpochMilli(dateRangePickerState.selectedEndDateMillis!!)
                                    )
                                }

                            }
                        }) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            {
                                showDateRangePicker = false
                            },
                        ) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    DateRangePicker(state = dateRangePickerState)
                }
            }
        }

    }
}

