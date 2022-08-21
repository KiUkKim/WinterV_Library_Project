//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.facility.FacilityReserve;
import com.eunyeong.book.springboot.domain.facility.Seats;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.facilities.FacilitiesService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.FacilitiesDto.FacilityReserveSaveRequestDto;
import com.eunyeong.book.springboot.web.dto.FacilitiesDto.SeatsUpdateRequestDto;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacilitiesApiController {
    private final FacilitiesService facilitiesService;
    private final UserService userService;

    @GetMapping({"/readingroom/{library_id}"})
    @ResponseBody
    public Map<String, Object> readingRoom(@PathVariable Long library_id) {
        Map<String, Object> map = new HashMap();
        map.put("readingRoom", this.facilitiesService.findReadingRoomByLibraryId(library_id));
        return map;
    }

    @PutMapping({"seat/assignment"})
    public Long seatAssignment(@RequestBody HashMap<String, String> param) {
        LocalTime useT = LocalTime.parse((CharSequence)param.get("useT"));
        Long user_id = Long.parseLong((String)param.get("user_id"));
        Long seat_id = Long.parseLong((String)param.get("seat_id"));
        User user = this.userService.findUser(user_id);
        SeatsUpdateRequestDto requestDto = new SeatsUpdateRequestDto();
        requestDto.setStartT(LocalDateTime.now());
        requestDto.setUseT(useT);
        requestDto.setCheckT(LocalDateTime.now().plusMinutes(10L));
        requestDto.setAssignmentT(LocalDateTime.now().plusHours((long)useT.getHour()).plusMinutes((long)useT.getMinute()));
        requestDto.setUser(user);
        return this.facilitiesService.seatAssignment(seat_id, requestDto);
    }

    @GetMapping({"/seat/state"})
    @ResponseBody
    public Seats seatRecord(@RequestBody HashMap<String, Long> param) {
        Long user_id = (Long)param.get("user_id");
        return this.facilitiesService.findSeatRecordByUserId(user_id);
    }

    @PutMapping({"/facility/reserve"})
    @ResponseBody
    public void reserveFacility(@RequestBody HashMap<String, String> param) {
        Long id = (long)Integer.parseInt((String)param.get("id"));
        this.facilitiesService.findFacilityInfo(id);
        Long user_id = (long)Integer.parseInt((String)param.get("user_id"));
        User user = this.userService.findUser(user_id);
        String startDateTime_str = (String)param.get("startDateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTime_str, formatter);
        String endDateTime_str = (String)param.get("endDateTime");
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTime_str, formatter);
        FacilityReserveSaveRequestDto reserveRequestDto = new FacilityReserveSaveRequestDto();
        reserveRequestDto.setUser(user);
        reserveRequestDto.setStartDateTime(startDateTime);
        reserveRequestDto.setEndDateTime(endDateTime);
        this.facilitiesService.saveFacilityReserve(reserveRequestDto);
    }

    @GetMapping({"/facility/reserve/state"})
    @ResponseBody
    public List<FacilityReserve> ReserveAllList(@RequestBody HashMap<String, Long> param) {
        Long user_id = (Long)param.get("user_id");
        return this.facilitiesService.facilityReserveAllDesc(user_id);
    }

    public FacilitiesApiController(final FacilitiesService facilitiesService, final UserService userService) {
        this.facilitiesService = facilitiesService;
        this.userService = userService;
    }
}
