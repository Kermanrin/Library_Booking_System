package com.huawei.ibooking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Booking_xxxDO {
    private int id;
    private String buildingNum;
    private String classroomNum;
    private String seat_Id;
    private String begin_Time;
    private String end_Time;
    private boolean if_Online;
    private String stuId;
}

