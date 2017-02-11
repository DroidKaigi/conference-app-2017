package io.github.droidkaigi.confsched2017.viewmodel

import android.support.annotation.ColorRes
import android.support.annotation.StyleRes

import io.github.droidkaigi.confsched2017.R
import io.github.droidkaigi.confsched2017.model.Topic

/**
 * Topic ID corresponds to the order of enum.
 * TODO There is not the specification document about topic id yet.
 * But you can see raw json data https://droidkaigi.github.io/2017/sessions.json
 */
enum class TopicColor(
        @JvmField @ColorRes val paleColorResId: Int,
        @JvmField @ColorRes val middleColorResId: Int,
        @JvmField @ColorRes val vividColorResId: Int,
        @JvmField @StyleRes val themeId: Int) {

    NONE(R.color.purple_alpha_15, R.color.purple_alpha_50,
            R.color.purple, R.style.AppTheme_NoActionBar_Purple),

    PRODUCTIVITY_AND_TOOLING(R.color.light_green_alpha_15, R.color.light_green_alpha_50,
            R.color.light_green, R.style.AppTheme_NoActionBar_LightGreen),

    ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY(R.color.yellow_alpha_15, R.color.yellow_alpha_50,
            R.color.yellow, R.style.AppTheme_NoActionBar_Yellow),

    HARDWARE(R.color.red_alpha_15, R.color.red_alpha_50,
            R.color.red, R.style.AppTheme_NoActionBar_Red),

    UI_AND_DESIGN(R.color.blue_alpha_15, R.color.blue_alpha_50,
            R.color.blue, R.style.AppTheme_NoActionBar_Blue),

    QUALITY_AND_SUSTAINABILITY(R.color.light_blue_alpha_15, R.color.light_blue_alpha_50,
            R.color.light_blue, R.style.AppTheme_NoActionBar_LightBlue),

    PLATFORM(R.color.pink_alpha_15, R.color.pink_alpha_50,
            R.color.pink, R.style.AppTheme_NoActionBar_Pink),

    OTHER(R.color.purple_alpha_15, R.color.purple_alpha_50,
            R.color.purple, R.style.AppTheme_NoActionBar_Purple);

    companion object {
        @JvmStatic
        fun from(topic: Topic?) =
                topic?.let {
                    when {
                        it.id < 0 || it.id >= TopicColor.values().size ->
                            NONE
                        else ->
                            TopicColor.values()[it.id]
                    }
                } ?: NONE
    }
}
