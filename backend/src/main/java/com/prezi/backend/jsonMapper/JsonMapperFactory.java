package com.prezi.backend.jsonMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prezi.backend.model.Presentation;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@Configuration
public class JsonMapperFactory {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JsonMapperFactory.class);
    private List<Presentation> presentations;
    public JsonMapperFactory(){
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream("prezis.json");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            setPresentations(objectMapper.readValue(inputStream, new TypeReference<List<Presentation>>() {}));
        }
        catch(IOException ex){
            log.error("Could not load the file...", ex);
            setPresentations(Collections.emptyList());
        }
    }

    public List<Presentation> getPresentations() {
        return presentations;
    }

    public void setPresentations(List<Presentation> presentations) {
        this.presentations = presentations;
    }

    public List<Presentation> getPresentationsPaginated(Integer page, Integer perPage){
        if(page == null || perPage == null || page <= 0 || perPage < 0){
            log.info("invalid pageSize: {}  and offset: {}", perPage, page);
            throw new IllegalArgumentException("invalid pageSize: " +  perPage + " and offset: " + page);
        }
        int fromIndex = (page - 1) * perPage;
        int toIndex = fromIndex + perPage;
        if(presentations.size() < fromIndex){
            return Collections.emptyList();
        }
        if(presentations.size() < toIndex){
            toIndex = presentations.size() - fromIndex;
        }
        return presentations.subList(fromIndex, toIndex);
    }
}