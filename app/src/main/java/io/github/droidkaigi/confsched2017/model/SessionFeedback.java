package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Table
public class SessionFeedback {

    @PrimaryKey(auto = false)
    @Column(indexed = true)
    @SerializedName("session_id")
    public int sessionId;

    @Column
    @SerializedName("session_title")
    public String sessionTitle;

    @Column
    @SerializedName("relevancy")
    public int relevancy;

    @Column
    @SerializedName("as_expected")
    public int asExpected;

    @Column
    @SerializedName("difficulty")
    public int difficulty;

    @Column
    @SerializedName("knowledgeable")
    public int knowledgeable;

    @Column
    @Nullable
    @SerializedName("comment")
    public String comment;

    @Column
    @SerializedName("is_submitted")
    public boolean isSubmitted;

    public SessionFeedback() {

    }

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
