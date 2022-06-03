package com.eunyeong.book.springboot.domain.books;

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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String library_name;

//    @Column
//    private String operatingHours;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectLocation", orphanRemoval = true)
    private List<CollectInfo> collectInfoList = new ArrayList<>();

//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "readingRooms", orphanRemoval = true)
//    private List<ReadingRoom> readingRoomList = new ArrayList<ReadingRoom>();

    @Builder
    public Category(String library_name, List<CollectInfo> collectInfoList){
        this.library_name=library_name;
        this.collectInfoList=collectInfoList;
//        this.readingRoomList = readingRoomList;
    }
}
