package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.service.test.testService;
import com.eunyeong.book.springboot.web.dto.TestDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TestApiController {

    private final testService testservice;

    @GetMapping("/testJson")
    @ResponseBody
    public String TestPrint(Model model, @RequestBody TestDto.TestResponseDto testResponseDto)
    {
        String library_id = testResponseDto.getLibrary_id();
        String test = "library_id : " + library_id;

        return test;
    }

    @GetMapping("/testParam/{id}")
    @ResponseBody
    public String TestPrintParam(Model model, @RequestParam Long library_id)
    {
        Long libraryid = testservice.testService(library_id);
        String test = "library_id : " + libraryid;
        return test;
    }

    @GetMapping("/testParam")
    @ResponseBody
    public String TestPrintParam2(Model model, @RequestParam Long library_id)
    {
        Long libraryid = testservice.testService(library_id);
        String test = "library_id : " + libraryid;
        return test;
    }

    @GetMapping("/testParam2")
    @ResponseBody
    public String TestPrintParam3(@RequestParam String keyword)
    {
        String key = testservice.testParam(keyword);
        String test = "keyword : " + key;
        return test;
    }

    @PutMapping("/community/test")
    @ResponseBody
    public UserDto.CommunitytestDto noticeDelete(@RequestBody UserDto.CommunitytestDto communitytestDto)
    {
        return communitytestDto;
    }
}
