package com.prezi.backend.fixtures;

import com.prezi.backend.model.Creator;
import com.prezi.backend.model.Presentation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PresentationListDTOBuilder {
    private List<Presentation> presentations = new ArrayList<>();
    public PresentationListDTOBuilder(){
        Date date = new Date();
        Creator creator1 = new CreatorDTOBuilder("Adil1", "www.adilsample1.com/profile").build();
        Presentation presentation1 = new PresentationDTOBuilder("1", "Title1", "thumbnail_url1", date, creator1).build();
        Creator creator2 = new CreatorDTOBuilder("Adil2", "www.adilsample2.com/profile").build();
        Presentation presentation2 = new PresentationDTOBuilder("2", "Title2", "thumbnail_url2", date, creator2).build();
        presentations.add(presentation1);
        presentations.add(presentation2);
    }
    public List<Presentation> build(){return presentations;}
}
