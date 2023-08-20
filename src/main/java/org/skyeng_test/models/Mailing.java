package org.skyeng_test.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mailings")
@ToString
public class Mailing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Type type;
    @OneToOne
    private Recipient recipient;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JsonIgnore
    private Routing routing;
    private LocalDateTime regDate;
    private LocalDateTime receivingDate;

}
