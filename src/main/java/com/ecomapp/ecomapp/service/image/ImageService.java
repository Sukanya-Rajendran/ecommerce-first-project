package com.ecomapp.ecomapp.service.image;

import com.ecomapp.ecomapp.model.Image;

import java.util.UUID;

public interface ImageService {


    Image saveImage(Image image);

    void deleteImage(Image deletedImage);

    void deleteImageById(UUID id);
}
