package io.github.droidkaigi.confsched2017.model

import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.BuildConfig
import org.junit.Test

class SpeakerTest {

    @Test
    @Throws(Exception::class)
    fun getAdjustedImageUrlWhenNull() {
        val speaker = Speaker().apply { imageUrl = null }
        speaker.adjustedImageUrl.should be null
    }

    @Test
    @Throws(Exception::class)
    fun getAdjustedImageUrlWhenHttpUrl() {
        val url = "http://droidkaigi.github.io/2017/images/1.jpg"
        val speaker = Speaker().apply { imageUrl = url }
        speaker.adjustedImageUrl.should be url
    }

    @Test
    @Throws(Exception::class)
    fun getAdjustedImageUrlWhenHttpsUrl() {
        val url = "https://droidkaigi.github.io/2017/images/1.jpg"
        val speaker = Speaker().apply { imageUrl = url }
        speaker.adjustedImageUrl.should be url
    }

    @Test
    @Throws(Exception::class)
    fun getAdjustedImageUrlWhenRelativePath() {
        val url = "/images/1.jpg"
        val speaker = Speaker().apply { imageUrl = url }
        speaker.adjustedImageUrl.should be BuildConfig.STATIC_ROOT + url
    }

    @Test
    @Throws(Exception::class)
    fun getAdjustedImageUrlWhenInvalidPath() {
        val url = "images/1.jpg"
        val speaker = Speaker().apply { imageUrl = url }
        speaker.adjustedImageUrl.should be null
    }
}
