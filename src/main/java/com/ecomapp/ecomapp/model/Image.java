package com.ecomapp.ecomapp.model;



import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Image extends BaseEntity {

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}





