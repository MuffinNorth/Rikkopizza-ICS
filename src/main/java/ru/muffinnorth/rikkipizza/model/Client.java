package ru.muffinnorth.rikkipizza.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 12, unique = true)
    private String phone_number;

    @Column
    private String location;

    @Column
    private double loyalty;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
