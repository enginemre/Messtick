package com.messtick.app.core.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import io.github.aakira.napier.Napier
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

actual class SyncMessageWorker(
    private val context: Context
) {
    actual fun syncMessages() {
        val workRequest = PeriodicWorkRequestBuilder<SyncMessageWorkerImpl>(
            intervalInMinutes, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "SyncMessagesWorkManager",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )

    }

    companion object{
        private const val intervalInMinutes = 15L
    }
}

class SyncMessageWorkerImpl(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        runBlocking {
           Napier.d("Worker Worked")
        }
        return Result.success()
    }
}