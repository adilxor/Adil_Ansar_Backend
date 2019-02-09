package com.prezi.backend.jsonMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prezi.backend.constant.SortConstant;
import com.prezi.backend.model.Presentation;
import com.prezi.backend.utils.PaginationHelper;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class PresentationData {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PresentationData.class);
    private List<Presentation> presentations;
    public PresentationData(){
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream("prezis.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("MMM dd, yyyy"));
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            setPresentations(objectMapper.readValue(inputStream, new TypeReference<List<Presentation>>() {}));
        }
        catch(IOException ex){
            log.error("Could not load the file...", ex);
            setPresentations(Collections.emptyList());
        }
    }

    public void setPresentations(List<Presentation> presentations) {
        this.presentations = presentations;
    }

    public List<Presentation> filterByTitle(String title){
        return presentations
                .stream()
                .filter(p-> p.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    public List<Presentation> getSortedPaginatedDataByTitle(Integer page, Integer perPage, Integer direction, String title){
        List<Presentation> listToReturn;
        if (!StringUtils.isEmpty(title)){
            listToReturn = new ArrayList<>(filterByTitle(title));
        }
        else{
            listToReturn = new ArrayList<>(presentations);
        }

        if (SortConstant.SortDirection.DESC.v.equals(direction)){
            listToReturn.sort(Comparator.comparing(Presentation::getCreatedAt).reversed());
        }
        else{
            listToReturn.sort(Comparator.comparing(Presentation::getCreatedAt));
        }
        return PaginationHelper.getPaginatedList(listToReturn, page, perPage);
    }
}