package by.bntu.salaryapp.datatable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DataTableServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataTableServiceApplication.class, args);
    }
}