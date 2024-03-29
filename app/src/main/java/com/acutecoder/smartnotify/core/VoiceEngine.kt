package com.acutecoder.smartnotify.core

import android.content.Context
import android.media.AudioAttributes
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.acutecoder.smartnotify.data.NotificationData
import com.acutecoder.smartnotify.ui.log

/**
 * Created by Bhuvaneshwaran
 *
 * 10:09 pm 02-02-2024
 * @author AcuteCoder
 */

object VoiceEngine : UtteranceProgressListener() {

    private var map: MutableMap<String, NotificationData> = mutableMapOf()
    private lateinit var speech: TextToSpeech
    private var ready = false
    private val messages: MutableMap<String, Pair<String, Boolean>> = mutableMapOf()
    private var queue: MutableList<String> = mutableListOf()
    private var isSpeaking = false
    private var speakingPack: String? = ""
    private lateinit var localStorage: LocalStorage

    fun init(context: Context, localStorage: LocalStorage, callback: (Boolean) -> Unit) {
        this.localStorage = localStorage
        speech = TextToSpeech(context) {
            ready = it == TextToSpeech.SUCCESS
            callback(ready)

            speech.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            )
            dispatchFirst()
        }
        speech.setOnUtteranceProgressListener(this)
    }

    override fun onStart(utteranceId: String?) {
        isSpeaking = true
    }

    override fun onDone(utteranceId: String?) {
        isSpeaking = false
        dispatchFirst()
    }

    private fun dispatchFirst() {
        if (queue.isNotEmpty()) {
            val id = queue[0]
            queue.removeAt(0)
            if (!messages.containsKey(id)) {
                dispatchFirst()
                return
            }

            val (msg, flush) = messages[id]!!
            messages.remove(id)
            msg.log("Removed")
            speak(
                if (messages.isEmpty()) " and $msg" else msg,
                id, flush
            )
        }
    }

    private fun enqueue(id: String, data: Pair<String, Boolean>) {
        if (data.second) {
            queue.removeIf {
                it == data.first
            }
        }
        messages[id] = data
        queue.add(id)
        data.first.log("Added")
    }

    @Deprecated("Deprecated in Java")
    override fun onError(utteranceId: String?) {
    }

    private fun speak(msg: String, id: String, flush: Boolean = false) {
        if (ready) {
            if (isSpeaking && speakingPack != id) {
                enqueue(id, Pair(msg, flush))
                return
            }
            isSpeaking = true
            speakingPack = id
            speech.speak(
                msg.apply { log("Speaking") },
                if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD,
                null,
                id
            )
        } else enqueue(id, Pair(msg, flush))
    }

    fun speak(map: MutableMap<String, NotificationData>, pack: String?) {
        this.map = map
        val msg = map[pack]
        val isFromSamePack = speakingPack == pack
        val flush = isSpeaking && isFromSamePack

        if (msg?.canAlert == true) {
            val count =
                if (msg.count == 1) "a"
                else "${msg.count}"
            val isOrAre =
                if (msg.count == 1) "is"
                else "are"
            val needS = msg.count != 1

            val from = msg.appName.let {
                if (it != null) " from $it"
                else ""
            }

            speak(
                (if (isSpeaking && !isFromSamePack)
                    localStorage.speakingMessage
                else localStorage.speakingFormat).namedFormat(
                    isOrAre,
                    count,
                    from,
                    needS,
                    msg.title ?: "",
                    msg.text ?: "",
                    msg.ticker ?: ""
                ),
                pack ?: "null",
                flush
            )
        }
    }
}

private fun String.namedFormat(
    isOrAre: String,
    formattedCount: String,
    from: String,
    needS: Boolean,
    title: String,
    text: String,
    ticker: String
) =
    replace("\$isOrAre", isOrAre)
        .replace("\$formattedCount", formattedCount)
        .replace("\$fromAppName", from)
        .replace("\$title", title)
        .replace("\$text", text)
        .replace("\$ticker", ticker)
        .replace("\$addSIfRequired", if (needS) "s" else "")
