package com.prezi.backend.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PaginationHelperTest {

    List<Integer> dataList;

    @Before
    public void setup() {
        dataList = new ArrayList<>();
        int i = 0;
        while(i<15){
            dataList.add(Integer.valueOf(i+1));
            i += 1;
        }
    }

    @Test
    // Test getPaginatedList of Pagination Helper for success
    public void testGetPaginatedListSuccess() throws Exception{
        List<Integer> paginatedList = PaginationHelper.getPaginatedList(dataList, 1, 5);
        assertEquals(5, paginatedList.size());
        assertEquals(Integer.valueOf(1), paginatedList.get(0));
        assertEquals(Integer.valueOf(5), paginatedList.get(4));


        paginatedList = PaginationHelper.getPaginatedList(dataList, 2, 5);
        assertEquals(5, paginatedList.size());
        assertEquals(Integer.valueOf(6), paginatedList.get(0));
        assertEquals(Integer.valueOf(10), paginatedList.get(4));

        paginatedList = PaginationHelper.getPaginatedList(dataList, 3, 5);
        assertEquals(5, paginatedList.size());
        assertEquals(Integer.valueOf(11), paginatedList.get(0));
        assertEquals(Integer.valueOf(15), paginatedList.get(4));
    }

    @Test
    // Test getPaginatedList of Pagination Helper for when page number  exceeds the data set
    public void testGetPaginatedListFailure() throws Exception{
        List<Integer> paginatedList = PaginationHelper.getPaginatedList(dataList, 4, 5);
        assertEquals(0, paginatedList.size());
    }

    @Test(expected=IllegalArgumentException.class)
    // Test getPaginatedList of Pagination Helper for when page number is null
    public void testGetPaginatedListFailure2() throws Exception{
        List<Integer> paginatedList = PaginationHelper.getPaginatedList(dataList, null, 5);
    }

    @Test(expected=IllegalArgumentException.class)
    // Test getPaginatedList of Pagination Helper for when page number is less than zero
    public void testGetPaginatedListFailure3() throws Exception{
        List<Integer> paginatedList = PaginationHelper.getPaginatedList(dataList, -1, 5);
    }

    @Test(expected=IllegalArgumentException.class)
    // Test getPaginatedList of Pagination Helper for when per page count is null
    public void testGetPaginatedListFailure4() throws Exception{
        List<Integer> paginatedList = PaginationHelper.getPaginatedList(dataList, null, 5);
    }

    @Test(expected=IllegalArgumentException.class)
    // Test getPaginatedList of Pagination Helper for when per page count is less than zero
    public void testGetPaginatedListFailure5() throws Exception{
        List<Integer> paginatedList = PaginationHelper.getPaginatedList(dataList, -1, 5);
    }
}
