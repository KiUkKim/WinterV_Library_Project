package com.eunyeong.book.springboot.web;


import com.eunyeong.book.springboot.service.facilities.FacilitiesService;
import com.eunyeong.book.springboot.web.dto.FacilitiesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FacilityApiController {
    private final FacilitiesService facilitiesService;

    @GetMapping("/InfoList")
    @ResponseBody
    public List<FacilitiesDto.InfoListDto> FacilityInfoList(){return facilitiesService.searchAllDescInfo();}
}
