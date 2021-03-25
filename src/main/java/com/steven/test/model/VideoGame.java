package com.steven.test.model;


import javax.persistence.*;

@Entity
@Table(name = "videoGames")
public class VideoGame {

    @Id
    private Integer id;

    private String title;

    private Double price;

    private String year;

    private String console;

    // Many to One
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="console_id")
//    private Console console_id;0

    public VideoGame(Integer id, String title, Double price, String year, String console) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.year = year;
        this.console = console;
    }

//    public VideoGame(Integer id, String title, Double price, String year, String console, Console console_id) {
//        this.id = id;
//        this.title = title;
//        this.price = price;
//        this.year = year;
//        this.console = console;
//        this.console_id = console_id;
//    }

    public VideoGame() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    @Override
    public String toString() {
        return "VideoGame{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", year='" + year + '\'' +
                ", console='" + console + '\'' +
                '}';
    }
}
