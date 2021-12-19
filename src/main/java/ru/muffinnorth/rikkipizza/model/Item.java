package ru.muffinnorth.rikkipizza.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int price;

    @Transient
    private MultipartFile multipartFile;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column
    private byte[] image;

    @Transient
    private int sectionId;

    @ManyToOne
    private Section section;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList;

    public void connectSection(Section section){
        this.section = section;
    }
}
