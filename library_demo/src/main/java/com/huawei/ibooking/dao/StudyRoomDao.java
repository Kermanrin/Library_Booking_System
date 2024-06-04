package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.StudyRoomMapper;
import com.huawei.ibooking.model.Seat_If_Open_State;
import com.huawei.ibooking.model.StudyRoomDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudyRoomDao {
    @Autowired
    private StudyRoomMapper StudyRoomMapper;
    @Autowired
    private StudyRoomMapper studyRoomMapper;

    public List<StudyRoomDO> getAllStudyRooms(){
        return StudyRoomMapper.getAllStudyRooms();
    }

    public boolean saveStudyRoom(final StudyRoomDO study){
        return StudyRoomMapper.saveStudyRoom(study) > 0;
    }

    public boolean deleteStudyRoom(final String buildingNum, final String classRoomNum){
        return StudyRoomMapper.deleteStudyRoom(buildingNum, classRoomNum) > 0;
    }

    public List<StudyRoomDO> getStudyRooms(final String buildingNum){
        return StudyRoomMapper.getStudyRooms(buildingNum);
    }

    public List<Seat_If_Open_State> getStudySeats(String buildingNum, String classRoomNum)
    {
        return StudyRoomMapper.getStudySeats(buildingNum, classRoomNum);
    }

    //配合自习室修改函数调整seats表：数据库add新加的座位
    public void add_Seats(final String buildingNum, final String classRoomNum, final String seat_Id_Set, final boolean if_Open, final String open_Time, final String close_Time)
    {
        studyRoomMapper.del_Seats(buildingNum, classRoomNum);//先把对应自习室的原来的座位删掉
        int len = seat_Id_Set.length();
        int i = 0;
        while(i <= len - 1)
        {
            String tmp = seat_Id_Set.substring(i, i + 2);
            i += 3;
            studyRoomMapper.add_Seats(buildingNum, classRoomNum, tmp, if_Open, open_Time, close_Time, false, false);
        }
    }

    public boolean modifySeatsAttribute(String buildingNum, String classRoomNum, boolean if_Near_Window, boolean if_Near_Power, String seat_Id, boolean if_Open)
    {
        return StudyRoomMapper.modifySeatsAttribute(buildingNum, classRoomNum, if_Near_Window, if_Near_Power, seat_Id, if_Open);
    }

    public boolean modifyStudyRoom(final String buildingNum, final String classRoomNum, final boolean if_Open, final String open_Time, final String close_Time, final String Seat_Id_Set){
        if(if_Open)
        {
            studyRoomMapper.modifySeatsOpen(buildingNum, classRoomNum, true, open_Time, close_Time);
        }
        else
        {
            studyRoomMapper.modifySeatsNotOpen(buildingNum, classRoomNum);
        }
        return StudyRoomMapper.modifyStudyRoom(buildingNum, classRoomNum, if_Open, open_Time, close_Time, Seat_Id_Set) > 0;
    }
}
