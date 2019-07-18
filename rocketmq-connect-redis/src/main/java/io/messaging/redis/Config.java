package io.messaging.redis;

import io.openmessaging.KeyValue;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class Config {

    private String redisAddr;
    private int redisPort;
    private String redisPassword;


    public Config() {

    }

    public Config(String redisAddr, int redisPort, String redisPassword) {
        this.redisAddr = redisAddr;
        this.redisPort = redisPort;
        this.redisPassword = redisPassword;
    }

    public static final Set<String> REQUEST_CONFIG = new HashSet<String>() {
        {
            add("redisAddr");
            add("redisPort");
        }
    };


    public void load(KeyValue props) {

        properties2Object(props, this);
    }

    private void properties2Object(final KeyValue p, final Object object) {

        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            String mn = method.getName();
            if (mn.startsWith("set")) {
                try {
                    String tmp = mn.substring(4);
                    String first = mn.substring(3, 4);

                    String key = first.toLowerCase() + tmp;
                    String property = p.getString(key);
                    if (property != null) {
                        Class<?>[] pt = method.getParameterTypes();
                        if (pt != null && pt.length > 0) {
                            String cn = pt[0].getSimpleName();
                            Object arg;
                            if (cn.equals("int") || cn.equals("Integer")) {
                                arg = Integer.parseInt(property);
                            } else if (cn.equals("long") || cn.equals("Long")) {
                                arg = Long.parseLong(property);
                            } else if (cn.equals("double") || cn.equals("Double")) {
                                arg = Double.parseDouble(property);
                            } else if (cn.equals("boolean") || cn.equals("Boolean")) {
                                arg = Boolean.parseBoolean(property);
                            } else if (cn.equals("float") || cn.equals("Float")) {
                                arg = Float.parseFloat(property);
                            } else if (cn.equals("String")) {
                                arg = property;
                            } else {
                                continue;
                            }
                            method.invoke(object, arg);
                        }
                    }
                } catch (Throwable ignored) {
                }
            }
        }
    }

    public String getRedisAddr() {
        return redisAddr;
    }

    public void setRedisAddr(String redisAddr) {
        this.redisAddr = redisAddr;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public URI convertToUri() throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        sb.append("redis://");
        if (this.redisPassword != null && this.redisPassword.length() > 0) {
            sb.append(this.getRedisPassword());
            sb.append("@");
        }
        sb.append(this.redisAddr);
        sb.append(":");
        sb.append(this.redisPort);
        return new URI(sb.toString());
    }
}
