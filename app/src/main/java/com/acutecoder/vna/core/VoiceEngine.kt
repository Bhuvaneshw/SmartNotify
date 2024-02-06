package com.acutecoder.vna.core

import android.content.Context
import android.media.AudioAttributes
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.acutecoder.vna.data.NotificationData
import com.acutecoder.vna.ui.log

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
    private var queue: MutableList<Triple<String, String, Boolean>>? = mutableListOf()
    private var isSpeaking = false
    private var speakingPack: String? = ""
    private lateinit var localStorage: LocalStorage

    fun init(context: Context, localStorage: LocalStorage) {
        this.localStorage = localStorage
        speech = TextToSpeech(context) {
            ready = true
            speech.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            )
            queue?.let { queue ->
                while (queue.size > 0) {
                    val (msg, id, flush) = queue[0]
                    speak(msg, id, flush)
                    queue.removeAt(0)
                }
            }
            queue = null
        }
        speech.setOnUtteranceProgressListener(this)
    }

    override fun onStart(utteranceId: String?) {
        isSpeaking = true
    }

    override fun onDone(utteranceId: String?) {
        isSpeaking = false
    }

    @Deprecated("Deprecated in Java")
    override fun onError(utteranceId: String?) {
    }

    private fun speak(msg: String, id: String, flush: Boolean = false) {
        if (ready) {
            isSpeaking = true
            speech.speak(
                msg.apply { log("Speaking") },
                if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD,
                null,
                id
            )
        } else addOrCreateQueue(Triple(msg, id, flush))
    }

    private fun addOrCreateQueue(pair: Triple<String, String, Boolean>) {
        if (queue == null) queue = mutableListOf(pair)
    }

    fun speak(map: MutableMap<String, NotificationData>, pack: String?) {
        this.map = map
        val msg = map[pack]
        val isFromSamePack = speakingPack == pack
        val flush = isSpeaking && isFromSamePack

        speakingPack = pack
        if (msg?.canAlert == true) {
            val count =
                if (msg.count == 1) "is a message"
                else "are ${msg.count} messages"
            val from = msg.appName.let {
                if (it != null) " from $it"
                else ""
            }

            speak(
                (if (isSpeaking && !isFromSamePack)
                    localStorage.speakingFormatAppend
                else localStorage.speakingFormat).namedFormat(
                    msg.count,
                    count,
                    from,
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
    count: Int,
    formattedCount: String,
    from: String,
    title: String,
    text: String,
    ticker: String
) =
    replace("\$formattedCountWOAre",  if (count == 1) "a message" else "$count messages")
        .replace("\$formattedCount", formattedCount)
        .replace("\$fromAppName", from)
        .replace("\$title", title)
        .replace("\$text", text)
        .replace("\$ticker", ticker)
