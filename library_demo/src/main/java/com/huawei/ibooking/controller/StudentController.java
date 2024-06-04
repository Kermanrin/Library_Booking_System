package com.huawei.ibooking.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.huawei.ibooking.business.StudentBusiness;
import com.huawei.ibooking.model.Booking_xxxDO;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;


@RestController
public class StudentController {
    @Autowired
    private StudentBusiness stuBiz;

    @GetMapping(value = "/student")
    @Tag(name = "StudentController1", description = "返回学生列表")
    public ResponseEntity<ApiResponse<List<StudentDO>>> list() {
        List<StudentDO> students = stuBiz.getStudents();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), students), HttpStatus.OK);
    }

    @PostMapping(value = "/student/query")
    @Tag(name = "Query_students_by_stuNum", description = "根据学号查询学生信息")
    public ResponseEntity<Map<String, Object>> query(@RequestBody JsonNode requestBody) {
        String stuNum = requestBody.get("stuNum").asText();
        Optional<StudentDO> studentOptional = stuBiz.getStudent(stuNum);

        if (studentOptional.isPresent()) {
            // 学生存在，返回学生信息
            StudentDO student = studentOptional.get();
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("code", HttpStatus.OK.value());
            response.put("stuNum", student.getStuNum());
            response.put("name", student.getName());
            response.put("password", student.getPassword());
            return ResponseEntity.ok(response);
        } else {
            // 学生不存在，返回固定的响应
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("code", 201);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/student")
    @Tag(name = "StudentController3", description = "添加学生信息")
    public ResponseEntity<ApiResponse<Void>> add(@RequestBody StudentDO student) {
        boolean result = stuBiz.saveStudent(student);
        HttpStatus status = result ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ApiResponse<>(status.value(), null), status);
    }

    @PutMapping(value = "/student/modify")
    @Tag(name = "Modify_student_info", description = "修改学生信息")
    public ResponseEntity<Map<String, Object>> save(@RequestBody JsonNode requestBody) {
        try {
            String stuNum = requestBody.get("stuNum").asText();
            String name = requestBody.get("name").asText();
            String password = requestBody.get("password").asText();
            StudentDO student = new StudentDO();
            student.setStuNum(stuNum);
            student.setName(name);
            student.setPassword(password);
            boolean result = stuBiz.saveStudent(student);
            Map<String, Object> response = new LinkedHashMap<>();

            if (result) {
                // 保存成功，设置状态码为 200
                response.put("code", HttpStatus.OK.value());
            } else {
                // 保存失败，设置状态码为 201
                response.put("code", HttpStatus.CREATED.value());
            }

            return ResponseEntity.ok(response);
        }catch (Exception e){
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("code", 201);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/delete_student")
    @Tag(name = "StudentController5", description = "根据学号删除学生信息")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody JsonNode requestBody) {
        try {
            String stuNum = requestBody.get("stuNum").asText();
            boolean result = stuBiz.deleteStudent(stuNum);
            Map<String, Object> response = new LinkedHashMap<>();
            if(result){
                response.put("code", HttpStatus.OK.value());
            }else{
                response.put("code", 201);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e){
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("code", 201);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/history/StuID")
    @Tag(name = "StudentController_Of_History_Query", description = "使用历史查询")
    public ResponseEntity<ApiResponse<List<Booking_xxxDO>>> get_Records(@RequestBody JsonNode requestBody)
    {
        try {
            String stuId = requestBody.get("stuId").asText();
            boolean if_Stu_Exist = stuBiz.stu_Exist(stuId);
            boolean flag = true;
            if (!if_Stu_Exist) {
                flag = false ;
            }
            List<Booking_xxxDO> Records = stuBiz.get_Records(stuId);
            if (Records.isEmpty())
            {
                flag = false;
            }
            if(!flag) return new ResponseEntity<>(new ApiResponse<>(201, null), HttpStatus.NOT_FOUND);
            else return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), Records), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(201, null), HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/student/search_seat")
    @Tag(name = "Personalized_recommended_seats", description = "个性化推荐座位")
    public ResponseEntity<Map<String, Object>> seat_search(@RequestBody JsonNode requestBody)
    {
        try {
            String buildingNum = requestBody.get("buildingNum").asText();
            String classroomNum = requestBody.get("classroomNum").asText();
            boolean ifNearWindow = requestBody.has("if_Near_Window") ? Boolean.parseBoolean(requestBody.get("if_Near_Window").asText()) : false;
            boolean ifNearPower = requestBody.has("if_Near_Power") ? Boolean.parseBoolean(requestBody.get("if_Near_Power").asText()) : false;

            List<SeatDO> Records = stuBiz.get_Seats(buildingNum, classroomNum, ifNearWindow, ifNearPower);
            List<Object> formattedRooms = new ArrayList<>();
            for (SeatDO seat : Records) {
                if (seat.getIf_Open()) {
                    Map<String, Object> formattedRoom = new LinkedHashMap<>();
                    formattedRoom.put("seat_Id", seat.getSeat_Id());
                    formattedRoom.put("Open_Time", seat.getOpen_Time());
                    formattedRoom.put("Close_Time", seat.getClose_Time());
                    formattedRooms.add(formattedRoom);
                }
            }
            if (formattedRooms.isEmpty()) {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("code", 201);
                return ResponseEntity.ok(response);
            } else {
                // 返回成功响应
                Map<String, Object> successResponse = new LinkedHashMap<>();
                successResponse.put("code", HttpStatus.OK.value());
                successResponse.put("list", formattedRooms);
                return ResponseEntity.ok(successResponse);
            }
        }catch (Exception e){
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("code", 201);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/student/prebook")
    @Tag(name = "StudentController_Of_Prebook_Seat", description = "学生预约座位")
    public ResponseEntity<Map<String, Object>> prebook_seat(@RequestBody JsonNode requestBody)
    {
        try {
            String buildingNum = requestBody.get("buildingNum").asText();
            String classroomNum = requestBody.get("classroomNum").asText();
            String seat_Id = requestBody.get("seat_Id").asText();
            String begin_time = requestBody.get("begin_time").asText();
            String end_time = requestBody.get("end_time").asText();
            boolean if_Online = requestBody.has("if_Online") ? Boolean.parseBoolean(requestBody.get("if_Online").asText()) : false;
            String stuId = requestBody.get("stuNum").asText();
            SeatDO Record = stuBiz.pre_Seat(buildingNum, classroomNum, seat_Id);
            int begin_hour = Integer.parseInt(begin_time.substring(0, 2));
            int begin_min = Integer.parseInt(begin_time.substring(2));
            int end_hour = Integer.parseInt(end_time.substring(0, 2));
            int end_min = Integer.parseInt(end_time.substring(2));
            int pre_begin = begin_hour * 60 + begin_min;
            int pre_end = end_hour * 60 + end_min;
            String open_time = Record.getOpen_Time();
            String close_time = Record.getClose_Time();
            int open_hour = Integer.parseInt(open_time.substring(0, 2));
            int open_min = Integer.parseInt(open_time.substring(2));
            int close_hour = Integer.parseInt(close_time.substring(0, 2));
            int close_min = Integer.parseInt(close_time.substring(2));
            int seat_begin = open_hour * 60 + open_min;
            int seat_end = close_hour * 60 + close_min;
            boolean if_pre = true;
            List<Booking_xxxDO> his_Records = stuBiz.his_Seat(buildingNum, classroomNum, seat_Id);
            if (!Record.getIf_Open()) {                                        //座位未开放，则无法预约
                if_pre = false;
            } else if ((pre_begin < seat_begin) | (pre_end > seat_end) | (pre_end - pre_begin) > 240) { //预约早于开放时间，或晚于关闭时间，或超过4小时，则无法预约
                if_pre = false;
            } else if (!his_Records.isEmpty()) {        //座位之前有被人预约过，则遍历预约记录看是否时间有冲突
                for (Booking_xxxDO his : his_Records) {
                    String his_begin_time = his.getBegin_Time();
                    String his_end_time = his.getEnd_Time();
                    int his_begin_hour = Integer.parseInt(his_begin_time.substring(0, 2));
                    int his_begin_min = Integer.parseInt(his_begin_time.substring(2));
                    int his_end_hour = Integer.parseInt(his_end_time.substring(0, 2));
                    int his_end_min = Integer.parseInt(his_end_time.substring(2));
                    int his_begin = his_begin_hour * 60 + his_begin_min;
                    int his_end = his_end_hour * 60 + his_end_min;
                    if (!((pre_end <= his_begin) | (pre_begin >= his_end))) {
                        if_pre = false;
                        break;
                    }
                }
            }
            if (!if_pre) {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("code", 201);
                return ResponseEntity.ok(response);
            } else {
                int k = stuBiz.his_Insert(buildingNum, classroomNum, seat_Id, begin_time, end_time, if_Online, stuId);
                //            int insert_Id = stuBiz.last_Id();
                //            String Id_str = Integer.toString(insert_Id);
                //            String encoder = Base64.getEncoder().encodeToString(Id_str.getBytes());
                //            byte[] decoder_byte = Base64.getDecoder().decode(encoder);
                //            String decoder = new String(decoder_byte);
                Map<String, Object> successResponse = new LinkedHashMap<>();
                successResponse.put("code", HttpStatus.OK.value());
                //            successResponse.put("identifyNum", encoder);
                return ResponseEntity.ok(successResponse);
            }
        }catch (Exception e){
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("code", 201);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(value = "/student/sign_in")
    @Tag(name = "StudentController_Of_Sign_In", description = "学生签到")
    public ResponseEntity<Map<String, Object>> sign_in(@RequestBody JsonNode requestBody)
    {
        try {
            String buildingNum = requestBody.get("buildingNum").asText();
            String classroomNum = requestBody.get("classroomNum").asText();
            String seat_Id = requestBody.get("seat_Id").asText();
            String stuNum = requestBody.get("stuNum").asText();
            String begin_time = requestBody.get("begin_time").asText();
            String end_time = requestBody.get("end_time").asText();
            //        String identifyNum = requestBody.get("identifyNum").asText();
            //        byte[] decoder_byte = Base64.getDecoder().decode(identifyNum);
            //        String decoder = new String(decoder_byte);
            Booking_xxxDO record = stuBiz.his_Info(buildingNum, classroomNum, seat_Id, begin_time, end_time);
            if ((record != null) & (stuNum.equals(record.getStuId()))) {
                Map<String, Object> successResponse = new LinkedHashMap<>();
                successResponse.put("code", HttpStatus.OK.value());
                return ResponseEntity.ok(successResponse);
            } else {
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("code", 201);
                return ResponseEntity.ok(response);
            }
        }catch (Exception e){
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("code", 201);
            return ResponseEntity.ok(response);
        }
    }

}

