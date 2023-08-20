package org.skyeng_test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "routings")
@ToString
public class Routing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Post postFrom, intermediatePost, postTo;
    private LocalDateTime start, departureTimeFromInterPost, arrivalTimeToInterPost, arrival;
    @OneToMany
    @JsonIgnore
    private List<Mailing> mailing;
}
