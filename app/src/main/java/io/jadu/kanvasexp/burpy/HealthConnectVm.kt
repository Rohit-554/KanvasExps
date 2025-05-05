package io.jadu.kanvasexp.burpy

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_AVAILABLE
import androidx.health.connect.client.HealthConnectClient.Companion.SDK_UNAVAILABLE
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.reflect.KClass

class HealthConnectVM(application: Application) : AndroidViewModel(application) {
   /* private lateinit var application: Context

    fun init(context: Context) {
        application = context.applicationContext
    }*/
    private val application = application.applicationContext

      var healthConnectClient: HealthConnectClient? = null
      var hasAllPermissions by mutableStateOf(false)
      var availability = mutableIntStateOf(SDK_UNAVAILABLE)
          private set

      init {
          checkAvailability()
      }

    fun checkAvailability() {
        //val application = getApplication<Application>()

        availability.intValue = HealthConnectClient.getSdkStatus(application)
        if (availability.intValue == SDK_AVAILABLE) {
            healthConnectClient = HealthConnectClient.getOrCreate(context = application)
            checkAllPermissions()
        }
    }

      private fun checkAllPermissions() {
          viewModelScope.launch {
              val grantedPermissions =
                  healthConnectClient?.permissionController?.getGrantedPermissions()
              hasAllPermissions = grantedPermissions?.containsAll(permissions) == true
              if (hasAllPermissions) {
  //                sample sync
  //                val stepsRecords = readHealthRecordsUtil(
  //                    StepsRecord::class,
  //                    null
  //                )
  //                println(stepsRecords)
              }
          }
      }

      @RequiresApi(Build.VERSION_CODES.O)
      suspend fun <T : androidx.health.connect.client.records.Record> readHealthRecordsUtil(
          recordType: KClass<T>,
          lastSync: Instant?
      ): List<String> {
          val now = Instant.now()
          val startOfToday = now.minusSeconds(now.epochSecond % 86400)
          val time30DaysBefore = startOfToday.minusSeconds(86400 * 30)
          val startTime = if (lastSync == null) {
              time30DaysBefore
          } else if (time30DaysBefore < lastSync) {
              lastSync
          } else {
              time30DaysBefore
          }
          val endTime = startOfToday.plusSeconds(86400)
          val records = readHealthRecords(
              recordType = recordType,
              timeRangeFilter = TimeRangeFilter.between(startTime,endTime)
          )
          return records.toJsonList()
      }


    private fun <T : Record> List<T>.toJsonList(): List<String> {
        return this.map {  Gson().toJson(it) }
    }

    private suspend fun <T : androidx.health.connect.client.records.Record> readHealthRecords(
        recordType: KClass<T>,
        timeRangeFilter: TimeRangeFilter,
        pageToken: String? = null,
        accumulated: MutableList<T> = mutableListOf()
    ): List<T> {
        return try {
            val request = ReadRecordsRequest(
                recordType = recordType,
                timeRangeFilter = timeRangeFilter,
                pageToken = pageToken
            )

            val response = healthConnectClient?.readRecords(request)

            if (response != null) {
                accumulated.addAll(response.records)

                if (!response.pageToken.isNullOrBlank()) {
                    readHealthRecords(
                        recordType = recordType,
                        timeRangeFilter = timeRangeFilter,
                        pageToken = response.pageToken,
                        accumulated = accumulated
                    )
                } else {
                    accumulated
                }
            } else {
                accumulated
            }
        } catch (e: Exception) {
            //burpyLog("HealthConnectError", "$e")
            emptyList()
        }
    }


}
