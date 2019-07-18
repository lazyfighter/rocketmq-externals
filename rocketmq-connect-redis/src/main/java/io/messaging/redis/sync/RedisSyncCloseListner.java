package io.messaging.redis.sync;

import com.moilioncircle.redis.replicator.CloseListener;
import com.moilioncircle.redis.replicator.Replicator;

public class RedisSyncCloseListner implements CloseListener {

    public RedisSyncCloseListner(EventProcessor eventProcessor) {

    }

    @Override
    public void handle(Replicator replicator) {

    }
}
