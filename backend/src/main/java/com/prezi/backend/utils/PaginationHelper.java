package com.prezi.backend.utils;

import com.prezi.backend.model.Presentation;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PaginationHelper {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PaginationHelper.class);

    public static <T> List<T> getPaginatedList(List<T> list, Integer page, Integer perPage){
        if(page == null || perPage == null || page <= 0 || perPage < 0){
            log.info("invalid pageSize: {}  and offset: {}", perPage, page);
            throw new IllegalArgumentException("invalid pageSize: " +  perPage + " and offset: " + page);
        }
        int fromIndex = (page - 1) * perPage;
        int toIndex = fromIndex + perPage;
        if(list.size() < fromIndex){
            return Collections.emptyList();
        }
        if(list.size() < toIndex){
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }
}
