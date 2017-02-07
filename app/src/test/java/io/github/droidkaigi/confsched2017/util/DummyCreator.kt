package io.github.droidkaigi.confsched2017.util

import io.github.droidkaigi.confsched2017.model.*
import java.sql.Date

object DummyCreator {
    fun newMySession(seed: Int) = MySession().apply {
        id = seed
        session = newSession(seed)
    }

    fun newSession(seed: Int) = Session().apply {
        val seedString = seed.toString()
        val seedLong = seed.toLong()

        id = seed
        title = seedString
        desc = seedString
        speaker = newSpeaker(seed)
        stime = Date(seedLong)
        etime = Date(seedLong)
        durationMin = seed
        type = seedString
        topic = newTopic(seed)
        room = newRoom(seed)
        lang = seedString
        slideUrl = seedString
        movieUrl = seedString
        movieDashUrl = seedString
        shareUrl = seedString
    }

    fun newSpeaker(seed: Int) = Speaker().apply {
        val seedString = seed.toString()

        id = seed
        name = seedString
        imageUrl = seedString
        twitterName = seedString
        githubName = seedString
    }

    fun newTopic(seed: Int) = Topic().apply {
        val seedString = seed.toString()

        id = seed
        name = seedString
        other = seedString
    }

    fun newRoom(seed: Int) = Room().apply {
        id = seed
        name = seed.toString()
    }

    fun newContributor(seed: Int) = Contributor().apply {
        val seedString = seed.toString()

        name = seedString
        avatarUrl = seedString
        htmlUrl = seedString
        contributions = seed
    }
}
