package com.thereselindquist.matd.todo;

import com.thereselindquist.matd.user.UserModel;
import jakarta.persistence.*;

@Entity
public class TodoItemModel {
    @Id
    @GeneratedValue
    private Long itemId;
    private String title;
    private boolean favorite;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserModel userModel;

    public TodoItemModel() {
    }

    public TodoItemModel(String title, boolean favorite, boolean completed) {
        this.title = title;
        this.favorite = favorite;
        this.completed = completed;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
