package com.eunyeong.book.springboot.domain.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacilityInfoRepository extends JpaRepository<FacilityInfo, Long> {

    @Query("SELECT f FROM FacilityInfo f WHERE f.id=:id")
    FacilityInfo findFacilityInfoById(Long id);
}
