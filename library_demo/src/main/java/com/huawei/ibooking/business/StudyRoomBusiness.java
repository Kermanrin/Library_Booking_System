package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.StudyRoomDao;
import com.huawei.ibooking.model.Seat_If_Open_State;
import com.huawei.ibooking.model.StudyRoomDO;
import org.springframework.data.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudyRoomBusiness {
    @Autowired
    private StudyRoomDao studyRoomDao;

    public List<StudyRoomDO> getAllStudyRooms(){
        return studyRoomDao.getAllStudyRooms();
    }

//    public Optional<StudyRoomDO> getStudyRooms(final String buildingNum){
//        List<StudyRoomDO> studentRooms = studyRoomDao.getStudyRooms(buildingNum);
//        if (studentRooms.isEmpty()){
//            return Optional.empty();
//        }
//        return Optional.ofNullable(studentRooms.get(0));
//    }

    public List<Seat_If_Open_State> getStudySeats(String buildingNum, String classRoomNum)
    {
        return studyRoomDao.getStudySeats(buildingNum, classRoomNum);
    }


    public boolean saveStudyRoom(final StudyRoomDO study){
        return studyRoomDao.saveStudyRoom(study);
    }

    public boolean deleteStudyRoom(final String buildingNum, final String classRoomNum){
        return studyRoomDao.deleteStudyRoom(buildingNum, classRoomNum);
    }

    public List<StudyRoomDO> getStudyRooms(final String buildingNum){
        return studyRoomDao.getStudyRooms(buildingNum);
    }

    public boolean modifySeatsAttribute(String buildingNum, String classRoomNum, boolean if_Near_Window, boolean if_Near_Power, List<String> seats_Id, List<Integer> if_Open)
    {
        for(int i = 0 ; i < if_Open.size() ; i++)
        {
            boolean flag ;
            flag = if_Open.get(i) == 1;
            String seat_Id = seats_Id.get(i);
            if(!studyRoomDao.modifySeatsAttribute(buildingNum, classRoomNum, if_Near_Window, if_Near_Power, seat_Id, flag)) return false;
        }
        return true;
    }

    public boolean modifyStudyRoom(final String buildingNum, final String classRoomNum, final boolean if_Open, final String open_Time, final String close_Time, final String seat_Id_Set)
    {
        studyRoomDao.add_Seats(buildingNum, classRoomNum, seat_Id_Set, if_Open, open_Time, close_Time);
        return studyRoomDao.modifyStudyRoom(buildingNum, classRoomNum, if_Open, open_Time, close_Time, seat_Id_Set);
    }
}
