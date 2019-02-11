package com.prezi.backend.model;

import java.io.Serializable;

public class Creator implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String name;
    private String profileUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
