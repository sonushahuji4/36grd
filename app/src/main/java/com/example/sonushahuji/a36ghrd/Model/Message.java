package com.example.sonushahuji.a36ghrd.Model;

public class Message
{
    private String message;
    private String username;

    public Message(){}

    public Message(String message, String username)
    {
        this.message = message;
        this.username = username;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
