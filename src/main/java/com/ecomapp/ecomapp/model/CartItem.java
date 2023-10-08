package com.ecomapp.ecomapp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseEntity{
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;



}
