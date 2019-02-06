package com.prezi.backend.fixtures;

import com.prezi.backend.model.Creator;
import com.prezi.backend.model.Presentation;

import java.util.Date;

public class PresentationDTOBuilder {
    Presentation presentation;
    private String id;
    private String title;
    private String thumbnail;
    private Date createdAt;
    private Creator creator;
    public PresentationDTOBuilder(String id, String title, String thumbnail, Date createdAt, Creator creator){

        presentation = new Presentation();
        presentation.setId(id);
        presentation.setTitle(title);
        presentation.setThumbnail(thumbnail);
        presentation.setCreatedAt(createdAt);
        presentation.setCreator(creator);
    }
    public Presentation build() {return this.presentation;}
}
