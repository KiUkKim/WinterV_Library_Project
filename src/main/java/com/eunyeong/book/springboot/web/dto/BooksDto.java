package com.eunyeong.book.springboot.web.dto;

import com.eunyeong.book.springboot.domain.books.Books;
import com.eunyeong.book.springboot.domain.books.Category;
import com.eunyeong.book.springboot.domain.books.CollectInfo;
import com.eunyeong.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

// 중요 : Dto 통합관련 설명 각 클래스에 static 키워드를 사용하는 이유
// static을 사용하든 사용하지 않든 어떤 방식을 사용하여도 문제가 없으나.
// static을 사용하지않았을 경우 외부 전체 클래스의 객체 인스턴스를 생성해주고, 사용해야함
// static을 사용하였을 경우 외부 전체 (BooksDto)의 인스턴스가 생성되지 않아도, 내부의 인스턴스 객체를 생성할 수 있다.
// ex ) static클래스를 선언하였을 경우 BooksDto.원하는클래스 이런식으로 접근가능
// static클래스를 선언하지 않았을 경우, BooksDto booksDto = new BooksDto(); booksDto.원하는클래스 이런식으로 접근해야한다.

public class BooksDto {

    ////////////// Books 관련 Dto /////////////////

    // Book column 정보 받아오는 Dto
    @Getter
    @NoArgsConstructor
    public static class BooksListResponseDto{
        private Long id;
        private String title;
        private String thumbnail;
        private String author;
        private String type;
        private String sign;
        private String publish;
        private String shape;
        private List<CollectInfo> collectInfoList;

        public BooksListResponseDto(Books entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.thumbnail = entity.getThumbnail();
            this.author = entity.getAuthor();
            this.type = entity.getType();
            this.sign = entity.getSign();
            this.publish = entity.getPublish();
            this.shape = entity.getShape();
            this.collectInfoList = entity.getCollectInfoListForBooks();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BooksSaveRequestDto{
        private Long id;
        private String title;
        private String thumbnail;
        private String type;
        private String author;
        private String sign;
        private String publish;
        private String shape;

        @Builder
        public BooksSaveRequestDto(String title, String thumbnail, String type, String author, String sign, String publish, String shape) {
            this.title = title;
            this.thumbnail = thumbnail;
            this.type = type;
            this.author = author;
            this.sign = sign;
            this.publish = publish;
            this.shape = shape;
        }

        @NotNull
        public Books toEntity() {
            return Books.builder()
                    .title(title)
                    .thumbnail(thumbnail)
                    .type(type)
                    .author(author)
                    .sign(sign)
                    .publish(publish)
                    .shape(shape)
                    .build();
        }
    }



    ////////////////// collectInfo 관련 Dto ///////////////
    @Getter
    @NoArgsConstructor
    public static class CollectInfoResponseDto{
        private Long book;
        private String collectLocation;
        private String callNumber;
        private String enrollNum;
        private Integer state;
        private LocalDate returnDate;
        private Integer reserveState;
    }

    @Getter
    @NoArgsConstructor
    public static class CollectInfoListResponseDto {
        private Long book;
        private Long collectLocation;
        private String callNumber;
        private String enrollNum;
        private Integer state;
        private LocalDate returnDate;
        private Integer reserveState;
        private LocalDate loanDate;
        private Integer extensionCount;
        private Long user;
        private String bookTitle;

        public CollectInfoListResponseDto(CollectInfo entity) {
            this.book = entity.getBook().getId();
            this.collectLocation = entity.getCollectLocation().getId();
            this.callNumber = entity.getCallNumber();
            this.enrollNum = entity.getEnrollNum();
            this.state = entity.getState();
            this.returnDate = entity.getReturnDate();
            this.reserveState = entity.getReserveState();
            this.loanDate= entity.getLoanDate();
            this.extensionCount= entity.getExtensionCount();
            this.user= entity.getUser().getSeq();
            this.bookTitle = entity.getBook().getTitle();
        }
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class CollectInfoSaveRequestDto {
        private LocalDate loanDate;
        private User user;
        private Integer extensionCount;
        private Books book;
        private Category collectLocation;
        private String callNumber;
        private String enrollNum;
        private Integer state;
        private LocalDate returnDate;
        private Integer reserveState;


        @Builder
        public CollectInfoSaveRequestDto(Books book, Category collectLocation, String callNumber, String enrollNum, Integer state, LocalDate returnDate, LocalDate loanDate, Integer extensionCount, Integer reserveState, User user) {
            this.book = book;
            this.collectLocation = collectLocation;
            this.callNumber = callNumber;
            this.enrollNum = enrollNum;
            this.state = state;
            this.returnDate = returnDate;
            this.reserveState = reserveState;
            this.loanDate = loanDate;
            this.extensionCount = extensionCount;
            this.user = user;
        }

        @NotNull
        public CollectInfo toEntity() {
            return CollectInfo.builder()
                    .book(book)
                    .collectLocation(collectLocation)
                    .callNumber(callNumber)
                    .enrollNum(enrollNum)
                    .state(state)
                    .returnDate(returnDate)
                    .reserveState(reserveState)
                    .loanDate(loanDate)
                    .extensionCount(extensionCount)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CollectInfoUpdateRequestDto {

        private LocalDate loanDate;
        private User user;
        //private String collectLocation;
        private Integer state;
        private LocalDate returnDate;
        private Integer reserveState;
        private Integer extensionCount;

        @Builder
        public CollectInfoUpdateRequestDto(Integer state, LocalDate returnDate, LocalDate loanDate, Integer reserveState, Integer extensionCount, User user){
            //this.collectLocation=collectLocation;
            this.state=state;
            this.returnDate=returnDate;
            this.reserveState=reserveState;
            this.extensionCount=extensionCount;
            this.loanDate=loanDate;
            this.user=user;
        }
    }



}
