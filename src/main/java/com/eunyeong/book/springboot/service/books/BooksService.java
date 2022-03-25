package com.eunyeong.book.springboot.service.books;

import com.eunyeong.book.springboot.domain.books.*;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.web.dto.BooksDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BooksService {
    private final BooksRepository booksRepository;

    private final CollectInfoRepository collectInfoRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long saveBooks(BooksDto.BooksSaveRequestDto requestDto) {
        return booksRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long saveCollectInfo(BooksDto.CollectInfoSaveRequestDto collectInfoSaveRequestDto) { return collectInfoRepository.save(collectInfoSaveRequestDto.toEntity()).getSeq();}


    /**
     * 도서 검색
     */
    @Transactional
    public List<BooksDto.BooksListResponseDto> searchBooks(String keyword) {
        return booksRepository.findByTitleContaining(keyword).stream()
                .map(BooksDto.BooksListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Books findBooks(Long book_id) {
        return collectInfoRepository.findBookByid(book_id);
    }


    @Transactional
    public CollectInfo findCollectInfo(Long seq){return collectInfoRepository.findCollectInfoBySeq(seq);}


    @Transactional
    public Category findCategory(Long id) {return categoryRepository.findCategoryInfoById(id);}


    @Transactional
    public List<Category> findCategoryList() {return categoryRepository.findAll();}

    @Transactional
    public List<BooksDto.BooksListResponseDto> searchBooksDetail(String title){
        return booksRepository.findBookDetail(title).stream()
                .map(BooksDto.BooksListResponseDto::new)
                .collect(Collectors.toList());
    }


    /**
     * collectInfo 수정
     */
    @Transactional
    public Long update(Long seq, BooksDto.CollectInfoUpdateRequestDto requestDto) {
        CollectInfo collectInfo = collectInfoRepository.findCollectInfoBySeq(seq);
//                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. seq=" + seq));
        collectInfo.update(requestDto.getState(), requestDto.getReturnDate(), requestDto.getReserveState(), requestDto.getExtensionCount(), requestDto.getUser());
        return seq;
    }


    /**
     * 대출현황 리스트 조회
     */
    @Transactional
    public List<BooksDto.CollectInfoListResponseDto> loanStatus(User user){
        return collectInfoRepository.loanStatus(user).stream()
                .map(BooksDto.CollectInfoListResponseDto::new)
                .collect(Collectors.toList());
    }


}
