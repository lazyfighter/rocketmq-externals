package io.messaging.redis.sync;

import com.moilioncircle.redis.replicator.RedisSocketReplicator;
import com.moilioncircle.redis.replicator.Replicator;
import com.moilioncircle.redis.replicator.cmd.impl.ExistType;
import com.moilioncircle.redis.replicator.cmd.impl.SetCommand;
import com.moilioncircle.redis.replicator.event.Event;
import com.moilioncircle.redis.replicator.event.EventListener;
import com.moilioncircle.redis.replicator.rdb.datatype.ExpiredType;
import io.messaging.redis.command.CommandMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSyncEventListener implements EventListener {


    private Logger logger = LoggerFactory.getLogger(RedisSyncEventListener.class);

    private EventProcessor eventProcessor;

    public RedisSyncEventListener(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @Override
    public void onEvent(Replicator replicator, Event event) {
        logger.info("event name:{}", event.getClass().getCanonicalName());
        RedisSocketReplicator redisSocketReplicator = (RedisSocketReplicator) replicator;
        eventProcessor.setConfiguration(redisSocketReplicator.getConfiguration());
        handler(event);

    }

    private void handler(Event event) {
        if (event instanceof SetCommand) {
            SetCommand setCommand = (SetCommand) event;
            byte[] key = setCommand.getKey();
            byte[] value = setCommand.getValue();
            ExpiredType expiredType = setCommand.getExpiredType();
            Long expiredValue = setCommand.getExpiredValue();
            ExistType existType = setCommand.getExistType();
            CommandMessage commandMessage = new CommandMessage();
            commandMessage.setCommandName("set");
            commandMessage.setExistType(setCommand.getExistType());
            commandMessage.setExpiredType(setCommand.getExpiredType());
            commandMessage.setExpireTime(setCommand.getExpiredValue());
            commandMessage.setKey(setCommand.getKey());

            logger.info("set {} value {}", new String(key), new String(value));
        }


    }
}
