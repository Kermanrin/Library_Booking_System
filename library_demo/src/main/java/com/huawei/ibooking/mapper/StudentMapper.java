package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.model.Booking_xxxDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<StudentDO> getStudents();

    List<StudentDO> getStudent(@Param("stuNum") String stuNum);

    int saveStudent(@Param("stu") StudentDO stu);

    int deleteStudent(@Param("stuNum") String stuNum);

    List<StudentDO> stu_Exist(@Param("stuNum") String stuNum);

    List<Booking_xxxDO> get_Records(@Param("stuId") String stuId);

    List<SeatDO> get_Seats(@Param("buildingNum") String buildingNum, @Param("classroomNum") String classroomNum, @Param("if_Near_Window") boolean if_Near_Window, @Param("if_Near_Power") boolean if_Near_Power);

    SeatDO pre_Seat(@Param("buildingNum") String buildingNum, @Param("classroomNum") String classroomNum, @Param("seat_Id") String seat_Id);

    List<Booking_xxxDO> his_Seat(@Param("buildingNum") String buildingNum, @Param("classroomNum") String classroomNum, @Param("seat_Id") String seat_Id);

    int his_Insert(@Param("buildingNum")  String buildingNum, @Param("classroomNum") String classroomNum, @Param("seat_Id") String seat_Id, @Param("begin_Time") String begin_Time, @Param("end_Time") String end_Time, @Param("if_Online") boolean if_Online, @Param("stuId") String stuId);

    int last_Id();

    Booking_xxxDO his_Info(@Param("buildingNum")  String buildingNum, @Param("classroomNum") String classroomNum, @Param("seat_Id") String seat_Id, @Param("begin_Time") String begin_Time, @Param("end_Time") String end_Time);
}
