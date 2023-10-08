package com.ecomapp.ecomapp.model;

import com.ecomapp.ecomapp.model.BaseEntity;
import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.model.Image;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Product extends BaseEntity {

    private String productName;

    private float price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Image> images;
    @Lob
    private String shortDescription;

    @Lob
    private String longDescription;
    @Lob
    @ElementCollection
  private List<byte[]> imagefiles;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    private boolean isDeleted;

    private boolean enabled;
    private boolean isAvailable;



    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }



}


