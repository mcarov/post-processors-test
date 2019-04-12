package ru.itpark.annotation;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itpark.RequestClient;
import ru.itpark.app.Cached;

@Cached
@Component("annotationClient")
public class AnnotationRequestClient extends RequestClient {
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
