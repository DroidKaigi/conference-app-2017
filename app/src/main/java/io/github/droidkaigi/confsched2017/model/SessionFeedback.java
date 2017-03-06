package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class SessionFeedback {

    @SerializedName("entry.1298546024")
    public final int sessionId;

    @SerializedName("entry.413792998")
    public final String sessionTitle;

    @SerializedName("entry.335146475")
    public final int relevancy;

    @SerializedName("entry.1916895481")
    public final int asExpected;

    @SerializedName("entry.1501292277")
    public final int difficulty;

    @SerializedName("entry.2121897737")
    public final int knowledgeable;

    @SerializedName("entry.645604473")
    public final String comment;

    public SessionFeedback(@NonNull Session session, int relevancy, int asExpected,
            int difficulty, int knowledgeable, @Nullable String comment) {
        this.sessionId = session.id;
        this.sessionTitle = session.title;
        this.relevancy = relevancy;
        this.asExpected = asExpected;
        this.difficulty = difficulty;
        this.knowledgeable = knowledgeable;
        this.comment = comment;
    }

    public boolean isAllFilled() {
        return sessionId > 0
                && sessionTitle != null
                && relevancy > 0
                && asExpected > 0
                && difficulty > 0
                && knowledgeable > 0;
    }
}
