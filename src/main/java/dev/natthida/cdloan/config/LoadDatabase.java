package dev.natthida.cdloan.config;

import dev.natthida.cdloan.repository.CDRepository;
import dev.natthida.cdloan.repository.LocationRepository;
import dev.natthida.cdloan.model.CD;
import dev.natthida.cdloan.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    @Transactional // ป้องกันปัญหาการบันทึกข้อมูลบางส่วน
    CommandLineRunner initDatabase(CDRepository cdRepository, LocationRepository locationRepository) {
        return args -> {
            if (locationRepository.count() == 0) { // ตรวจสอบก่อนโหลด Location
                Location branchA = locationRepository.save(new Location("Downtown"));
                Location branchB = locationRepository.save(new Location("Uptown"));
                Location branchC = locationRepository.save(new Location("Suburban"));

                log.info("Loading " + branchA);
                log.info("Loading " + branchB);
                log.info("Loading " + branchC);

                if (cdRepository.count() == 0) { // ตรวจสอบก่อนโหลด CD
                    log.info("Loading " + cdRepository.save(new CD("Inception", "Hans Zimmer", branchA)));
                    log.info("Loading " + cdRepository.save(new CD("Interstellar", "Hans Zimmer", branchB)));
                    log.info("Loading " + cdRepository.save(new CD("The Dark Knight", "Hans Zimmer", branchC)));
                    log.info("Loading " + cdRepository.save(new CD("The Beatles - Abbey Road", "The Beatles", branchA)));
                    log.info("Loading " + cdRepository.save(new CD("Pink Floyd - The Wall", "Pink Floyd", branchA)));
                    log.info("Loading " + cdRepository.save(new CD("Led Zeppelin - IV", "Led Zeppelin", branchB)));
                    log.info("Loading " + cdRepository.save(new CD("Abbey Road", "The Beatles", branchC)));
                    log.info("Loading " + cdRepository.save(new CD("Back in Black", "AC/DC", branchB)));
                    log.info("Loading " + cdRepository.save(new CD("The Wall", "Pink Floyd", branchA)));
                } else {
                    log.info("CDs already exist, skipping data initialization.");
                }
            } else {
                log.info("Locations already exist, skipping data initialization.");
            }
        };
    }
}
