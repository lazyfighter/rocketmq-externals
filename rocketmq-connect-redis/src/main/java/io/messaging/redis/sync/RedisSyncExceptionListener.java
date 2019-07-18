package io.messaging.redis.sync;

import com.moilioncircle.redis.replicator.ExceptionListener;
import com.moilioncircle.redis.replicator.Replicator;
import com.moilioncircle.redis.replicator.event.Event;

public class RedisSyncExceptionListener implements ExceptionListener {

    private EventProcessor eventProcessor;
    public RedisSyncExceptionListener(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @Override
    public void handle(Replicator replicator, Throwable throwable, Event event) {

    }
}
