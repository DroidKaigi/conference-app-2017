package io.github.droidkaigi.confsched2017.viewmodel;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Topic;

enum TopicColor {

    PRODUCTIVITY_AND_TOOLING(1, R.color.material_light_green_a700_alpha_15, R.color.material_light_green_a700_alpha_50,
            R.color.material_light_green_a700, R.style.AppTheme_NoActionBar_LightGreen),

    ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY(2, R.color.material_amber_a700_alpha_15, R.color.material_amber_a700_alpha_50,
            R.color.material_amber_a700, R.style.AppTheme_NoActionBar_Yellow),

    HARDWARE(3, R.color.material_red_a700_alpha_15, R.color.material_red_a700_alpha_50,
            R.color.material_red_a700, R.style.AppTheme_NoActionBar_Red),

    UI_AND_DESIGN(4, R.color.material_blue_a700_alpha_15, R.color.material_blue_a700_alpha_50,
            R.color.material_blue_a700, R.style.AppTheme_NoActionBar_Blue),

    QUALITY_AND_SUSTAINABILITY(5, R.color.material_light_blue_a700_alpha_15, R.color.material_light_blue_a700_alpha_50,
            R.color.material_light_blue_a700, R.style.AppTheme_NoActionBar_LightBlue),

    PLATFORM(6, R.color.material_pink_a700_alpha_15, R.color.material_pink_a700_alpha_50,
            R.color.material_pink_a700, R.style.AppTheme_NoActionBar_Pink),

    OTHER(7, R.color.material_purple_a700_alpha_15, R.color.material_purple_a700_alpha_50,
            R.color.material_purple_a700, R.style.AppTheme_NoActionBar_Purple),

    NONE(0, R.color.material_purple_a700_alpha_15, R.color.material_purple_a700_alpha_50,
            R.color.material_purple_a700, R.style.AppTheme_NoActionBar_Purple);

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
