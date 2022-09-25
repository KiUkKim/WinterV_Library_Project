package com.eunyeong.book.springboot.web;


import com.eunyeong.book.springboot.domain.facility.FacilityInfo;
import com.eunyeong.book.springboot.domain.facility.FacilityReserve;
import com.eunyeong.book.springboot.domain.facility.Seats;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.facilities.FacilitiesService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.FacilitiesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FacilitiesApiController {
    private final FacilitiesService facilitiesService;
    private final UserService userService;

    @GetMapping({"/readingroom/{library_id}"})
    @ResponseBody
    public Map<String, Object> readingRoom(@PathVariable Long library_id) {
        Map<String, Object> map = new HashMap();

        map.put("readingRoom", facilitiesService.findReadingRoomByLibraryId(library_id));
        return map;
    }

    @PutMapping({"seat/assignment"})
    public Long seatAssignment(@RequestBody HashMap<String, String> param) {
        LocalTime useT = LocalTime.parse((CharSequence)param.get("useT"));
        Long user_id = Long.parseLong((String)param.get("user_id"));
        Long seat_id = Long.parseLong((String)param.get("seat_id"));
        User user = userService.findUser(user_id);
        FacilitiesDto.SeatsUpdateRequestDto requestDto = new FacilitiesDto.SeatsUpdateRequestDto();
        requestDto.setStartT(LocalDateTime.now());
        requestDto.setUseT(useT);
        requestDto.setCheckT(LocalDateTime.now().plusMinutes(10L));
        requestDto.setAssignmentT(LocalDateTime.now().plusHours((long)useT.getHour()).plusMinutes((long)useT.getMinute()));
        requestDto.setUser(user);
        return facilitiesService.seatAssignment(seat_id, requestDto);
    }

    //TODO
    // parameter 방식 -> 완료
    @GetMapping({"/seat/state"})
    @ResponseBody
    public Seats seatRecord(@RequestParam(name = "user_id", required = true) Long user_id) {
        return facilitiesService.findSeatRecordByUserId(user_id);
    }

//    public Seats seatRecord(@RequestBody HashMap<String, Long> param) {
//        Long user_id = (Long)param.get("user_id");
//        return this.facilitiesService.findSeatRecordByUserId(user_id);
//    }

    @PutMapping({"/facility/reserve"})
    @ResponseBody
    public void reserveFacility(@RequestBody HashMap<String, String> param) {
        Long facility_id = Long.valueOf(Integer.parseInt(param.get("facility_id")));
        FacilityInfo facilityInfo = facilitiesService.findFacilityInfo(facility_id);

        Long user_id = Long.valueOf(Integer.parseInt(param.get("user_id")));
        User user = userService.findUser(user_id);

        String startDateTime_str = param.get("startDateTime"); //"2021-11-05 13:47:13.248" 이런 형식으로 줘야 함
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTime_str, formatter);

        String endDateTime_str = param.get("endDateTime"); //"2021-11-05 13:47:13.248" 이런 형식으로 줘야 함
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTime_str, formatter);

        FacilitiesDto.FacilityReserveSaveRequestDto reserveRequestDto = new FacilitiesDto.FacilityReserveSaveRequestDto();

        reserveRequestDto.setUser(user);
        reserveRequestDto.setStartDateTime(startDateTime);
        reserveRequestDto.setEndDateTime(endDateTime);
        reserveRequestDto.setFacilityInfo(facilityInfo);

        facilitiesService.saveFacilityReserve(reserveRequestDto);
    }

    //TODO
    // JSON -> PARAM :: 완료
    @GetMapping({"/facility/reserve/state"})
    @ResponseBody
    public List<FacilityReserve> ReserveAllList(@RequestParam(name = "user_id" , required = true) Long user_id) {
        return facilitiesService.facilityReserveAllDesc(user_id);
    }

}

