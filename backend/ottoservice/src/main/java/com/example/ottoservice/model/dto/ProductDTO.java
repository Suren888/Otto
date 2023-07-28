package com.example.ottoservice.model.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class ProductDTO implements Serializable {

    public static class Image {
        private String src;
        private String title;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Image image = (Image) o;
            return Objects.equals(title, image.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title);
        }
    }

    private String name;
    private int categoryId;
    private long id;
    private Image[] pictures;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Image[] getPictures() {
        return pictures;
    }

    public void setPictures(Image[] pictures) {
        this.pictures = pictures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", id=" + id +
                ", pictures=" + Arrays.toString(pictures) +
                '}';
    }
}
