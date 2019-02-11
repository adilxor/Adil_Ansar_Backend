package com.prezi.backend.constant;

public class SortConstant {
    private SortConstant() {
        throw new IllegalStateException("SortConstant class cannot be instantiated.");
    }
    /*
        Enum to manage sorting directions
        ASC is ascending with value = 1
        DESC is descending with value = -1
     */
    public enum SortDirection {

        ASC(1), DESC(-1);
        public final Integer v;
        SortDirection(Integer value) {
            this.v = value;
        }
    }
}
