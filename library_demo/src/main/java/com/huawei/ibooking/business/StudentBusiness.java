package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.StudentDao;
import com.huawei.ibooking.model.Booking_xxxDO;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Component
public class StudentBusiness {
    @Autowired
    private StudentDao studentDao;

    public List<StudentDO> getStudents() {
        return studentDao.getStudents();
    }

    public Optional<StudentDO> getStudent(final String stuNum) {
        List<StudentDO> students = studentDao.getStudent(stuNum);
        if (students.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(students.get(0));
    }

    public boolean saveStudent(final StudentDO stu) {
        return studentDao.saveStudent(stu);
    }

    public boolean deleteStudent(final String stuNum) {
        return studentDao.deleteStudent(stuNum);
    }

    //纯后端API，查询学生是否存在，只返回一个boolean类型
    public boolean stu_Exist(final String stuNum)
    {
        return studentDao.stu_Exist(stuNum);
    }

    //历史记录查询，输入学好返回一个历史记录列表
    public List<Booking_xxxDO> get_Records(final String stuId)
    {
        return studentDao.get_Records(stuId);
    }

    public List<SeatDO> get_Seats(final String buildingNum, final String classroomNum, final boolean if_Near_Window, final boolean if_Near_Power){return studentDao.get_Seats(buildingNum, classroomNum, if_Near_Window, if_Near_Power);}

    public SeatDO pre_Seat(final String buildingNum, final String classroomNum, final String seat_Id){return studentDao.pre_Seat(buildingNum, classroomNum, seat_Id);}

    public List<Booking_xxxDO> his_Seat(final String buildingNum, final String classroomNum, final String seat_Id){return studentDao.his_Seat(buildingNum, classroomNum, seat_Id);}

    public int his_Insert(final String buildingNum, final String classroomNum, final String seat_Id, final String begin_Time, final String end_Time, final boolean if_Online, final String stuId)
    {
        return studentDao.his_Insert(buildingNum, classroomNum, seat_Id, begin_Time, end_Time, if_Online, stuId);
    }

    public int last_Id(){return studentDao.last_Id();}

    public Booking_xxxDO his_Info(final String buildingNum, final String classroomNum, final String seat_Id, final String begin_Time, final String end_Time)
    {
        return studentDao.his_Info(buildingNum, classroomNum, seat_Id, begin_Time, end_Time);
    }
}
