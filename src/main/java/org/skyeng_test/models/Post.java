package org.skyeng_test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
@ToString
public class Post {
    @Id
    private int index;
    private String name;
    private String address;
    @OneToMany
    @JsonIgnore
    private List<Routing> routing;

}
