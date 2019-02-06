package com.prezi.backend.fixtures;

import com.prezi.backend.model.Creator;

public class CreatorDTOBuilder {
    Creator creator;
    private String name;
    private String profileUrl;
    public CreatorDTOBuilder(String name, String profileUrl){
        creator = new Creator();
        creator.setName(name);
        creator.setProfileUrl(profileUrl);
    }
    public Creator build() {return this.creator;}
}
