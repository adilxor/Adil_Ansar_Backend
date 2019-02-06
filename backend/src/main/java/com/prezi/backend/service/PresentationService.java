package com.prezi.backend.service;

import com.prezi.backend.jsonMapper.PresentationData;
import com.prezi.backend.model.Presentation;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresentationService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PresentationService.class);
    private PresentationData presentationData;

    public PresentationService(PresentationData presentationData){
        this.presentationData = presentationData;
    }
    public List<Presentation> getPresentations(Integer page, Integer perPage, Integer sortDirection){
        return this.presentationData.getSortedPaginatedData(page, perPage, sortDirection);
    }
}
