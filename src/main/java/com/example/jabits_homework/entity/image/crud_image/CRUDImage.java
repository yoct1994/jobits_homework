package com.example.jabits_homework.entity.image.crud_image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CRUDImage {
    @Id
    private Long listId;

    private String imageName;

    public CRUDImage updateImageName(String imageName) {
        this.imageName = imageName;

        return this;
    }
}
