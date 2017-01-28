package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Date;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.model.Topic;
import io.github.droidkaigi.confsched2017.util.DateUtil;

public class SessionViewModel extends BaseObservable implements ViewModel {

    private Session session;

    private String shortStime = "";

    private String formattedDate = "";

    private String title = "";

    private String speakerName = "";

    private String roomName = "";

    private String languageId = "";

    private String minutes = "";

    private int rowSpan = 1;

    private int colSpan = 1;

    private int titleMaxLines = 3;

    private int speakerNameMaxLines = 1;

    @DrawableRes
    private int backgroundResId;

    @ColorRes
    private int categoryColorResId;

    private boolean isClickable;

    private int checkVisibility;

    private int normalSessionItemVisibility;

    private Callback callback;

    SessionViewModel(@NonNull Session session, Context context, int roomCount, boolean isMySession) {
        this.session = session;
        this.shortStime = DateUtil.getHourMinute(session.stime);
        this.formattedDate = DateUtil.getMonthDate(session.stime, context);
        this.title = session.title;

        if (session.speaker != null) {
            this.speakerName = session.speaker.name;
        }
        if (session.room != null) {
            this.roomName = session.room.name;
        }
        if (session.lang != null) {
            this.languageId = session.lang.toUpperCase();
        }

        this.minutes = context.getString(R.string.session_minutes, session.durationMin);

        decideRowSpan(session);
        this.colSpan = decideColSpan(session, roomCount);

        this.checkVisibility = isMySession ? View.VISIBLE : View.GONE;

        if (session.isBreak()) {
            this.isClickable = true;
            this.backgroundResId = R.drawable.bg_empty_session;
            this.categoryColorResId = android.R.color.transparent;
            this.normalSessionItemVisibility = View.GONE;
        } else {
            this.isClickable = false;
            this.backgroundResId = R.drawable.clickable_white;
            this.categoryColorResId = decideCategoryColorResId(session.topic);
            this.normalSessionItemVisibility = View.VISIBLE;
        }

    }

    private SessionViewModel(int rowSpan, int colSpan) {
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.backgroundResId = R.drawable.bg_empty_session;
        this.categoryColorResId = android.R.color.transparent;
        this.checkVisibility = View.GONE;
        this.normalSessionItemVisibility = View.GONE;
    }

    static SessionViewModel createEmpty(int rowSpan) {
        return createEmpty(rowSpan, 1);
    }

    static SessionViewModel createEmpty(int rowSpan, int colSpan) {
        return new SessionViewModel(rowSpan, colSpan);
    }

    private int decideCategoryColorResId(@Nullable Topic topic) {
        if (topic == null) {
            return R.color.purple_alpha_15;
        }

        switch (topic.id) {
            case Topic.ID_PRODUCTIVITY_AND_TOOLING:
                return R.color.light_green_alpha_50;
            case Topic.ID_ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY:
                return R.color.yellow_alpha_50;
            case Topic.ID_HARDWARE:
                return R.color.red_alpha_50;
            case Topic.ID_UI_AND_DESIGN:
                return R.color.blue_alpha_50;
            case Topic.ID_QUALITY_AND_SUSTAINABILITY:
                return R.color.light_green_alpha_50;
            case Topic.ID_PLATFORM:
                return R.color.pink_alpha_50;
            case Topic.ID_OTHER:
                return R.color.purple_alpha_50;
            default:
                return R.color.purple_alpha_50;
        }
    }

    private int decideColSpan(@NonNull Session session, int roomCount) {
        if (session.isCeremony()) {
            return 3;
        } else if (session.isBreak()) {
            return roomCount;
        } else {
            return 1;
        }
    }

    private void decideRowSpan(@NonNull Session session) {
        // Break time is over 30 min, but one row is good
        if (session.durationMin > 30 && !session.isBreak()) {
            this.rowSpan = this.rowSpan * 2;
            this.titleMaxLines = this.titleMaxLines * 2;
            this.speakerNameMaxLines = this.speakerNameMaxLines * 3;
        }
    }

    Date getStime() {
        return session.stime;
    }

    public void showSessionDetail(@SuppressWarnings("unused") View view) {
        if (callback != null && session != null) {
            callback.showSessionDetail(session);
        }
    }

    @Override
    public void destroy() {
        callback = null;
    }

    public String getShortStime() {
        return shortStime;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    String getRoomName() {
        return roomName;
    }

    public String getLanguageId() {
        return languageId;
    }

    public String getMinutes() {
        return minutes;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public int getTitleMaxLines() {
        return titleMaxLines;
    }

    public int getSpeakerNameMaxLines() {
        return speakerNameMaxLines;
    }

    public int getCategoryColorResId() {
        return categoryColorResId;
    }

    public int getNormalSessionItemVisibility() {
        return normalSessionItemVisibility;
    }

    @Bindable
    public int getCheckVisibility() {
        return checkVisibility;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void showSessionDetail(@NonNull Session session);
    }
}
