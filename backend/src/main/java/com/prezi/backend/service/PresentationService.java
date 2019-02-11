package com.prezi.backend.service;

import com.prezi.backend.jsonmapper.PresentationData;
import com.prezi.backend.response.PresentationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class PresentationService {
    private PresentationData presentationData;

    public PresentationService(PresentationData presentationData){
        this.presentationData = presentationData;
    }
    public PresentationResponseDTO getPresentations(Integer page, Integer perPage, Integer sortDirection, String title){
        return this.presentationData.getSortedPaginatedDataByTitle(page, perPage, sortDirection, title);
    }
}
