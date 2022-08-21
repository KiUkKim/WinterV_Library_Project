package com.eunyeong.book.springboot.domain.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacilityReserveRepository extends JpaRepository<FacilityReserve, Long> {

    @Query("SELECT f FROM FacilityReserve f WHERE f.user.seq=:user_id")
    List<FacilityReserve> facilityReserveById(Long user_id);
}
