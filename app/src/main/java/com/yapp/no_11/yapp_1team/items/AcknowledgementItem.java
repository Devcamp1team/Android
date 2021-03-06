package com.yapp.no_11.yapp_1team.items;

/**
 * Created by HunJin on 2017-10-19.
 */

public class AcknowledgementItem {
    private int id;
    private String nickname;
    private String email;
    private int thumbnail;

    public AcknowledgementItem(int id, String nickname, String email, int thumbnail) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
