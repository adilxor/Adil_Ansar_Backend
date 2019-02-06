package com.prezi.backend.constant;

public class SortConstant {
    private SortConstant() {
        throw new IllegalStateException("SortConstant class cannot be instantiated.");
    }
    public static final String CREATED_AT = "createdAt";
    public enum SortDirection {

        ASC(1), DESC(-1);
        public final Integer v;
        SortDirection(Integer value) {
            this.v = value;
        }
    }
}
