package com.eunyeong.book.springboot.service.books;

import com.eunyeong.book.springboot.domain.books.*;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.web.dto.BooksDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BooksService {
    private final BooksRepository booksRepository;

    private final CollectInfoRepository collectInfoRepository;

    private final LibraryRepository libraryRepository;

    private final CategoryRepository categoryRepository;

    private final ReserveRepository reserveRepository;

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
        return booksRepository.findBookByid(book_id);
    }


    @Transactional
    public CollectInfo findCollectInfo(Long seq){return collectInfoRepository.findCollectInfoBySeq(seq);}


    @Transactional
    public Library findLibrary(Long id) {
        return libraryRepository.findLibraryInfoById(id);
    }

    @Transactional
    public List<Library> findCategoryList() {
        return libraryRepository.findAll();
    }

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
    public void updateCollectInfo(Long seq, BooksDto.CollectInfoUpdateRequestDto requestDto) {
        CollectInfo collectInfo = collectInfoRepository.findCollectInfoBySeq(seq);
        //.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. seq=" + seq));
        collectInfo.update(requestDto.getState(), requestDto.getLoanDate(), requestDto.getReturnDate(), requestDto.getReserveState(), requestDto.getExtensionCount(), requestDto.getUser());
    }

    /**
     * 대출현황 리스트 조회
     */
    @Transactional
    public List<BooksDto.CollectInfoListResponseDto> loanStatus(User user) {
        return collectInfoRepository.loanStatus(user).stream()
                .map(BooksDto.CollectInfoListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<CollectInfo> findCollectInfoByBookid(Long book_id) {
        return collectInfoRepository.findCollectInfoByBookid(book_id);
    }

    /**
     * 해당 책의 카테고리별 collectInfo 가져오기  !!!!!!!!!!!!!!!!!!!Dto 수정하고 이것도 수정
     */
    @Transactional
    public List<CollectInfo> findByCategoryId(Long bookId, Long categoryId) {
//        Long bookId=param.get("book_id");
//        Long categoryId=param.get("category_id");

        // 해당 BookId에 딸린 CollectInfo 리스트를 가져온다.
        List<CollectInfo> c_list = findCollectInfoByBookid(bookId);
        List<CollectInfo> result = new ArrayList<>();
        // 해당 CollectInfo 리스트에서 categoryId별로 CollectInfo 리스트를 가져온다.
        for (CollectInfo c : c_list) {
            if (c.getCollectLocation().getId() == categoryId) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * 대출 상태에 따른 예약 가능 여부 체크
     */
    @Transactional
    public void possibleReserve(Long seq, String request) {
        // 해당 book의 각 category_id에 속해있는 collectinfo의 state가 다 대출중(0)이면 예약(reserveState)를 예약가능(1)로 세팅, 아니면 예약불가능(0)로 세팅

        CollectInfo collectInfo = findCollectInfo(seq);

        Long book_id = collectInfo.getBook().getId();
        Long category_id = collectInfo.getCollectLocation().getId();

        //reserveState
        List<CollectInfo> c_list;
        c_list = findByCategoryId(book_id, category_id);


        if (request.equals("return")) {
            BooksDto.CollectInfoUpdateRequestDto requestDto = new BooksDto.CollectInfoUpdateRequestDto();
            for (CollectInfo c : c_list) {
                BeanUtils.copyProperties(c, requestDto);
                requestDto.setReserveState(0);
                updateCollectInfo(c.getSeq(), requestDto);
            }
        }
        else {
            for (CollectInfo c : c_list) {// 하나라도 대출 가능(1)이 있으면 reserve_state는 0(예약불가능)
                if (c.getState() == 1) {
                    return;
                }
            }
            if (request.equals("loan")) {
                BooksDto.CollectInfoUpdateRequestDto requestDto = new BooksDto.CollectInfoUpdateRequestDto();
                for (CollectInfo c : c_list) {
                    BeanUtils.copyProperties(c, requestDto);
                    requestDto.setReserveState(1);
                    updateCollectInfo(c.getSeq(), requestDto);
                }
            }
        }
    }

    @Transactional
    public Long saveReserve(BooksDto.ReserveSaveRequestDto requestDto) {
        return reserveRepository.save(requestDto.toEntity()).getSeq();
    }

    @Transactional
    public Reserve findReserve(Long seq) {
        return reserveRepository.findReserveBySeq(seq);
    }

    /**
     * reserve 수정
     */
    @Transactional
    public void updateReserve(Long seq, BooksDto.ReserveUpdateRequestDto requestDto) {
        Reserve reserve = reserveRepository.findReserveBySeq(seq);
        //.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. seq=" + seq));
        reserve.update(requestDto.getArrivalNoticeDate(),requestDto.getLoanWatingDate(), requestDto.getRanking(), requestDto.getState(), requestDto.getCancel());
    }

    // reserve 전체 조회
    @Transactional
    public List<Reserve> searchReserveAllDesc(Long user_id)
    {
        return reserveRepository.findReserveByIdOrderByUserIdDesc(user_id);
    }



}
