package org.hameister.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by hameister on 24.12.16.
 */
@Entity
@Table(name = "Item")
@Document(indexName = "items", type = "items", shards = 1)
public class Item {

    public Item() {
    }

    public Item(String description, String location, LocalDate itemdate) {
        this.description = description;
        this.location = location;
        this.itemdate = itemdate;
    }

    public Item(Long id,String description, String location, LocalDate itemdate) {
        this.id=id;
        this.description = description;
        this.location = location;
        this.itemdate = itemdate;
    }

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private  String location;

    @JsonIgnore
    @Column(name = "itemdate")
    private LocalDate itemdate;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getItemdate() {
        return itemdate;
    }

    public void setItemdate(LocalDate itemdate) {
        this.itemdate = itemdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
