package vn.edu.uit.a15520275.model;

import java.io.Serializable;

/**
 * Created by admin on 10/30/2017.
 */

public class Topic implements Serializable {
    private String idTopic;
    private String topic;
    public Topic() {
    }

    public Topic(String idTopic, String topic) {
        this.idTopic = idTopic;
        this.topic = topic;
    }

    public String getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(String idTopic) {
        this.idTopic = idTopic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
