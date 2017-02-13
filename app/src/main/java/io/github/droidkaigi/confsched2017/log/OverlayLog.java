package io.github.droidkaigi.confsched2017.log;

/**
 * @author KeithYokoma
 */
public class OverlayLog {
    private final int priority;
    private final String tag;
    private final String message;

    public OverlayLog(int priority, String tag, String message) {
        this.priority = priority;
        this.tag = tag;
        this.message = message;
    }

    public int getPriority() {
        return priority;
    }

    public String getTag() {
        return tag;
    }

    public String getMessage() {
        return message;
    }
}
