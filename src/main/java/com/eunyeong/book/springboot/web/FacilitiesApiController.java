package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.service.facilities.FacilitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FacilitiesApiController {
    private final FacilitiesService facilitiesService;

    /**
     * 도서관별 열람실 정보 가져오기
     */
    @GetMapping("/readingroom")
    @ResponseBody
    public Map<String, Object> searchDetail(@RequestBody HashMap<String, Long> param) {

        Long library_id = param.get("library_id");

        Map<String, Object> map = new HashMap<>();

        map.put("readingRoom", facilitiesService.findReadingRoomByLibraryId(library_id));

        return map;
    }
}
