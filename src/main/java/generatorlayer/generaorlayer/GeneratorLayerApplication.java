package generatorlayer.generaorlayer;

import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeneratorLayerApplication {

    private static final Logger logger = LoggerFactory.getLogger(GeneratorLayerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GeneratorLayerApplication.class, args);
        logger.info("Yandex Generator Layer Application started successfully."); // Логгирование, что приложение успешно запущено
    }
}

