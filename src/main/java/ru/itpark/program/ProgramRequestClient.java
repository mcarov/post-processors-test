package ru.itpark.program;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.itpark.app.RequestClient;
import ru.itpark.app.Cached;

@Cached
public class ProgramRequestClient extends RequestClient {
    @Override
    @Autowired
    public void setUrl(@Value("${url}") String url) {
        super.setUrl(url);
    }

    @Override
    @Autowired
    public void setGson(Gson gson) {
        super.setGson(gson);
    }
}
