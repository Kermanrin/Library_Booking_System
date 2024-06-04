package com.huawei.ibooking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
public class Seat_If_Open_State {
    private String  seat_Id;
    private Boolean if_Open;
}
