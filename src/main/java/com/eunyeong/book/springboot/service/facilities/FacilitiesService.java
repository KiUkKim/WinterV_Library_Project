package com.eunyeong.book.springboot.service.facilities;

import com.eunyeong.book.springboot.domain.books.Library;
import com.eunyeong.book.springboot.domain.books.LibraryRepository;
import com.eunyeong.book.springboot.domain.facility.ReadingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FacilitiesService {
    private final LibraryRepository libraryRepository;

    @Transactional
    public Library findLibrary(Long id) {
        return libraryRepository.findLibraryInfoById(id);
    }

    /**
     * 해당 도서관별로 열람실 정보 가져오기
     */
    @Transactional
    public List<ReadingRoom> findReadingRoomByLibraryId(Long libraryId) {
        // 해당 libraryId의 도서관을 가져온다
        Library l = findLibrary(libraryId);

        return l.getReadingRoomList();
    }


}
