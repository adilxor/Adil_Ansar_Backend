package com.prezi.backend.fixtures;

import com.prezi.backend.model.Creator;
import com.prezi.backend.model.Presentation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PresentationListDTOBuilder {
    private List<Presentation> presentations = new ArrayList<>();
    public PresentationListDTOBuilder(Integer count){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String baseName = "Adil";
        String baseProfileUrl = "www.adilsample.com/profile";
        String baseTitle = "Title";
        int i = 0;
        while(i < count){
            calendar.add(Calendar.DATE, i+1);
            date = calendar.getTime();
            Creator creator = new CreatorDTOBuilder(baseName + (i+1), baseProfileUrl + (i+1)).build();
            Presentation presentation = new PresentationDTOBuilder(String.valueOf(i+1), baseTitle + (i+1), "thumbnail_url", date, creator).build();
            presentations.add(presentation);
            i += 1;
        }

    }
    public List<Presentation> build(){return presentations;}
}
