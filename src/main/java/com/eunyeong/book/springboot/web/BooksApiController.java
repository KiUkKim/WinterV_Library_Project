package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.books.Books;
import com.eunyeong.book.springboot.domain.books.Category;
import com.eunyeong.book.springboot.domain.books.CategoryRepository;
import com.eunyeong.book.springboot.domain.books.CollectInfo;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.books.BooksService;

import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.BooksDto;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BooksApiController {
    private final BooksService booksService;
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    /**
     * books 저장
     */
    @PostMapping("/books/save")
    @ResponseBody
    public void booksSave(@RequestBody BooksDto.BooksSaveRequestDto bookRequestDto){
//        Assert.notNull(bookRequestDto, "bookRequestDto must not be NULL");

        booksService.saveBooks(bookRequestDto);
    }

    /**
     * collectInfo 저장
     */
    @PostMapping("/collectinfo/save")
    @ResponseBody
    public Long collectInfoSave(@RequestBody BooksDto.CollectInfoListResponseDto collectInfoListResponseDto) {
        Books book=booksService.findBooks(collectInfoListResponseDto.getBook());
        Category collectLocation = booksService.findCategory(collectInfoListResponseDto.getCollectLocation());

        BooksDto.CollectInfoSaveRequestDto collectInfoSaveRequestDto = new BooksDto.CollectInfoSaveRequestDto();

        collectInfoSaveRequestDto.setBook(book);
        collectInfoSaveRequestDto.setCollectLocation(collectLocation);

        BeanUtils.copyProperties(collectInfoListResponseDto, collectInfoSaveRequestDto); // 주입된 Bean을 또다른 객체의 Bean으로 복사시 사용

        return booksService.saveCollectInfo(collectInfoSaveRequestDto);
    }


    /**
     * 도서 검색
     */
    @GetMapping("/book/search")
    @ResponseBody
    public Map<String, Object> search(@RequestBody HashMap<String, Object> param) {

        String keyword=param.get("keyword").toString();

        Map<String, Object> map = new HashMap<>();

        map.put("bookList", booksService.searchBooks(keyword));

        return map;
    }

    /**
     * 도서 검색(title로 검색)
     */
    @GetMapping("/book/detail")
    @ResponseBody
    public Map<String, Object> searchDetail(@RequestBody HashMap<String, Object> param){

        String title = param.get("title").toString();

        Map<String, Object> map = new HashMap<>();

        map.put("bookDetail", booksService.searchBooksDetail(title));

        return map;
    }

    /**
     * 대출 기능
     */
    @PutMapping("/book/loan")
    @ResponseBody
    public void loan(@RequestBody HashMap<String, Long> param){
        Long seq=param.get("seq");
        CollectInfo collectInfo=booksService.findCollectInfo(seq);

        Long user_id=param.get("user_id");
        User user = userService.findUser(user_id);

        BooksDto.CollectInfoUpdateRequestDto requestDto = new BooksDto.CollectInfoUpdateRequestDto() ;
        BeanUtils.copyProperties(collectInfo, requestDto);

        requestDto.setState(0);
        requestDto.setUser(user); //빌린 사람
        requestDto.setLoanDate(LocalDate.now());//대출날짜 // 컴퓨터의 현재 날짜 정보 2018-07-26
        requestDto.setReturnDate(LocalDate.now().plusDays(14)); // 반납일 2주 뒤
        requestDto.setExtensionCount(0);//연장횟수
        //reserveState는 수정 필요

        booksService.update(seq, requestDto);
    }

    /**
     * 대출현황 리스트 조회
     */
    @GetMapping("/book/loan/status")
    @ResponseBody
    public Map<String, Object> loan_status(@RequestBody HashMap<String, Long> param){
        Long user_id=param.get("user_id");
        User user=userService.findUser(user_id);

        Map<String, Object> map = new HashMap<>();

        map.put("loanStatus", booksService.loanStatus(user));
        return map;
    }

    /**
     * 카테고리(collectLocation) 리스트 조회
     */
    @GetMapping("/book/category")
    @ResponseBody
    public Map<String, Object> categoryList(){
        List<Category> categoryList = categoryRepository.findAll();

        Map<String, Object> map = new HashMap<>();

        map.put("categoryList", categoryList.stream().collect(Collectors.toMap(Category::getId, Category::getCollectLocation)));

        return map;
    }

}


