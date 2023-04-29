package com.bigdata.omp.plugin.common;

/**
 * Created by blwx on 2019/11/28.
 */
public class ResultEntity<T> {

    private String code;
    private String message;
    private String seqid;
    private T data;

    public ResultEntity() {
        this.code = "10000";
        this.message = "成功";
    }

    public ResultEntity(T data) {
        this();
        this.data = data;
    }

    public ResultEntity(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeqid() {
        return seqid;
    }

    public void setSeqid(String seqid) {
        this.seqid = seqid;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
