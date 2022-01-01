package de.htwberlin.webtech.web.api;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

public class PostManipulationRequest {

    @Size(min = 2, message = "Please Provide 3 or more Charakters.")
    private String body;
    private String username;

    @NotNull(message = "Please Provide")
    private String title;
    private String content;



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
