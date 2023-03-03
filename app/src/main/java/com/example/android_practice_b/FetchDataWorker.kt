package com.example.android_practice_b

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay


class FetchDataWorker(private val context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {


    private lateinit var notificationManager: NotificationManager
    lateinit var notification2 :Notification
    val CHANNEL_ID = "NOTIFI_CHANNEL"

    val NOTIFICATION_ID = 1

    @SuppressLint("ServiceCast")
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {

        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        val notification1 = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("My Foreground Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.notify_ic)
            .setPriority(NotificationCompat.PRIORITY_HIGH)


        val notificationInfo = ForegroundInfo(NOTIFICATION_ID,notification1.build())
        setForeground(notificationInfo)

        try {

            val dataList = getAllData()
            val total = dataList.size
            var count = 0
            var fetched = false



            dataList.forEach {
                count++
                val progress = ((count + 1) * 100f / total).toInt()
                delay(100)

                 notification2 = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("My Foreground Service")
                    .setSmallIcon(R.drawable.notify_ic)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setProgress(100, progress, false)
                    .setContentText("Fetched ${count+1} out of $total")
                     .build()
                notificationManager.notify(NOTIFICATION_ID, notification2)
                if (progress == 100) {
                    notification2 = NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentTitle("My Foreground Service")
                        .setSmallIcon(R.drawable.notify_ic)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setProgress(100, 100, false)
                        .setContentText("Fetched ${progress} out of $total")
                        .build()
                    fetched = true
                    return Result.success()
                }
            }


            notificationManager.notify(NOTIFICATION_ID, notification2)
            notificationManager.cancel(NOTIFICATION_ID)

            return Result.success()


        } catch (e: java.lang.Exception) {
            Log.d("WORKER_EXCEPTION", e.message.toString())
            return Result.failure()

        }

    }

    fun getAllData(): List<String> {
        val mediaList = mutableListOf<String>()

        val uri = MediaStore.Files.getContentUri("external")

        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DATA
        )
        val selection =
            "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE} = ? "
        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )


        val cursor = applicationContext.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {

            val dataColum = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val data = it.getString(dataColum)

                mediaList.add(data)
            }
        }

        return mediaList
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Downloading"
            notificationManager.createNotificationChannel(channel)
        }
    }


}