package ru.muffinnorth.rikkipizza.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private short count;

    @Column
    private boolean isReady;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Item item;
}
