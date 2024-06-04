package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.Seat_If_Open_State;
import com.huawei.ibooking.model.StudyRoomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hibernate.annotations.Parent;

import java.util.List;

@Mapper
public interface StudyRoomMapper {
    List<StudyRoomDO> getAllStudyRooms();
    int saveStudyRoom(@Param("study") StudyRoomDO study);
    int deleteStudyRoom(@Param("buildingNum") String buildingNum, @Param("classRoomNum") String classRoomNum);

    //修改自习室信息的第一次调用，返回前端需要的自习室信息列表
    List<StudyRoomDO> getStudyRooms(@Param("buildingNum") String buildingNum);

    //修改自习室信息的第一次调用，返回前端需要的座位开放状态列表
    List<Seat_If_Open_State> getStudySeats(@Param("buildingNum") String buildingNum,
                                           @Param("classRoomNum") String classRoomNum);
    boolean modifySeatsAttribute(@Param("buildingNum") String buildingNum,
                                 @Param("classRoomNum") String classRoomNum,
                                 @Param("if_Near_Window") boolean if_Near_Window,
                                 @Param("if_Near_Power") boolean if_Near_Power,
                                 @Param("seat_Id") String seat_Id,
                                 @Param("if_Open") boolean if_Open);

    //这是第二次调用，将会把前端的数据存入对应自习室的数据表
    int modifyStudyRoom(@Param("buildingNum") String buildingNum, @Param("classRoomNum") String classRoomNum, @Param("if_Open") boolean if_Open,
                        @Param("open_Time") String open_Time, @Param("close_Time") String close_Time, @Param("Seat_Id_Set") String Seat_Id_Set);
    //修改自习室信息的第二次调用，也需要更改涉及到的座位表信息，这个函数是对不开放座位的修改
    void modifySeatsNotOpen(@Param("buildingNum") String buildingNum, @Param("classRoomNum") String classRoomNum);
    //修改自习室信息的第二次调用，也需要更改涉及到的座位表信息，这个函数是对开放座位的修改
    void modifySeatsOpen(@Param("buildingNum") String buildingNum, @Param("classRoomNum") String classRoomNum, @Param("if_Open") boolean if_Open,
                        @Param("open_Time") String open_Time, @Param("close_Time") String close_Time);
    //对第二次调用中的可能存在的座位修改的实现，首先删除所有现存的座位，让后将新添加的座位加到表中。
    void del_Seats(@Param("buildingNum") String buildingNum, @Param("classRoomNum") String classRoomNum);
    void add_Seats(@Param("buildingNum") String buildingNum, @Param("classRoomNum") String classRoomNum, @Param("seat_Id") String seat_Id,
                   @Param("if_Open") boolean if_Open, @Param("open_Time") String open_Time, @Param("close_Time") String close_Time,
                   @Param("if_Near_Window")boolean if_Near_Window, @Param("if_Near_Power")boolean if_Near_Power);
}
