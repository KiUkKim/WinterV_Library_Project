package com.eunyeong.book.springboot.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class testService {

    public Long testService(Long library_id)
    {
        return library_id;
    }

    public String testParam(String keyword)
    {
        return keyword;
    }
}
