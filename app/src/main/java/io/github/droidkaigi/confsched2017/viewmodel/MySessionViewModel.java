package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.MySession;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.util.DateUtil;
import io.github.droidkaigi.confsched2017.util.LocaleUtil;

public class MySessionViewModel extends BaseObservable implements ViewModel {

    private String sessionTitle;

    private String speakerImageUrl;

    private String sessionTimeRange;

    private int roomVisibility;

    public MySession mySession;

    private Callback callback;

    public MySessionViewModel(Context context, MySession mySession) {
        this.sessionTitle = mySession.session.title;
        if (mySession.session.speaker != null) {
            this.speakerImageUrl = mySession.session.speaker.imageUrl;
        }
        this.mySession = mySession;
        this.roomVisibility = mySession.session.room != null ? View.VISIBLE : View.GONE;

        this.sessionTimeRange = decideSessionTimeRange(context, mySession.session);
    }

    @Override
    public void destroy() {
        callback = null;
    }


    public String getSessionTitle() {
        return sessionTitle;
    }

    public String getSpeakerImageUrl() {
        return speakerImageUrl;
    }

    public String getSessionTimeRange() {
        return sessionTimeRange;
    }

    public int getRoomVisibility() {
        return roomVisibility;
    }

    private String decideSessionTimeRange(Context context, Session session) {
        Date displaySTime = LocaleUtil.getDisplayDate(session.stime, context);
        Date displayETime = LocaleUtil.getDisplayDate(session.etime, context);

        return context.getString(R.string.session_time_range,
                DateUtil.getLongFormatDate(displaySTime),
                DateUtil.getHourMinute(displayETime),
                DateUtil.getMinutes(displaySTime, displayETime));
    }


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void onItemClick(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showSessionDetail(mySession);
        }
    }


    public interface Callback {

        void showSessionDetail(@NonNull MySession mySession);
    }



}
