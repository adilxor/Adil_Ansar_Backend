package com.prezi.backend.utils;

import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class PaginationHelper {

    /*
        Name: getPaginatedList
        Params:
            1. list (list of any data type to paginate)
            2. page (page number for pagination)
            3. perPage (number of records to list per page)
        Returns:
            A a list of same data type as received (list) with paginated content only.
     */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PaginationHelper.class);
    private PaginationHelper() {
        throw new IllegalStateException("PaginationHelper class cannot be instantiated.");
    }
    public static <T> List<T> getPaginatedList(List<T> list, Integer page, Integer perPage){
        // In case any value is invalid throw exception
        if(page == null || perPage == null || page <= 0 || perPage < 0){
            log.info("invalid pageSize: {}  and offset: {}", perPage, page);
            throw new IllegalArgumentException("invalid pageSize: " +  perPage + " and offset: " + page);
        }
        int fromIndex = (page - 1) * perPage;
        int toIndex = fromIndex + perPage;
        // In case the page requested exceeds the data.
        if(list.size() < fromIndex){
            return Collections.emptyList();
        }
        // To handle fetching data from the last page
        if(list.size() < toIndex){
            toIndex = list.size();
        }
        return list.subList(fromIndex, toIndex);
    }
}
