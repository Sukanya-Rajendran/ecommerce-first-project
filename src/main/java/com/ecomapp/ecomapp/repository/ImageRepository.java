package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    Image save(Image image);
}
