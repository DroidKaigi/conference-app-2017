package io.github.droidkaigi.confsched2017.model;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import io.github.droidkaigi.confsched2017.R;

public enum TopicColor {

    PRODUCTIVITY_AND_TOOLING(1, R.color.light_green_alpha_15, R.color.light_green_alpha_50,
            R.color.light_green, R.style.AppTheme_NoActionBar_LightGreen),

    ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY(2, R.color.yellow_alpha_15, R.color.yellow_alpha_50,
            R.color.yellow, R.style.AppTheme_NoActionBar_Yellow),

    HARDWARE(3, R.color.red_alpha_15, R.color.red_alpha_50,
            R.color.red, R.style.AppTheme_NoActionBar_Red),

    UI_AND_DESIGN(4, R.color.blue_alpha_15, R.color.blue_alpha_50,
            R.color.blue, R.style.AppTheme_NoActionBar_Blue),

    QUALITY_AND_SUSTAINABILITY(5, R.color.light_blue_alpha_15, R.color.light_blue_alpha_50,
            R.color.light_blue, R.style.AppTheme_NoActionBar_LightBlue),

    PLATFORM(6, R.color.pink_alpha_15, R.color.pink_alpha_50,
            R.color.pink, R.style.AppTheme_NoActionBar_Pink),

    OTHER(7, R.color.purple_alpha_15, R.color.purple_alpha_50,
            R.color.purple, R.style.AppTheme_NoActionBar_Purple),

    NONE(0, R.color.purple_alpha_15, R.color.purple_alpha_50,
            R.color.purple, R.style.AppTheme_NoActionBar_Purple);

    private int topicId;

    @ColorRes
    public int paleColorResId;

    @ColorRes
    public int middleColorResId;

    @ColorRes
    public int vividColorResId;

    @StyleRes
    public int themeId;

    TopicColor(int topicId, @ColorRes int paleColorResId, @ColorRes int middleColorResId,
            @ColorRes int vividColorResId, @StyleRes int themeId) {
        this.topicId = topicId;
        this.paleColorResId = paleColorResId;
        this.middleColorResId = middleColorResId;
        this.vividColorResId = vividColorResId;
        this.themeId = themeId;
    }

    public static TopicColor from(@Nullable Topic topic) {
        if (topic == null) {
            return NONE;
        } else if (PRODUCTIVITY_AND_TOOLING.topicId == topic.id) {
            return PRODUCTIVITY_AND_TOOLING;
        } else if (ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY.topicId == topic.id) {
            return ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY;
        } else if (HARDWARE.topicId == topic.id) {
            return HARDWARE;
        } else if (UI_AND_DESIGN.topicId == topic.id) {
            return UI_AND_DESIGN;
        } else if (QUALITY_AND_SUSTAINABILITY.topicId == topic.id) {
            return QUALITY_AND_SUSTAINABILITY;
        } else if (PLATFORM.topicId == topic.id) {
            return PLATFORM;
        } else if (OTHER.topicId == topic.id) {
            return OTHER;
        } else {
            return NONE;
        }
    }

}
