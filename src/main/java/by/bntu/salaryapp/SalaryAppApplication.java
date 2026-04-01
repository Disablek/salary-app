package by.bntu.salaryapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SalaryAppApplication {

    private static final Logger log = LoggerFactory.getLogger(SalaryAppApplication.class);

    public static void main(String[] args) {
        log.info("Starting Salary App application");
        SpringApplication.run(SalaryAppApplication.class, args);
        log.info("Salary App application started successfully");
    }

}
