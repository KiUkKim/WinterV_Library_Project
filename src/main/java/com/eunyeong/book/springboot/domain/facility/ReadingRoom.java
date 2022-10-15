package com.eunyeong.book.springboot.domain.facility;

import com.eunyeong.book.springboot.domain.books.Library;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="readingRooms")
public class ReadingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="READINGROOM_ID")
    private Long id;

    @JsonBackReference
    @ManyToOne(targetEntity= Library.class, fetch=FetchType.EAGER)
    @JoinColumn(name="library")
    private Library library;

    @Column
    private String readingRoom_name;

    @Column
    private Integer totalNum;

    @Column
    private Integer useNum;

    @Column
    private Integer availableNum;

    @Column
    private Integer utilizationRate;

//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "readingRoom", orphanRemoval = true)
//    private List<Seats> seatsList = new ArrayList<>();

    @Builder
    public ReadingRoom(Library library, String readingRoom_name, Integer totalNum, Integer useNum, Integer availableNum, Integer utilizationRate, List<Seats> seatsList){
        this.library=library;
        this.readingRoom_name=readingRoom_name;
        this.totalNum=totalNum;
        this.useNum=useNum;
        this.availableNum=availableNum;
        this.utilizationRate=utilizationRate;
//        this.seatsList = seatsList;
    }

}