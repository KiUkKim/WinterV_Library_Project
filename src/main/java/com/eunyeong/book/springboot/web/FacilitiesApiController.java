package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.facility.Seats;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.facilities.FacilitiesService;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.FacilitiesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FacilitiesApiController {
    private final FacilitiesService facilitiesService;
    private final UserService userService;

    /**
     * 도서관별 열람실 정보 가져오기
     */
    @GetMapping("/readingroom")
    @ResponseBody
    public Map<String, Object> readingRoom(@RequestBody HashMap<String, Long> param) {

        Long library_id = param.get("library_id");

        Map<String, Object> map = new HashMap<>();

        map.put("readingRoom", facilitiesService.findReadingRoomByLibraryId(library_id));

        return map;
    }

    /**
     * 좌석 배정
     */
    @PutMapping("seat/assignment")
    public Long seatAssignment(@RequestBody HashMap<String, String> param){
        LocalTime useT = LocalTime.parse(param.get("useT"));
        Long user_id = Long.parseLong(param.get("user_id"));
        Long seat_id = Long.parseLong(param.get("seat_id"));

        User user = userService.findUser(user_id);

        FacilitiesDto.SeatsUpdateRequestDto requestDto = new FacilitiesDto.SeatsUpdateRequestDto();
        requestDto.setStartT(LocalDateTime.now());
        requestDto.setUseT(useT);
        requestDto.setCheckT(LocalDateTime.now().plusMinutes(10));
        requestDto.setAssignmentT(LocalDateTime.now().plusHours(useT.getHour()).plusMinutes(useT.getMinute()));
        requestDto.setUser(user);

        return facilitiesService.seatAssignment(seat_id, requestDto);
    }

    /**
     * 좌석배정 정보 가져오기
     */
    @GetMapping("/seat/state")
    @ResponseBody
    public Seats seatRecord(@RequestBody HashMap<String, Long> param) {

        Long user_id = param.get("user_id");

        return facilitiesService.findSeatRecordByUserId(user_id);

    }


}
