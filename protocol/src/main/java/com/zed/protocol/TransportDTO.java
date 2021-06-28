package com.zed.protocol;

import lombok.ToString;

import java.io.Serializable;

/**
 * @author Zed
 */
@ToString
public class TransportDTO<T> implements Serializable {

    private String id;

    private T data;

    private String event;

    private String target;

    private ServiceTransportType type;

    private String namespace;

    private String sessionId;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ServiceTransportType getType() {
        return type;
    }

    public void setType(ServiceTransportType type) {
        this.type = type;
    }
}
