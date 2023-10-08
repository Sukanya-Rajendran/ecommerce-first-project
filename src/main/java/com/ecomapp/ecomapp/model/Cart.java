package com.ecomapp.ecomapp.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseEntity {
    @Id
@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
@GenericGenerator(name = "uuid2", strategy = "uuid2")
@Type(type = "org.hibernate.type.UUIDCharType")
private UUID id;



    private int quantity;


    @ManyToOne
    @JoinColumn(name = "user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;




}



