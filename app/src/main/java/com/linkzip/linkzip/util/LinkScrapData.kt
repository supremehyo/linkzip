@file:Suppress("UNNECESSARY_SAFE_CALL")

package com.linkzip.linkzip.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.linkzip.linkzip.data.room.LinkData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
suspend fun LinkScrapData(url : String) : LinkData? {
    if(isUrl(url)){
        var resultLinkData = LinkData(
            link = url,
            linkGroupId = "",//분류되지 않음 이라는 그룹이 자동으로 생성되어야 할듯함.
            linkTitle = "",
            linkMemo = "",
            createDate = "",
            updateDate = "",
            linkThumbnail = "",
            favorite = false
        )
        var job = CoroutineScope(Dispatchers.IO).async {
            val document = Jsoup.connect(url).get()
            val elements = document.select("meta[property^=og:]")
            elements.let {
                it.forEach { el ->
                    when (el.attr("property")) {
                        "og:url" -> el.attr("content")?.let { content ->
                            Log.e("og:url" , content)
                        }
                        "og:site_name" -> {
                            el.attr("content")?.let { content ->
                                Log.e("og:site_name" , content)
                            }
                        }
                        "og:title" -> {
                            el.attr("content")?.let { content ->
                                Log.e("og:title" , content)
                                resultLinkData = resultLinkData.copy(linkTitle = content)
                            }
                        }
                        "og:description" -> {
                            el.attr("content")?.let { content ->
                                Log.e("og:description" , content)
                                resultLinkData = resultLinkData.copy(linkMemo = content)
                                Log.e("data" , "$resultLinkData")
                            }
                        }
                        "og:image" -> {
                            Log.e("og:image" , el.attr("content"))
                        }
                    }
                }
            }
            resultLinkData = resultLinkData.copy(createDate = getCurrentDateFormatted())
            resultLinkData = resultLinkData.copy(updateDate = getCurrentDateFormatted())
        }
        job.await()
        return resultLinkData
    }else{
        return null
    }
}

fun isUrl(str: String): Boolean {
    val urlRegex = """^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$""".toRegex(RegexOption.IGNORE_CASE)
    return urlRegex.matches(str)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateFormatted(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return currentDate.format(formatter)
}