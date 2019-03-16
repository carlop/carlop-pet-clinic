package es.carlop.petclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

    @Builder
    public Visit(Long id, LocalDate date, String description, Pet pet) {
        super(id);
        if (date != null) {
            this.date = date;
        }
        this.description = description;
        this.pet = pet;
    }

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public LocalDate getDate() {
        return date;
    }

}
