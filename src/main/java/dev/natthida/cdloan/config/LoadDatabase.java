package dev.natthida.cdloan.config;

import dev.natthida.cdloan.repository.CDRepository;
import dev.natthida.cdloan.repository.StorageRepository;
import dev.natthida.cdloan.model.CD;
import dev.natthida.cdloan.model.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CDRepository cdRepository, StorageRepository storageRepository) {
        return args -> {
            // โหลด Storage
            Storage shelfA = storageRepository.save(new Storage("Shelf A"));
            Storage shelfB = storageRepository.save(new Storage("Shelf B"));
            Storage box1 = storageRepository.save(new Storage("Box 1"));

            log.info("Loading " + shelfA);
            log.info("Loading " + shelfB);
            log.info("Loading " + box1);

            // โหลด CD และกำหนด Storage ให้ CD
            log.info("Loading " + cdRepository.save(new CD("Inception", shelfA)));
            log.info("Loading " + cdRepository.save(new CD("Interstellar", shelfB)));
            log.info("Loading " + cdRepository.save(new CD("The Dark Knight", box1)));
        };
    }
}


