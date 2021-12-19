package ru.muffinnorth.rikkipizza.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "Заголовок не должен быть пустым")
    @Column
    private String title;

    @Column(columnDefinition = "int default 0")
    private int priority;

    @Transient
    private MultipartFile multipartFile;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column
    private byte[] icon;

    @OneToMany(mappedBy = "section")
    private List<Item> items;

    public void addItem(Item item){
        items.add(item);
    }

}
