package de.htwberlin.webtech.web.api;

public class PostManipulationRequest {

    private String title;
    private String content;
    private String username;
    private String body;

    public PostManipulationRequest(String title, String content, String username, String body) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.body = body;
    }

    public PostManipulationRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
