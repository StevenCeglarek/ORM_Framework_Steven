package com.steven.test.model;

import com.steven.annotations.Entity;
import com.steven.annotations.Id;
import com.steven.annotations.Table;

@Entity
@Table(name="video_games")
public class VideoGame {

    @Id
    private Integer id;

    private String title;

    public VideoGame(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

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
}
