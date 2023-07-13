package com.och.rjwm.common;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private static  final long serialVersionUID = 21321564564L;

    /**
     * 分页查询总记录数
     */
    private long total;

    /**
     * 当前结果列表
     */
    private List<T> records;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
