package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.StudentMapper;
import com.huawei.ibooking.model.Booking_xxxDO;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDao {
    @Autowired
    private StudentMapper studentMapper;

    public List<StudentDO> getStudents() {
        return studentMapper.getStudents();
    }

    public List<StudentDO> getStudent(final String stuNum) {
        return studentMapper.getStudent(stuNum);
    }

    public boolean saveStudent(final StudentDO stu) {
        return studentMapper.saveStudent(stu) > 0;
    }

    public boolean deleteStudent(final String stuNum) {
        return studentMapper.deleteStudent(stuNum) > 0;
    }

    public boolean stu_Exist(final String stuNum) {
        return !studentMapper.stu_Exist(stuNum).isEmpty();
    }

    public List<Booking_xxxDO> get_Records(final String stuId)
    {
        return studentMapper.get_Records(stuId);
    }

    public List<SeatDO> get_Seats(final String buildingNum, final String classroomNum, final boolean if_Near_Window, final boolean if_Near_Power){return studentMapper.get_Seats(buildingNum, classroomNum, if_Near_Window, if_Near_Power);}

    public SeatDO pre_Seat(final String buildingNum, final String classroomNum, final String seat_Id){return studentMapper.pre_Seat(buildingNum, classroomNum, seat_Id);}

    public List<Booking_xxxDO> his_Seat(final String buildingNum, final String classroomNum, final String seat_Id){return studentMapper.his_Seat(buildingNum, classroomNum, seat_Id);}

    public int his_Insert(final String buildingNum, final String classroomNum, final String seat_Id, final String begin_Time, final String end_Time, final boolean if_Online, final String stuId)
    {
        return studentMapper.his_Insert(buildingNum, classroomNum, seat_Id, begin_Time, end_Time, if_Online, stuId);
    }

    public int last_Id(){return studentMapper.last_Id();}

    public Booking_xxxDO his_Info(final String buildingNum, final String classroomNum, final String seat_Id, final String begin_Time, final String end_Time)
    {
        return studentMapper.his_Info(buildingNum, classroomNum, seat_Id, begin_Time, end_Time);
    }
}
