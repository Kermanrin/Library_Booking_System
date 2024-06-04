package com.huawei.ibooking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
public class SeatDO {
    private int id;
    private String buildingNum;
    private String classRoomNum;
    private String  seat_Id;
    private Boolean if_Open;
    private String open_Time;
    private String close_Time;
    private boolean if_Near_Window;
    private boolean if_Near_Power;
}
