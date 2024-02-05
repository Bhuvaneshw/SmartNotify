package com.acutecoder.vna.core

import android.content.Context
import android.media.AudioAttributes
import android.speech.tts.TextToSpeech
import com.acutecoder.vna.service.NotificationData
import com.acutecoder.vna.ui.log

/**
 * Created by Bhuvaneshwaran
 *
 * 10:09 pm 02-02-2024
 * @author AcuteCoder
 */

object VoiceEngine {

    private lateinit var speech: TextToSpeech
    private var ready = false
    private var queue: MutableList<Pair<String, Boolean>>? = mutableListOf()
    private val isSpeaking: Boolean
        get() = speech.isSpeaking
    private var speakingPack: String? = ""

    fun init(context: Context) {
        speech = TextToSpeech(context) {
            ready = true
            speech.setAudioAttributes(
                AudioAttributes.Builder()
                    .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            )
            queue?.let { queue ->
                while (queue.size > 0) {
                    val (msg, flush) = queue[0]
                    speak(msg, flush)
                    queue.removeAt(0)
                }
            }
            queue = null
        }
    }

    fun speak(msg: String, flush: Boolean = false) {
        if (ready)
            speech.speak(
                msg.apply { log("Speaking") },
                if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD,
                null,
                null
            )
        else addOrCreateQueue(msg to flush)
    }

    private fun addOrCreateQueue(pair: Pair<String, Boolean>) {
        if (queue == null)
            queue = mutableListOf(pair)
    }

    fun flushAll() {
        speech.stop()
    }

    fun speak(map: MutableMap<String, NotificationData>, pack: String?, appName: String?) {
        val msg = map[pack]
        val flush = isSpeaking && speakingPack == pack

        speakingPack = pack
        if (msg?.alert == true) {
            val count =
                if (msg.count == 1) "is a message"
                else "are ${msg.count} messages"
            val from = appName.let {
                if (it != null) " from $it"
                else ""
            }

            speak("Sir there $count $from", flush)
        }
    }
}
