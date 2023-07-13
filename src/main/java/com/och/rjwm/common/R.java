package com.och.rjwm.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class R<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;
    private Map map = new HashMap();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public void add(String key, Object value) {
        this.map.put(key, value);
    }
}
