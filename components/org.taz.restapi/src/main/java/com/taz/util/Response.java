package com.taz.util;

public class Response<T> {
    private String statusCode;
    private String statusDescription;
    private T content;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Response() {
        this.statusCode = ResponseStatus.ERROR.getStatusCode();
        this.statusDescription = ResponseStatus.ERROR.getStatusDescription();
    }

    public Response(ResponseStatus mnStatus) {
        this.statusCode = mnStatus.getStatusCode();
        this.statusDescription = mnStatus.getStatusDescription();
    }

    public void setStatus(ResponseStatus status) {
        this.statusCode = status.getStatusCode();
        this.statusDescription = status.getStatusDescription();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
        setStatus(ResponseStatus.SUCCESS);
    }
}
