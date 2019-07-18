package io.messaging.redis.sync;

import com.moilioncircle.redis.replicator.Configuration;
import com.moilioncircle.redis.replicator.RedisReplicator;
import com.moilioncircle.redis.replicator.RedisURI;
import com.moilioncircle.redis.replicator.Status;
import io.messaging.redis.Config;
import io.messaging.redis.RedisConnectorReplicator;

import java.net.URI;

public class EventProcessor {

    private Config config;

    private RedisReplicator redisReplicator;

    private RedisConnectorReplicator redisConnectorReplicator;

    private Configuration configuration;

    private final RedisSyncEventListener redisSyncEventListener;
    private final RedisSyncCloseListner redisSyncCloseListner;
    private final RedisSyncExceptionListener redisSyncExceptionListener;


    public EventProcessor(RedisConnectorReplicator redisConnectorReplicator) throws Exception {
        this.redisConnectorReplicator = redisConnectorReplicator;
        this.config = redisConnectorReplicator.getConfig();
        this.redisReplicator = initRedisReplicator();
        this.configuration = redisReplicator.getConfiguration();
        this.redisSyncEventListener = new RedisSyncEventListener(this);
        this.redisSyncCloseListner = new RedisSyncCloseListner(this);
        this.redisSyncExceptionListener = new RedisSyncExceptionListener(this);
        this.setReplOffset(-1);
    }

    public void start() throws Exception {

        this.redisReplicator.addEventListener(redisSyncEventListener);

        this.redisReplicator.addCloseListener(redisSyncCloseListner);

        this.redisReplicator.addExceptionListener(redisSyncExceptionListener);

        this.redisReplicator.open();
    }

    private RedisReplicator initRedisReplicator() throws Exception {
        URI uri = config.convertToUri();
        RedisURI redisURI = new RedisURI(uri);
        RedisReplicator replicator = new RedisReplicator(redisURI);
        return replicator;
    }


    public void setReplOffset(long replOffset) {

        if (replOffset < 0) {
            replOffset = 0;
        }
        configuration.setReplOffset(replOffset);
    }

    public boolean isConnected() {
        if (this.redisReplicator == null) {
            return false;
        }
        Status status = this.redisReplicator.getStatus();
        if (status.equals(Status.CONNECTED)) {
            return true;
        }
        return false;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
