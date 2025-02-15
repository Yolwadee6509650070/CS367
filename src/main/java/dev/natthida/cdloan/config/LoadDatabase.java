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
                Location branchA = locationRepository.save(new Location("Branch A"));
                Location branchB = locationRepository.save(new Location("Branch B"));
                Location branchC = locationRepository.save(new Location("Branch C"));

                log.info("Loading " + branchA);
                log.info("Loading " + branchB);
                log.info("Loading " + branchC);

                if (cdRepository.count() == 0) { // ตรวจสอบก่อนโหลด CD
                    log.info("Loading " + cdRepository.save(new CD("Inception", branchA)));
                    log.info("Loading " + cdRepository.save(new CD("Interstellar", branchB)));
                    log.info("Loading " + cdRepository.save(new CD("The Dark Knight", branchC)));
                } else {
                    log.info("CDs already exist, skipping data initialization.");
                }
            } else {
                log.info("Locations already exist, skipping data initialization.");
            }
        };
    }
}
