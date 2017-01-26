package io.github.droidkaigi.confsched2017.viewmodel;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
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

    private int titleMaxLines = 4;

    private int speakerNameMaxLines = 1;

    @DrawableRes
    private int backgroundResId;

    @ColorRes
    private int categoryColorResId;

    private boolean isClickable;

    private int checkVisibility;

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

        this.backgroundResId = R.drawable.clickable_white;
        this.categoryColorResId = decideCategoryColorResId(session.topic);
        this.isClickable = true;

        this.checkVisibility = isMySession ? View.VISIBLE : View.GONE;
    }

    private SessionViewModel(int rowSpan, int colSpan) {
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.backgroundResId = R.drawable.bg_empty_session;
        this.categoryColorResId = android.R.color.transparent;
        this.checkVisibility = View.GONE;
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
            case 1:
                return R.color.light_green_alpha_50;
            case 2:
                return R.color.ultra_light_blue_alpha_50;
            case 3:
                return R.color.light_blue_alpha_50;
            case 4:
                return R.color.blue_alpha_50;
            case 5:
                return R.color.light_green_alpha_50;
            case 6:
                return R.color.pink_alpha_50;
            case 7:
                return R.color.red_alpha_50;
            case 8:
                return R.color.yellow_alpha_50;
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
        if (session.durationMin > 30) {
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

    @BindingAdapter("twowayview_rowSpan")
    public static void setTwowayViewRowSpan(View view, int rowSpan) {
        final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.rowSpan = rowSpan;
        view.setLayoutParams(lp);
    }

    @BindingAdapter("twowayview_colSpan")
    public static void setTwowayViewColSpan(View view, int colSpan) {
        final SpannableGridLayoutManager.LayoutParams lp = (SpannableGridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.colSpan = colSpan;
        view.setLayoutParams(lp);
    }

    @BindingAdapter("sessionCellBackground")
    public static void setSessionCellBackground(View view, @DrawableRes int backgroundResId) {
        view.setBackgroundResource(backgroundResId);
    }

    @BindingAdapter("sessionCategoryColor")
    public static void setSessionCategoryColor(View view, @ColorRes int colorResId) {
        if (colorResId > 0) {
            view.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), colorResId, null));
        }
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
