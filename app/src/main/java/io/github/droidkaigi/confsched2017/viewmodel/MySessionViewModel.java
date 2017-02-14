package io.github.droidkaigi.confsched2017.viewmodel;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import io.github.droidkaigi.confsched2017.model.MySession;

public class MySessionViewModel extends BaseObservable implements ViewModel {

    private static final String ELLIPSIZE_TEXT = "...";

    private static final int ELLIPSIZE_LIMIT_COUNT = 30;

    private String sessionTitle;

    private String speakerImageUrl;

    private String text;

    private MySession mySession;

    private Callback callback;

    private boolean shouldEllipsis;

    public MySessionViewModel(MySession mySession) {
        this.text = mySession.session.desc;
        this.sessionTitle = mySession.session.title;
        if (mySession.session.speaker != null) {
            this.speakerImageUrl = mySession.session.speaker.imageUrl;
        }
        this.shouldEllipsis = true; // TODO
        this.mySession = mySession;
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
