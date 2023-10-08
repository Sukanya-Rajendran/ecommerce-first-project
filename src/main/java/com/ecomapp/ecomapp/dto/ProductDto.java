package com.ecomapp.ecomapp.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)

public class ProductDto {
    private Long id ;

    private String  name;

    private String description;

    private int  categoryId;

    private double actualPrice;

    private double sellingPrice;

    private double stock;

    private String imageName;

    private boolean isAvailable;



    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }


}
