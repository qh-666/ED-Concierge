package com.example.edconcierge;

public class Message {
    public String content;
    public String time;
    public boolean toUser;
    public boolean isText;
    public Message(String content, String time, boolean toUser, boolean isText) {
        this.content = content;
        this.time = time;
        this.toUser = toUser;
        this.isText = isText;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", toUser=" + toUser +
                ", isText=" + isText +
                '}';
    }

    public String getContent() {
        return content;
    }
}
