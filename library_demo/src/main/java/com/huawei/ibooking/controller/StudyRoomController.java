package com.huawei.ibooking.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.ibooking.business.StudyRoomBusiness;
import com.huawei.ibooking.model.Seat_If_Open_State;
import com.huawei.ibooking.model.StudyRoomDO;
import com.huawei.ibooking.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class StudyRoomController {

    @Autowired
    private StudyRoomBusiness stuRooBiz;

    @GetMapping(value = "/studyroom")
    @Tag(name = "StudyRoomController1", description = "返回自习室列表")
    public ResponseEntity<ApiResponse<List<StudyRoomDO>>> list() {
        List<StudyRoomDO> studyrooms = stuRooBiz.getAllStudyRooms();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), studyrooms), HttpStatus.OK);
    }

//    @GetMapping(value = "/studyroom/{buildingNum}")
//    @Tag(name = "StudyRoomController2", description = "根据教学楼号查询该教学楼的所有自习室")
//    public ResponseEntity<ApiResponse<List<StudyRoomDO>>> list(@PathVariable("buildingNum") String buildingNum) {
//        List<StudyRoomDO> one_building_rooms = stuRooBiz.getStudyRooms(buildingNum);
//        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), one_building_rooms), HttpStatus.OK);
//    }

    @PostMapping(value = "/studyroom")
    @Tag(name = "StudyRoomController3", description = "添加自习室")
    public ResponseEntity<ApiResponse<Void>> add(@RequestBody StudyRoomDO studyRoom) {
        boolean result = stuRooBiz.saveStudyRoom(studyRoom);
        HttpStatus status = result ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ApiResponse<>(status.value(), null), status);
    }

    @DeleteMapping(value = "/studyroom/{buildingNum}/{classRoomNum}")
    @Tag(name = "StudyRoomController4", description = "根据教学楼号和自习室号删除自习室")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("buildingNum") String buildingNum,
                                                    @PathVariable("classRoomNum") String classRoomNum) {
        boolean result = stuRooBiz.deleteStudyRoom(buildingNum, classRoomNum);
        HttpStatus status = result ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ApiResponse<>(status.value(), null), status);
    }

    @PostMapping(value = "/studyroom/buildingNum_&_classRoomNum")
    @Tag(name = "StudyRoomController_Of_Query_Seats_By_BuildNum", description = "根据楼号返回各个自习室的座位的状态")
    public ResponseEntity<ApiResponse<List<Seat_If_Open_State>>> query_Seats_By_BuildNum(@RequestBody JsonNode requestBody_Seats_State) {
        try {
            String buildingNum = requestBody_Seats_State.get("buildingNum").asText();
            String classRoomNum = requestBody_Seats_State.get("classRoomNum").asText();
            List<Seat_If_Open_State> result = stuRooBiz.getStudySeats(buildingNum, classRoomNum);

            if (result.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse<>(201, null), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), result), HttpStatus.OK);
            }
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new ApiResponse<>(201, null), HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/student/search_room")
    @Tag(name = "StudyRoomController_Of_Query_Rooms_By_BuildNum", description = "根据楼号返回各个自习室状态")
    public ResponseEntity<Map<String, Object>> query_Rooms_By_BuildNum(@RequestBody JsonNode requestBody) {
        try {
            String buildingNum = requestBody.get("buildingNum").asText();
            List<StudyRoomDO> result = stuRooBiz.getStudyRooms(buildingNum);
            if (result.isEmpty()) {
                // 没有找到自习室信息，返回 NO_CONTENT
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("code", 201);
                return ResponseEntity.ok(response);
            } else {
                // 构造成功响应的数据格式
                List<Object> formattedRooms = new ArrayList<>();
                for (StudyRoomDO room : result) {
                    // 构造每个自习室的格式
                    Map<String, Object> formattedRoom = new LinkedHashMap<>();
                    formattedRoom.put("classroomNum", room.getClassRoomNum());
                    formattedRoom.put("if_Open", room.getIf_Open());
                    formattedRoom.put("Open_Time", room.getOpen_Time());
                    formattedRoom.put("Close_Time", room.getClose_Time());
                    formattedRooms.add(formattedRoom);
                }
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

    @PostMapping(value = "/seats/attribute_Of_Seats")
    @Tag(name = "SeatsController_Of_Modify_Seats_Attributes", description = "修改自习室座位状态")
    public ResponseEntity<ApiResponse<Void>> modify_Seats_Attribute(@RequestBody JsonNode requestBody_Seats_Attribute) {
        try{
            String buildingNum = requestBody_Seats_Attribute.get("buildingNum").asText();
            String classRoomNum = requestBody_Seats_Attribute.get("classRoomNum").asText();
            boolean if_Near_Window = requestBody_Seats_Attribute.get("if_Near_Window").asBoolean();
            boolean if_Near_Power = requestBody_Seats_Attribute.get("if_Near_Power").asBoolean();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(requestBody_Seats_Attribute.toString());
            JsonNode if_Open_Node = rootNode.path("if_Open");
            JsonNode seats_Id_Node = rootNode.path("seats_Id");

            List<Integer> if_Open = mapper.readerForListOf(Integer.class).readValue(if_Open_Node);
            List<String> seats_Id = mapper.readerForListOf(String.class).readValue(seats_Id_Node);

            ApiResponse<String> warning = new ApiResponse<>(HttpStatus.OK.value(), null);
            if(seats_Id.size() != if_Open.size())
            {
                return  new ResponseEntity<>(new ApiResponse<>(201, null), HttpStatus.BAD_REQUEST);
            }

            boolean res = stuRooBiz.modifySeatsAttribute(buildingNum, classRoomNum, if_Near_Window, if_Near_Power, seats_Id, if_Open);

            HttpStatus status = res ? HttpStatus.OK : HttpStatus.CREATED;
            if(status == HttpStatus.OK)
            {
                warning.setCode(HttpStatus.OK.value());
            }
            else
            {
                warning.setCode(201);
            }
            return new ResponseEntity<>(new ApiResponse<>(warning.getCode(), null), status);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(new ApiResponse<>(201, null), HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/studyroom/{buildingNum}/{classRoomNum}")
    @Tag(name = "StudyRoomController_Of_Modify_Rooms", description = "修改自习室状态")
    public ResponseEntity<ApiResponse<String>> modify_Rooms(@RequestParam("buildingNum") String buildingNum , @RequestParam("classRoomNum") String classRoomNum,
                                                          @RequestParam("if_Open") boolean if_open, @RequestParam("open_Time") String open_Time, @RequestParam("close_Time") String close_Time,
                                                          @RequestParam("Seat_Id_Set") String Seat_Id_Set) {

        boolean result;
        int int_Open_time = Integer.parseInt(open_Time);
        int int_Close_time = Integer.parseInt(close_Time);
        ApiResponse<String> warning = new ApiResponse<>(1, null);


        if (if_open) {
            //            //使用整点签到和预定时用这个语句判定
            //            if(int_Close_time <= int_Open_time || int_Close_time < 0 || int_Open_time < 0 || int_Close_time > 24 || int_Open_time > 24)
            //            {
            //                warning.setCode(0);
            //                warning.setData("Illegal parameters.");
            //                return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), warning.getData()), HttpStatus.BAD_REQUEST);
            //            }
            //使用小时和分钟签到和预定时用这个语句判定

            if (int_Close_time < 0)
            {
                warning.setCode(201);
                warning.setData("Illegal parameters: close time less than 0.");
                result = false;
            }
            else if (int_Open_time < 0)
            {
                warning.setCode(201);
                warning.setData("Illegal parameters: open time less than 0.");
                result = false;
            }
            else if (int_Open_time > 2400)
            {
                warning.setCode(201);
                warning.setData("Illegal parameters: open time large than 2400.");
                result = false;
            }
            else if (int_Close_time > 2400)
            {
                warning.setCode(201);
                warning.setData("Illegal parameters: close time large than 2400.");
                result = false;
            }
            else if (int_Close_time <= int_Open_time) {
                warning.setCode(201);
                warning.setData("Illegal parameters: close time less than open time.");
                result = false;
            }
            else
            {
                result = stuRooBiz.modifyStudyRoom(buildingNum, classRoomNum, true, open_Time, close_Time, Seat_Id_Set);
            }
        } else {
            if (int_Open_time != 0 || int_Close_time != 0) {
                warning.setCode(201);
                warning.setData("warning : unavailable room open/close time are only 0");
            }
            result = stuRooBiz.modifyStudyRoom(buildingNum, classRoomNum, false, "0", "0", Seat_Id_Set);
        }
        HttpStatus status = result ? HttpStatus.OK : HttpStatus.CREATED;
        if (status == HttpStatus.OK){
            return new ResponseEntity<>(new ApiResponse<>(status.value(), warning.getData()), status);
        }
        else
        {
            return new ResponseEntity<>(new ApiResponse<>(201, warning.getData()), HttpStatus.CREATED);
        }
    }
}