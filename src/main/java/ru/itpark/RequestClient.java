package ru.itpark;

import com.google.gson.Gson;
import ru.itpark.app.Post;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public abstract class RequestClient {
    private String url;
    private Gson gson;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public String getPost(int id) {
        System.out.println("--> url request");
        try {
            Reader reader = new InputStreamReader(new URL(url+id).openStream());
            Post post = gson.fromJson(reader, Post.class);
            return post.toString();
        }
        catch (IOException e) {
            return e.toString();
        }
    }
}
