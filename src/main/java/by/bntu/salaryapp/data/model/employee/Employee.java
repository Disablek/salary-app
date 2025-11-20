package by.bntu.salaryapp.data.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.lang.Nullable;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
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

    //TODO: Position
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(name = "position_id")
//    private Position position;
    //TODO: Subject
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "subject_id")
//    private Subject subject;
    //TODO: Qualification
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(name = "qualification_id")
//    private Qualification qualification;
    //TODO: Experience
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable
//    private Experience experience;

//    @Override
    //@Transient
//    public String toString() {
//        return "Employee{" +
//                "id='" + id + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                (surName != null ? ", surName='" + surName + '\'' : "") +
//                ", qualification='" + qualification.toString() +'\' +
//                ", subject='" + subject.toString() +'\' +
//                ", position='" + position.toString() +'\' +
//                ", experience='" + experience.toString() +'\' +
//                '}';
//    }

}
