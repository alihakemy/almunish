package com.refreshing.fcm.broadCast

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.refreshing.datalayer.models.New


const val UPLOAD_COMPLETE_ACTION="update"
const val UPLOAD_POST_EXTRA_NAME="update"

object LocalBroadCast {

    fun sendBroadCastPostUploaded(context: Context,new: New) {
        val intent = Intent(UPLOAD_COMPLETE_ACTION)
        intent.putExtra(UPLOAD_POST_EXTRA_NAME,new)
        LocalBroadcastManager.getInstance(context)
            .sendBroadcast(intent)


    }



}