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
            assertThat(TopicColor.from(Topic().apply { id = 10 })).isEqualTo(TopicColor.NONE)
        }

        // valid topic id
        run {
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.PRODUCTIVITY_AND_TOOLING.ordinal }))
                    .isEqualTo(TopicColor.PRODUCTIVITY_AND_TOOLING)
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY.ordinal }))
                    .isEqualTo(TopicColor.ARCHITECTURE_AND_DEVELOPMENT_PROCESS_METHODOLOGY)
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.HARDWARE.ordinal }))
                    .isEqualTo(TopicColor.HARDWARE)
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.UI_AND_DESIGN.ordinal }))
                    .isEqualTo(TopicColor.UI_AND_DESIGN)
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.QUALITY_AND_SUSTAINABILITY.ordinal }))
                    .isEqualTo(TopicColor.QUALITY_AND_SUSTAINABILITY)
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.PLATFORM.ordinal }))
                    .isEqualTo(TopicColor.PLATFORM)
            assertThat(TopicColor.from(Topic().apply { id = TopicColor.OTHER.ordinal }))
                    .isEqualTo(TopicColor.OTHER)
        }

    }
}
