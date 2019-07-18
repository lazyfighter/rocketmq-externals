package io.messaging.redis;

import com.moilioncircle.redis.replicator.Replicator;
import io.messaging.redis.sync.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RedisConnectorReplicator {

    Logger LOGGER = LoggerFactory.getLogger(RedisConnectorReplicator.class);

    private Replicator replicator;

    private Config config;

    private BlockingQueue<Object> queue = new LinkedBlockingQueue<>();


    public RedisConnectorReplicator(Config config) {
        this.config = config;
    }


    public void start() {
        try {
            EventProcessor eventProcessor = new EventProcessor(this);
            eventProcessor.start();
        } catch (Exception e) {
            LOGGER.error("Start error.", e);
        }
    }


    public void submit() {
    }


    public Config getConfig() {
        return config;
    }

    public BlockingQueue<Object> getQueue() {
        return queue;
    }
}
