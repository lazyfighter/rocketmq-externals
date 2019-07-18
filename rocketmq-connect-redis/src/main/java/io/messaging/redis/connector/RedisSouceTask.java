package io.messaging.redis.connector;

import io.messaging.redis.Config;
import io.messaging.redis.RedisConnectorReplicator;
import io.openmessaging.KeyValue;
import io.openmessaging.connector.api.data.SourceDataEntry;
import io.openmessaging.connector.api.source.SourceTask;

import java.util.Collection;

public class RedisSouceTask extends SourceTask {


    private Config config;
    private RedisConnectorReplicator replicator;

    @Override
    public Collection<SourceDataEntry> poll() {
        return null;
    }

    @Override
    public void start(KeyValue props) {
        this.config = new Config();
        this.config.load(props);
        this.replicator = new RedisConnectorReplicator(this.config);
        this.replicator.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
