package io.github.droidkaigi.confsched2017.model

import com.taroid.knit.should
import io.github.droidkaigi.confsched2017.util.DummyCreator
import org.junit.Test

class SessionFeedbackTest {

    @Test
    @Throws(Exception::class)
    fun isAllFilledWhenrelevancyIsNotFilled() {
        val feedback = SessionFeedback(DummyCreator.newSession(1), 0, 3, 3, 3, null)
        feedback.isAllFilled.should be false
    }

    @Test
    @Throws(Exception::class)
    fun isAllFilledWhenAllFilled() {
        val feedback = SessionFeedback(DummyCreator.newSession(1), 3, 3, 3, 3, "Cool")
        feedback.isAllFilled.should be true
    }

    @Test
    @Throws(Exception::class)
    fun isAllFilledWhenCommentIsNotFilled() {
        val feedback = SessionFeedback(DummyCreator.newSession(1), 3, 3, 3, 3, null)
        feedback.isAllFilled.should be true
    }

}
