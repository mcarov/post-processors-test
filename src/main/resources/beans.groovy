import com.google.gson.Gson
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import ru.itpark.app.Processor
import ru.itpark.groovy.GroovyRequestClient

beans {
    configurer(PropertyPlaceholderConfigurer) {
        location = 'connection.properties'
    }

    processor Processor

    gson Gson

    groovyClient(GroovyRequestClient) {
        url = '${url}'
        gson = gson
    }
}