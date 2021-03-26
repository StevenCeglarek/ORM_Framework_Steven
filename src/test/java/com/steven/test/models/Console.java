package com.steven.test.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "consoles")
public class Console {

    @Id
    private Integer id;
    private String consoleName;
    private Double price;
    private Long number;

    public Console() {
    }

    public Console(Integer id, String consoleName, Double price, Long number) {
        this.id = id;
        this.consoleName = consoleName;
        this.price = price;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Console{" +
                "id=" + id +
                ", consoleName='" + consoleName + '\'' +
                ", price=" + price +
                ", number=" + number +
                '}';
    }
}
