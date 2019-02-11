package com.prezi.backend.jsonmapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prezi.backend.constant.CommonConstant;
import com.prezi.backend.constant.SortConstant;
import com.prezi.backend.model.Presentation;
import com.prezi.backend.response.PresentationResponseDTO;
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
import java.util.TimeZone;
import java.util.stream.Collectors;

@Configuration
public class PresentationData {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PresentationData.class);
    private List<Presentation> presentations;
    /*
        Constructor loads data from classpath with filename prezis.json
        And sets it in presentations named List
     */
    public PresentationData(){
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream("prezis.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat(CommonConstant.DATE_FORMAT));
            objectMapper.setTimeZone(TimeZone.getTimeZone("GMT"));
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

    /*
        Name: filterByTitle
        Params:
            1. title
        Returns:
            A List of presentations with datatype List<Presentation>
     */
    public List<Presentation> filterByTitle(String title){
        return presentations
                .stream()
                .filter(p-> p.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    /*
        Name: getSortedPaginatedDataByTitle
        Params:
            1. page (page number for pagination)
            2. perPage (number of records to list per page)
            3. direction (direction to sort, 1 for ascending and -1 for descending)
            4. title (search by title value, empty string will return all)
        Returns:
            A an object of PresentationResponseDTO of format {presentationList: [], totalCount: 0}
     */
    public PresentationResponseDTO getSortedPaginatedDataByTitle(Integer page, Integer perPage, Integer direction, String title){
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
        return new PresentationResponseDTO(PaginationHelper.getPaginatedList(listToReturn, page, perPage), listToReturn.size());
    }
}