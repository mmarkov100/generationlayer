package yandexgeneratorlayer.yandexgeneratorlayer;

import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YandexGeneratorLayerApplication {

    private static final Logger logger = LoggerFactory.getLogger(YandexGeneratorLayerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(YandexGeneratorLayerApplication.class, args);
        logger.info("Yandex Generator Layer Application started successfully."); // Логгирование, что приложение успешно запущено
    }
}

