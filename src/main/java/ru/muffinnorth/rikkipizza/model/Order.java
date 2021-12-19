package ru.muffinnorth.rikkipizza.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "inet_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String location;

    @Column
    private int status;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;
}
