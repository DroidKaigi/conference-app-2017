package io.github.droidkaigi.confsched2017.log;

import timber.log.Timber;

/**
 * @author KeithYokoma
 */
public class OverlayLogTree extends Timber.Tree {
    private final LogEmitter emitter;

    public OverlayLogTree(LogEmitter emitter) {
        this.emitter = emitter;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        emitter.log(priority, tag, message);
    }
}
