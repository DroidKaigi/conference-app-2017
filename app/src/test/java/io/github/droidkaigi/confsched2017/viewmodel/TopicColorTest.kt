package io.github.droidkaigi.confsched2017.viewmodel

import io.github.droidkaigi.confsched2017.model.Topic
import org.junit.Test

import org.junit.Assert.*
import org.assertj.core.api.Assertions.assertThat;

class TopicColorTest {

    @Test
    fun from() {

        // topic is null
        run {
            assertThat(TopicColor.from(null)).isEqualTo(TopicColor.NONE)
        }

        // invalid topic id
        run {
            assertThat(TopicColor.from(Topic().apply { id = -1 })).isEqualTo(TopicColor.NONE)
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.values().size })).isEqualTo(TopicColor.NONE)
        }

        // valid topic id
        // TODO There is not the specification document about topic id yet.
        // So, now just see raw json data. https://droidkaigi.github.io/2017/sessions.json
        run {
            assertThat(TopicColor.from(Topic().apply { id = 0 }))
                    .isEqualTo(TopicColor.NONE)

            assertThat(TopicColor.from(Topic().apply { id = 1 }))
                    .isEqualTo(TopicColor.PRODUCTIVITY_AND_TOOLING)

            assertThat(TopicColor.from(Topic().apply { id = 2 }))
                    .isEqualTo(TopicColor.ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY)

            assertThat(TopicColor.from(Topic().apply { id = 3 }))
                    .isEqualTo(TopicColor.HARDWARE)

            assertThat(TopicColor.from(Topic().apply { id = 4 }))
                    .isEqualTo(TopicColor.UI_AND_DESIGN)

            assertThat(TopicColor.from(Topic().apply { id = 5 }))
                    .isEqualTo(TopicColor.QUALITY_AND_SUSTAINABILITY)

            assertThat(TopicColor.from(Topic().apply { id = 6 }))
                    .isEqualTo(TopicColor.PLATFORM)

            assertThat(TopicColor.from(Topic().apply { id = 7 }))
                    .isEqualTo(TopicColor.OTHER)
        }

    }
}
