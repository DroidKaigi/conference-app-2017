package io.github.droidkaigi.confsched2017.model;

import com.google.gson.annotations.SerializedName;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import android.support.annotation.NonNull;

@Table
public class MySession {

    @PrimaryKey
    @Column(indexed = true)
    @SerializedName("id")
    public int id;

    @Column(indexed = true, unique = true)
    @SerializedName("session")
    public Session session;

    public MySession() {
    }

    public MySession(@NonNull Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MySession && ((MySession) o).id == id || super.equals(o);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
