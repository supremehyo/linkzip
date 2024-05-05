package com.linkzip.linkzip.presentation

import android.content.Context
import android.util.Log
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.linkzip.linkzip.data.room.LinkData
import com.linkzip.linkzip.data.room.LinkRoomDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type


class FavoriteWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = FavoriteWidget
}


object FavoriteWidget: GlanceAppWidget() {
    val links = stringPreferencesKey("links")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val linkLists = currentState(key = links) ?: ""
            Column(
                modifier = GlanceModifier.fillMaxSize()
                    .background(GlanceTheme.colors.background),
                verticalAlignment = Alignment.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "즐겨찾는 링크", modifier = GlanceModifier.padding(12.dp))
                val jsonList = linkLists
                val type: Type = object : TypeToken<List<LinkData?>?>() {}.type
                val list: List<LinkData> = try {
                    Gson().fromJson(jsonList , type)
                } catch (e: Exception) {
                    emptyList()
                }
                list.forEach { linkData ->
                    Text(text = "Title: ${linkData.linkTitle}, URL: ${linkData.linkTitle}")
                }
                Button(text = "새로고침", onClick = actionRunCallback<ReloadActionCallback>())
            }
        }
    }
}
object ReloadActionCallback: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val room = Room.databaseBuilder(
            context,
            LinkRoomDataBase::class.java ,"linkzip-database.db"
        ).build()

        val list : List<LinkData> = withContext(Dispatchers.IO) {
            room.linkDao().getFavoriteLinkList()
        }
        Log.e("getFavoriteLinkList" , "$list")
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[FavoriteWidget.links] = Gson().toJson(list)
        }
        FavoriteWidget.update(context, glanceId)
    }
}
