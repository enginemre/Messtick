package com.messtick.app.core.data.worker

import kotlinx.cinterop.ExperimentalForeignApi
import platform.BackgroundTasks.BGAppRefreshTaskRequest
import platform.BackgroundTasks.BGProcessingTaskRequest
import platform.BackgroundTasks.BGTask
import platform.BackgroundTasks.BGTaskScheduler
import platform.Foundation.NSBundle
import platform.Foundation.NSDate
import platform.Foundation.NSLog
import platform.Foundation.dateWithTimeIntervalSinceNow

actual class SyncMessageWorker {

    private val taskID = "com.messtick.app.sync.message"
    actual fun syncMessages() {
            BGTaskScheduler.sharedScheduler.registerForTaskWithIdentifier(
                identifier =taskID,
                usingQueue = null
            ) { task ->
                NSLog("Task Triggered ")
                handleBackgroundTask(task)
            }

            scheduleTask()

    }

    private fun handleBackgroundTask(task: BGTask?) {
        task?.expirationHandler = {
            NSLog("Task expired before completion")
        }
        NSLog("Task successfully completed")
        task?.setTaskCompletedWithSuccess(true)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun scheduleTask() {
        val request = BGAppRefreshTaskRequest(identifier = taskID)
        request.earliestBeginDate = NSDate.dateWithTimeIntervalSinceNow(1 * 60 * 60.0)
        try {
            BGTaskScheduler.sharedScheduler.submitTaskRequest(request, null)
            NSLog("Task scheduled successfully")
        } catch (e: Exception) {
            NSLog("Failed to schedule task: ${e.message}")
        }
    }
}