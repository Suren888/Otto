package com.example.ottoservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "attribute")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attribute_id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "category_attribute",
        joinColumns = @JoinColumn(name = "attribute_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> relatedCategories;

    @OneToMany(mappedBy = "attribute",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<AttributeValue> attributeValues;

    public long getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(long attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
