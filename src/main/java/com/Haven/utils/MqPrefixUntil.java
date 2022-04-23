package com.Haven.utils;

public class MqPrefixUntil {

    public static String getFanoutExchange(String value) {
        return "fanout_" + value + "_exchange";
    }
    public static String getFanoutQueue(String value) {
        return value + ".fanout." + "exchange";
    }

    public static String getDirectExchange(String value) {
        return "direct_" + value + "_exchange";
    }
    public static String getDirectQueue(String value) {
        return value + ".direct." + "exchange";
    }

    public static String getTopicExchange(String value) {
        return "topic_" + value + "_exchange";
    }
    public static String getTopicQueue(String value) {
        return value + ".topic." + "exchange";
    }
}
