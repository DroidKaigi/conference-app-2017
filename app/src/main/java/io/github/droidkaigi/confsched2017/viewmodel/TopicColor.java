package io.github.droidkaigi.confsched2017.viewmodel;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Topic;

/**
 * Topic ID corresponds to the order of enum.
 * TODO There is not the specification document about topic id yet.
 * But you can see raw json data https://droidkaigi.github.io/2017/sessions.json
 */
enum TopicColor {

    NONE(R.color.material_purple_a700_alpha_15, R.color.material_purple_a700_alpha_50,
            R.color.material_purple_a700, R.style.AppTheme_NoActionBar_Purple),

    PRODUCTIVITY_AND_TOOLING(R.color.material_light_green_a700_alpha_15, R.color.material_light_green_a700_alpha_50,
            R.color.material_light_green_a700, R.style.AppTheme_NoActionBar_LightGreen),

    ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY(R.color.material_amber_a700_alpha_15, R.color.material_amber_a700_alpha_50,
            R.color.material_amber_a700, R.style.AppTheme_NoActionBar_Yellow),

    HARDWARE(R.color.material_red_a700_alpha_15, R.color.material_red_a700_alpha_50,
            R.color.material_red_a700, R.style.AppTheme_NoActionBar_Red),

    UI_AND_DESIGN(R.color.material_blue_a700_alpha_15, R.color.material_blue_a700_alpha_50,
            R.color.material_blue_a700, R.style.AppTheme_NoActionBar_Blue),

    QUALITY_AND_SUSTAINABILITY(R.color.material_light_blue_a700_alpha_15, R.color.material_light_blue_a700_alpha_50,
            R.color.material_light_blue_a700, R.style.AppTheme_NoActionBar_LightBlue),

    PLATFORM(R.color.material_pink_a700_alpha_15, R.color.material_pink_a700_alpha_50,
            R.color.material_pink_a700, R.style.AppTheme_NoActionBar_Pink),

    OTHER(R.color.material_purple_a700_alpha_15, R.color.material_purple_a700_alpha_50,
            R.color.material_purple_a700, R.style.AppTheme_NoActionBar_Purple);

    @ColorRes
    public int paleColorResId;

    @ColorRes
    public int middleColorResId;

    @ColorRes
    public int vividColorResId;

    @StyleRes
    public int themeId;

    TopicColor(@ColorRes int paleColorResId, @ColorRes int middleColorResId,
            @ColorRes int vividColorResId, @StyleRes int themeId) {
        this.paleColorResId = paleColorResId;
        this.middleColorResId = middleColorResId;
        this.vividColorResId = vividColorResId;
        this.themeId = themeId;
    }

    public static TopicColor from(@Nullable Topic topic) {
        if (topic == null) {
            return NONE;
        }
        if (topic.id < 0) {
            return NONE;
        }
        if (topic.id >= TopicColor.values().length) {
            return NONE;
        }
        return TopicColor.values()[topic.id];
    }
}
