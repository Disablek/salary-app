package by.bntu.salaryapp.domain.model.employee;
import by.bntu.salaryapp.domain.model.BaseAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee extends BaseAuditingEntity
{
    @NotEmpty
    @Column(nullable = false)
    private String firstName;

    @NotEmpty
    @Column(nullable = false)
    private String lastName;

    @Nullable
    private String surName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_subject",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @Builder.Default
    private Set<Subject> subjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Override
    @Transient
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                (surName != null ? ", surName='" + surName + '\'' : "") +
                ", qualification='" + qualification.toString() +'\'' +
                ", subject='" + subjects.toString() +'\'' +
                ", position='" + position.toString() +'\'' +
                ", experience='" + experience.toString() +'\'' +
                '}';
    }

}
