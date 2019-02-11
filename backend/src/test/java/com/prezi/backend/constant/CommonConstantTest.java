package com.prezi.backend.constant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CommonConstantTest {

    @Test
    // Test constants
    public void testCommonConstants() throws Exception {
        assertEquals(CommonConstant.DATE_FORMAT, "MMM dd, yyyy");
    }
}
