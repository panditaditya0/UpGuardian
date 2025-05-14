package com.atozdev.uptimemoniter.enums;

import lombok.Getter;

public enum TimeRange {
    m5(5L, "minutes"),
    m10(10L, "minutes"),
    m15(15L, "minutes"),
    m30(30L, "minutes"),
    h1(1L, "hours"),
    h3(3L, "hours"),
    h6(6L, "hours"),
    h12(12L, "hours"),
    h24(24L, "hours"),
    d2(2L, "days");


    @Getter
    private final Long timeValue;

    @Getter
    private final String timeUnit;

    TimeRange(Long timeValue, String timeUnit){
        this.timeValue = timeValue;
        this.timeUnit = timeUnit;
    }
}
