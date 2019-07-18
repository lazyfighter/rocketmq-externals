package io.messaging.redis.command;


import com.moilioncircle.redis.replicator.cmd.impl.ExistType;
import com.moilioncircle.redis.replicator.rdb.datatype.ExpiredType;

public class CommandMessage {

    private String SPACE = " ";
    private String commandName;
    private byte[] key;
    private byte[] value;
    private long expireTime;
    private ExistType existType;
    private ExpiredType expiredType;


    public ExpiredType getExpiredType() {
        return expiredType;
    }

    public void setExpiredType(ExpiredType expiredType) {
        this.expiredType = expiredType;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public ExistType getExistType() {
        return existType;
    }

    public void setExistType(ExistType existType) {
        this.existType = existType;
    }


}
