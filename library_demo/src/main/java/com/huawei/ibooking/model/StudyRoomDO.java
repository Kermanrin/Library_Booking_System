package com.huawei.ibooking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StudyRoomDO {
    private int id;
    private String buildingNum;
    private String classRoomNum;
    private String seat_Id_Set;
    private Boolean if_Open = false;
    private String open_Time ;
    private String close_Time ;
}
