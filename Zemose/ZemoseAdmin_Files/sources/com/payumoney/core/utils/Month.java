package com.payumoney.core.utils;

public enum Month {
    JAN("01"),
    FEB("02"),
    MAR("03"),
    APR("04"),
    MAY("05"),
    JUN("06"),
    JUL("07"),
    AUG("08"),
    SEP("09"),
    OCT("10"),
    NOV("11"),
    DEC("12");
    
    private final String month;

    private Month(String month2) {
        this.month = month2;
    }

    public static Month getMonth(String month2) {
        if ("01".equals(month2) || "1".equals(month2)) {
            return JAN;
        }
        if ("02".equals(month2) || "2".equals(month2)) {
            return FEB;
        }
        if ("03".equals(month2) || "3".equals(month2)) {
            return MAR;
        }
        if ("04".equals(month2) || "4".equals(month2)) {
            return APR;
        }
        if ("05".equals(month2) || "5".equals(month2)) {
            return MAY;
        }
        if ("06".equals(month2) || "6".equals(month2)) {
            return JUN;
        }
        if ("07".equals(month2) || "7".equals(month2)) {
            return JUL;
        }
        if ("08".equals(month2) || "8".equals(month2)) {
            return AUG;
        }
        if ("09".equals(month2) || "9".equals(month2)) {
            return SEP;
        }
        if ("10".equals(month2)) {
            return OCT;
        }
        if ("11".equals(month2)) {
            return NOV;
        }
        if ("12".equals(month2)) {
            return DEC;
        }
        return null;
    }

    public String toString() {
        return this.month;
    }
}
