package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.books.Books;
import com.eunyeong.book.springboot.domain.books.BooksRepository;
import com.eunyeong.book.springboot.domain.books.CollectInfo;
import com.eunyeong.book.springboot.domain.books.CollectInfoRepository;
import com.eunyeong.book.springboot.web.dto.BooksDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BooksApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CollectInfoRepository collectInfoRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                //.apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        booksRepository.deleteAll();
        collectInfoRepository.deleteAll();
    }

    @Test
    //@WithMockUser(roles = "USER")
    public void Books_등록된다() throws Exception {
        String title = "test";
        String thumbnail = "test";
        String type = "test";
        String author = "test";
        String sign = "test";
        String publish = "test";
        String shape = "test";

        BooksSaveRequestDto requestBookDto = BooksSaveRequestDto.builder()
                .title(title)
                .thumbnail(thumbnail)
                .author(author)
                .sign(sign)
                .publish(publish)
                .shape(shape)
                .build();

        String url = "http://localhost:" + port + "/books/save";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestBookDto)))
                .andExpect(status().isOk());

        //then
        List<Books> all = booksRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getThumbnail()).isEqualTo(thumbnail);

    }

    @Test
    //@WithMockUser(roles = "USER")
    public void CollectInfo_등록된다() throws Exception {
        Books book = new Books("test", "test", "test", "test", "test", "test", "test");
        booksRepository.save(book);

        String collectLocation = "과학도서관/Sci-Info(1층서고)";
        String callNumber = "006.78.2019z2";
        String enrollNum = "121251508";
        Integer state = 0; // 대출중:0 대출가능:1
        LocalDate returnDate = LocalDate.of(2021, 8, 17);
        Integer reserveState = 1; // 예약 불가능:0 예약 가능:1

        BooksDto.CollectInfoSaveRequestDto requestCollectInfoDto = BooksDto.CollectInfoSaveRequestDto.builder()
                .book(book)
                .collectLocation(collectLocation)
                .callNumber(callNumber)
                .enrollNum(enrollNum)
                .state(state)
                .returnDate(returnDate)
                .reserveState(reserveState)
                .build();

        String url = "http://localhost:" + port + "/collectinfo/save";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestCollectInfoDto)))
                .andExpect(status().isOk());

        //then
        List<CollectInfo> all = collectInfoRepository.findAll();
        assertThat(all.get(0).getBook()).isEqualTo(book);
        assertThat(all.get(0).getCollectLocation()).isEqualTo(collectLocation);
    }

    //상세 조회
    @Test
    //@WithMockUser(roles = "USER")
    public void Book_조회() throws Exception {
        // Test를 위해서 Book에 해당하는 db 2개 저장
        String title1 = "test";
        String type1 = "test";
        String author1 = "test";
        String sign1 = "test";
        String publish1 = "test";
        String shape1 = "test";

        String title2 = "test2";
        String type2 = "test2";
        String author2 = "test2";
        String sign2 = "test2";
        String publish2 = "test2";
        String shape2 = "test2";

        // 기존 코드 활용하려고 했으나, 단순 조회 기능만 수행하면 되기 떄문에 두 가지 간단하게 넣어서 조회되는지만 테스트

//        Books books = new Books(title1, type1, author1, sign1, publish1, shape1);
//        booksRepository.save(books);
//
//        Books books2 = new Books(title2, type2, author2, sign2, publish2, shape2);
//        booksRepository.save(books2);

        Books savedBooks = booksRepository.save(Books.builder()
                .title(title1)
                .type(type1)
                .author(author1)
                .sign(sign1)
                .publish(publish1)
                .shape(shape1)
                .build());

        savedBooks = booksRepository.save(Books.builder()
                .title(title2)
                .type(type2)
                .author(author2)
                .sign(sign2)
                .publish(publish2)
                .shape(shape2)
                .build());


        //then
        List<Books> all = booksRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    // 조회
    @Test
   // @WithMockUser(roles = "USER")
    public void Book_조회2() throws Exception{
        // Test를 위해서 Book에 해당하는 db 2개 저장
        String title1 = "test";
        String type1 = "test";
        String author1 = "test";
        String sign1 = "test";
        String publish1 = "test";
        String shape1 = "test";

        String title2 = "test2";
        String type2 = "test2";
        String author2 = "test2";
        String sign2 = "test2";
        String publish2 = "test2";
        String shape2 = "test2";

        Books savedBooks = booksRepository.save(Books.builder()
                .title(title1)
                .type(type1)
                .author(author1)
                .sign(sign1)
                .publish(publish1)
                .shape(shape1)
                .build());

        BooksSaveRequestDto requestDto = BooksSaveRequestDto.builder()
                .title(title2)
                .type(type2)
                .author(author2)
                .sign(sign2)
                .publish(publish2)
                .shape(shape2)
                .build();


        String url = "http://localhost:" +port+ "/books/save";

        ObjectMapper objectMapper = new ObjectMapper();
        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Books> all = booksRepository.findAllBookDesc();
        //assertThat(all.get(0).getTitle()).isEqualTo(title1);
        assertThat(all.size()).isEqualTo(2);
    }
}
