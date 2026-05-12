package by.bntu.salaryapp.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "by.bntu.salaryapp")
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "by.bntu.salaryapp.infrastructure.persistence.repository")
@EntityScan(basePackages = "by.bntu.salaryapp.domain.model")
public class EmployeeServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceApplication.class);

    public static void main(String[] args) {
        log.info("Starting Employee Service application");
        SpringApplication.run(EmployeeServiceApplication.class, args);
        log.info("Employee Service started successfully");
    }
}