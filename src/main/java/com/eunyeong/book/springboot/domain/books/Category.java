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
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String collectLocation;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectLocation", orphanRemoval = true)
    private List<CollectInfo> collectInfoList = new ArrayList<>();

    @Builder
    public Category(String collectLocation, List<CollectInfo> collectInfoList){
        this.collectLocation=collectLocation;
        this.collectInfoList=collectInfoList;
    }

}
