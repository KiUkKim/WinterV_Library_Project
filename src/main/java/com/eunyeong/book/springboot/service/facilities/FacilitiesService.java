package com.eunyeong.book.springboot.service.facilities;

import com.eunyeong.book.springboot.domain.books.Library;
import com.eunyeong.book.springboot.domain.books.LibraryRepository;
import com.eunyeong.book.springboot.domain.facility.*;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.domain.user.UserRepository;
import com.eunyeong.book.springboot.web.dto.FacilitiesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableScheduling // 스케줄링 기능을 enable 함
@Slf4j
@RequiredArgsConstructor
@Service
public class FacilitiesService {
    private final LibraryRepository libraryRepository;
    private final SeatsRepository seatsRepository;
    private final UserRepository userRepository;
    private final FacilityReserveRepository facilityReserveRepository;
    private final FacilityInfoRepository facilityInfoRepository;

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

        log.info("id" + libraryId);
        log.info("l : " + l);

        return l.getReadingRoomList();
    }

    /**
     * 좌석 배정
     */
    @Transactional
    public Long seatAssignment(Long pk, FacilitiesDto.SeatsUpdateRequestDto requestDto) {
        Seats seats = seatsRepository.findById(pk).orElseThrow(() -> new IllegalArgumentException("해당 좌석이 없습니다. pk=" + pk));
        seats.update(requestDto.getStartT(), requestDto.getUseT(), requestDto.getCheckT(), requestDto.getAssignmentT(), requestDto.getUser());


        return pk;
    }

    /**
     * 좌석 배정 기록 조회
     */
    @Transactional
    public Seats findSeatRecordByUserId(Long user_id) {
        User user = userRepository.findUserById(user_id);
        return user.getSeat();
    }

    /**
     * 시설 예약
     */
    @Transactional
    public void saveFacilityReserve(FacilitiesDto.FacilityReserveSaveRequestDto requestDto) {
        facilityReserveRepository.save(requestDto.toEntity());
    }

    /**
     * 시설 예약 기록 조회
     */
    @Transactional
    public List<FacilityReserve> facilityReserveAllDesc(Long user_id)
    {
        return facilityReserveRepository.facilityReserveById(user_id);
    }

    @Transactional
    public FacilityInfo findFacilityInfo(Long id) {
        return facilityInfoRepository.findFacilityInfoById(id);
    }
}