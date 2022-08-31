package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.books.*;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.books.BooksService;

import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.BooksDto;
import com.eunyeong.book.springboot.web.dto.GetDto;
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


    /**
     * books 저장
     */
    @PostMapping(value = "/books/save", consumes = {"application/json"})
    @ResponseBody
    public void booksSave(@RequestBody BooksDto.BooksSaveRequestDto bookRequestDto) {
        booksService.saveBooks(bookRequestDto);
    }

    /**
     * collectInfo 저장
     */
    @PostMapping("/collectinfo/save")
    @ResponseBody
    public Long collectInfoSave(@RequestBody BooksDto.CollectInfoListResponseDto collectInfoListResponseDto) {
        Books book = booksService.findBooks(collectInfoListResponseDto.getBook());
        Library collectLocation = booksService.findLibrary(collectInfoListResponseDto.getCollectLocation());

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
    public Map<String, Object> search(@RequestParam(value = "keyword", required = true) String keyword) {

        Map<String, Object> map = new HashMap<>();

        map.put("bookList", booksService.searchBooks(keyword));

        return map;
    }

    /**
     * 상세정보조회
     */
    @GetMapping("/book/detail")
    @ResponseBody
    public Map<String, Object> bookDetail(@RequestParam(value = "title", required = true) String title) {
        Map<String, Object> map = new HashMap<>();

        map.put("bookDetail", booksService.searchBooksDetail(title));

        return map;
    }

    /**
     * 대출 기능
     */
    @PutMapping("/book/loan")
    @ResponseBody
    public void loan(@RequestBody HashMap<String, Long> param) {

        Long seq = param.get("seq");

        Long user_id = param.get("user_id");

        User user = userService.findUser(user_id);

        BooksDto.CollectInfoUpdateRequestDto requestDto = new BooksDto.CollectInfoUpdateRequestDto();
        Assert.notNull(user , "user must not be NULL");

        requestDto.setState(0);
        requestDto.setLoanDate(LocalDate.now());//대출날짜 // 컴퓨터의 현재 날짜 정보 2018-07-26
        requestDto.setReturnDate(LocalDate.now().plusDays(14)); // 반납일 2주 뒤
        requestDto.setReserveState(0);
        requestDto.setExtensionCount(0);//연장횟수
        requestDto.setUser(user); //빌린 사람

        booksService.updateCollectInfo(seq, requestDto);

        booksService.possibleReserve(seq, "loan");

    }

    /**
     * 반납 기능
     */
    @PutMapping("/book/return")
    @ResponseBody
    public void returnBook(@RequestBody HashMap<String, Long> param) {

        Long seq = param.get("seq");
        CollectInfo collectInfo = booksService.findCollectInfo(seq);

        Long user_id = param.get("user_id");
        User user = userService.findUser(user_id);

        BooksDto.CollectInfoUpdateRequestDto requestDto = new BooksDto.CollectInfoUpdateRequestDto();

        requestDto.setState(1);
        requestDto.setLoanDate(null);//대출날짜 // 컴퓨터의 현재 날짜 정보 2018-07-26
        requestDto.setReturnDate(null); // 반납일 2주 뒤
        requestDto.setExtensionCount(null);//연장횟수
        requestDto.setUser(null); //빌린 사람
        requestDto.setReserveState(null);
        booksService.updateCollectInfo(seq, requestDto);

        booksService.possibleReserve(seq, "return");

    }


    /**
     * 대출현황 리스트 조회
     */
    @GetMapping("/book/loan/status")
    @ResponseBody
    public Map<String, Object> loan_status(@RequestParam(value = "user_id", required = true) Long user_id) {

        User user = userService.findUser(user_id);

        Map<String, Object> map = new HashMap<>();

        map.put("loanStatus", booksService.loanStatus(user));
        return map;
    }

    /**
     * 카테고리(collectLocation) 리스트 조회
     */
    //TODO
    // 데이터 추가 ( 현재 1개 )
    @GetMapping("/book/category")
    @ResponseBody
    public Map<String, Object> categoryList() {
        List<Library> categoryList = booksService.findCategoryList();

        Map<String, Object> map = new HashMap<>();

        map.put("categoryList", categoryList.stream().collect(Collectors.toMap(Library::getId, Library::getLibrary_name)));

        return map;
    }

    /**
     * 예약 기능 (예약 버튼을 눌렀을 경우)
     */
    @PutMapping("/book/reserve")
    @ResponseBody
    public void reserveBook(@RequestBody HashMap<String, Long> param) {
        Long seq = param.get("seq");
        CollectInfo collectInfo = booksService.findCollectInfo(seq);

        Assert.notNull(collectInfo, "collectinto is not null!!");

        Long user_id = param.get("user_id");
        User user = userService.findUser(user_id);

        System.out.println("user : " + user);

        BooksDto.ReserveSaveRequestDto reserveRequestDto = new BooksDto.ReserveSaveRequestDto();

        reserveRequestDto.setUser(user);
        reserveRequestDto.setCollectInfo(collectInfo);
        reserveRequestDto.setReserveDate(LocalDate.now()); //예약일
        reserveRequestDto.setArrivalNoticeDate(null); //도착통보일
        reserveRequestDto.setLoanWatingDate(null);  //대출대기일
        reserveRequestDto.setRanking(null);
        reserveRequestDto.setState(1); // 뭐가 있는지는 잘 모르겠음 (일단 0: 관리자 취소, 1: 완료)
        reserveRequestDto.setCancel(null);

        booksService.saveReserve(reserveRequestDto);

        BooksDto.CollectInfoUpdateRequestDto requestDto2 = new BooksDto.CollectInfoUpdateRequestDto();
        BeanUtils.copyProperties(collectInfo, requestDto2);

        Assert.notNull(collectInfo, "source is not null!!!");
        Assert.notNull(requestDto2, "target is not null!!");
        requestDto2.setReserveState(0); //예약 불가능(0)으로 바꿔야 함
        booksService.updateCollectInfo(seq, requestDto2);
    }

    /**
     * 예약 도서 도착했을 경우 업데이트
     */
    @PutMapping("/book/reserve/arrive")
    @ResponseBody
    public void arriveReserve(@RequestBody HashMap<String, Long> param) {
        Long seq = param.get("seq");
        Reserve reserve = booksService.findReserve(seq);

        BooksDto.ReserveUpdateRequestDto reserveRequestDto = new BooksDto.ReserveUpdateRequestDto();
        BeanUtils.copyProperties(reserve, reserveRequestDto);

        reserveRequestDto.setArrivalNoticeDate(LocalDate.now()); //도착통보일
        reserveRequestDto.setLoanWatingDate(LocalDate.now().plusDays(2));  //대출대기일

        booksService.updateReserve(seq, reserveRequestDto);
    }



    /**
     * 예약 현황 조회
     */
    //TODO
    // parameter 방식
    @GetMapping("/book/reserve/state")
    @ResponseBody
    public List<Reserve> ReserveAllList(@RequestBody HashMap<String, Long> param){
        Long user_id = param.get("user_id");

        return booksService.searchReserveAllDesc(user_id);
    }

    //예약도서를 대출했을 경우 따져줘야 함




}


