package com.och.rjwm.common;

public class BaseContext {
    private static ThreadLocal<Long> currentId = new ThreadLocal<>();

    public static void setCurrentId(Long userId) {
        currentId.set(userId);
    }

    public static Long getCurrentId() {
        return currentId.get();
    }

    public static void clear() {
        currentId.remove();
    }
}
