package com.steven.test.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "consoles")
public class Console {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String consoleName;

    private Double price;

    private Long number;

//    One to Many
//    @OneToMany(mappedBy = "console", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    private List<VideoGame> videoGames;

    public Console(Integer id, String consoleName) {
        this.id = id;
        this.consoleName = consoleName;
    }

    public Console(Integer id, String consoleName, Double price, Long number) {
        this.id = id;
        this.consoleName = consoleName;
        this.price = price;
        this.number = number;
    }

    public Console() {
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
