package by.testfirebase.dataModel;

import java.util.Date;

public class Article {

    private String userId;
    private String textMessage;
    private long timeMessage;

    public Article(String userId, String textMessage) {
        this.userId = userId;
        this.textMessage = textMessage;
        this.timeMessage = new Date().getTime();
    }

    public Article() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(long timeMessage) {
        this.timeMessage = timeMessage;
    }
}
