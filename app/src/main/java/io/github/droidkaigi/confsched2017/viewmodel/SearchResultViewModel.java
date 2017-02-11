package io.github.droidkaigi.confsched2017.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.repository.sessions.MySessionsRepository;

public class SearchResultViewModel extends BaseObservable implements ViewModel {

    private static final String ELLIPSIZE_TEXT = "...";

    private static final int ELLIPSIZE_LIMIT_COUNT = 30;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_TITLE, TYPE_DESCRIPTION, TYPE_SPEAKER})
    public @interface Type {
    }

    public static final int TYPE_TITLE = 1;

    public static final int TYPE_DESCRIPTION = 2;

    public static final int TYPE_SPEAKER = 3;

    private String sessionTitle;

    private String speakerImageUrl;

    private String text;

    private int type;

    private TextAppearanceSpan textAppearanceSpan;

    @DrawableRes
    private int iconResId;

    private boolean isMySession;

    private Session session;

    private Callback callback;

    private boolean shouldEllipsis;

    private SearchResultViewModel(String text, @Type int type, Session session, Context context,
            MySessionsRepository mySessionsRepository) {
        this.text = text;
        this.sessionTitle = session.title;
        if (session.speaker != null) {
            this.speakerImageUrl = session.speaker.imageUrl;
        }
        this.type = type;
        this.iconResId = getIconResId(type);
        this.shouldEllipsis = type == TYPE_DESCRIPTION;
        this.session = session;
        this.textAppearanceSpan = new TextAppearanceSpan(context, R.style.SearchResultAppearance);
        this.isMySession = mySessionsRepository.isExist(session.id);
    }

    @Override
    public void destroy() {
        callback = null;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void onItemClick(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.showSessionDetail(session);
        }
    }

    public boolean match(String filterPattern) {
        return text.toLowerCase().contains(filterPattern);
    }

    public interface Callback {

        void showSessionDetail(@NonNull Session session);
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public String getSpeakerImageUrl() {
        return speakerImageUrl;
    }

    public int getIconResId() {
        return iconResId;
    }

    @Type
    public int getType() {
        return type;
    }

    public SpannableStringBuilder getMatchedText(@Nullable String searchText) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        if (TextUtils.isEmpty(text)) {
            return builder;
        }

        text = text.replace("\n", "  ");

        if (TextUtils.isEmpty(searchText)) {
            return builder.append(text);
        } else {
            int idx = text.toLowerCase().indexOf(searchText.toLowerCase());
            if (idx >= 0) {
                builder.append(text);
                builder.setSpan(
                        textAppearanceSpan,
                        idx,
                        idx + searchText.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                if (idx > ELLIPSIZE_LIMIT_COUNT && shouldEllipsis) {
                    builder.delete(0, idx - ELLIPSIZE_LIMIT_COUNT);
                    builder.insert(0, ELLIPSIZE_TEXT);
                }

                return builder;
            } else {
                return builder.append(text);
            }
        }
    }

    public boolean getIsMySession() {
        return isMySession;
    }

    public int getSearchResultId() {
        return session.id * 10 + getType();
    }

    @DrawableRes
    private static int getIconResId(@Type int type) {
        switch (type) {
            case TYPE_DESCRIPTION:
                return R.drawable.ic_description12_vector;
            case TYPE_SPEAKER:
                return R.drawable.ic_speaker_12_vector;
            case TYPE_TITLE:
                return R.drawable.ic_title_12_vector;
        }
        throw new IllegalStateException("unknown search result type " + type);
    }

    static SearchResultViewModel createTitleType(@NonNull Session session, Context context,
            MySessionsRepository mySessionsRepository) {
        return new SearchResultViewModel(session.title, TYPE_TITLE, session, context, mySessionsRepository);
    }

    static SearchResultViewModel createDescriptionType(@NonNull Session session, Context context,
            MySessionsRepository mySessionsRepository) {
        return new SearchResultViewModel(session.desc, TYPE_DESCRIPTION, session, context, mySessionsRepository);
    }

    static SearchResultViewModel createSpeakerType(@NonNull Session session, Context context,
            MySessionsRepository mySessionsRepository) {
        return new SearchResultViewModel(session.speaker.name, TYPE_SPEAKER, session, context, mySessionsRepository);
    }


}
