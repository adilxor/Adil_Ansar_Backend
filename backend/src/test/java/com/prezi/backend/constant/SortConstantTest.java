package com.prezi.backend.constant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SortConstantTest {
    @Test
    // Test constants
    public void testSortConstants() throws Exception {
        assertEquals(Integer.valueOf(1), SortConstant.SortDirection.ASC.v);
        assertEquals(Integer.valueOf(-1), SortConstant.SortDirection.DESC.v);
    }

}
