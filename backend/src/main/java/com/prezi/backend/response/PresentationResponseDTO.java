package com.prezi.backend.response;

import com.prezi.backend.model.Presentation;

import java.io.Serializable;
import java.util.List;

public class PresentationResponseDTO implements Serializable {
    List<Presentation> presentationList;
    Integer totalCount;
    public PresentationResponseDTO(List<Presentation> presentationList, Integer totalCount){
        this.totalCount = totalCount;
        this.presentationList = presentationList;
    }

    public List<Presentation> getPresentationList() {
        return presentationList;
    }

    public void setPresentationList(List<Presentation> presentationList) {
        this.presentationList = presentationList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
