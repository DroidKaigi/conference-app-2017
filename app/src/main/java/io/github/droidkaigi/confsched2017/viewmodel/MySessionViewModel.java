package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import java.util.Date;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.MySession;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.util.DateUtil;
import io.github.droidkaigi.confsched2017.util.LocaleUtil;
import io.github.droidkaigi.confsched2017.view.activity.MySessionsActivity;
import io.github.droidkaigi.confsched2017.view.helper.Navigator;

public class MySessionViewModel extends BaseObservable implements ViewModel {

    private final Navigator navigator;

    private String sessionTitle;

    private String speakerImageUrl;

    private String sessionTimeRange;

    private int roomVisibility;

    public MySession mySession;

    public MySessionViewModel(Context context, Navigator navigator, MySession mySession) {
        this.navigator = navigator;
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
        // Nothing to do
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

    public void onItemClick(@SuppressWarnings("unused") View view) {
        navigator.navigateToSessionDetail(mySession.session, view, MySessionsActivity.class);
    }

}
