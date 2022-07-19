package com.eunyeong.book.springboot.domain.books;

import com.eunyeong.book.springboot.domain.facility.Info;
import com.eunyeong.book.springboot.domain.facility.ReadingRoom;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name="libraries")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LIBRARY_ID")
    private Long id;

    @Column(unique = true)
    private String library_name;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library", orphanRemoval = true)
    private List<Info> facilityInfos = new ArrayList<>();

//    @Column
//    private String operatingHours;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectLocation", orphanRemoval = true)
    private List<CollectInfo> collectInfoList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library", orphanRemoval = true)
    private List<ReadingRoom> readingRoomList = new ArrayList<>();

    @Builder
    public Library(String library_name, List<CollectInfo> collectInfoList, List<ReadingRoom> readingRoomList){
        this.library_name=library_name;
        this.collectInfoList=collectInfoList;
        this.readingRoomList = readingRoomList;
    }
}
