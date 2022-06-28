package com.eunyeong.book.springboot.service.Community;

public class CommunityService2 {
}
//package com.eunyeong.book.springboot.service.Community;
//
//import com.eunyeong.book.springboot.domain.user.Community;
//import com.eunyeong.book.springboot.domain.user.CommunityRepository;
//import com.eunyeong.book.springboot.web.dto.UserDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@Service
//public class CommunityService{
//
//    @Autowired
//    private final CommunityRepository communityRepository;
//
//    ////////////////////// Community 관련 //////////////////////////////
//    /*
//    게시글 저장 관련 Service
//     */
//
//    @Transactional
//    public Long saveCommunity(UserDto.CommunitySaveDto communitySaveDto)
//    {
//        return communityRepository.save(communitySaveDto.toEntity()).getId();
//    }
//
//
//    /*
//    게시글 업데이트 관련 Service
//    front 에 현재 접속되어 있는 유저와, Community Table에 저장되어 있는 유저를 비교
//     */
//
//    @Transactional
//    public Long communityUpdate(Long id, UserDto.CommunityUpdateDto communityUpdateDto)
//    {
//        // 해당 ID에 해당하는 게시물 찾기
//        Community community = communityRepository.findCommunityById(id);
//
//        Long seq = communityRepository.userIDoFCommunity(communityUpdateDto.getEmail());
//
//        int check = communityRepository.TureOrFalseInCommunity(seq, id);
//
//        if(check < 1)
//        {
//            throw new RuntimeException("게시물의 작성자가 아닙니다.");
//        }
//
//        community.update(communityUpdateDto.getTitle(), communityUpdateDto.getContent());
//        return id;
//    }
//
//
//    /*
//    Delete 관련
//     */
//    @Transactional
//    public void deleteCommunity(Long id, UserDto.CommunityDeleteDto communityDeleteDto)
//    {
//        Long seq = communityRepository.userIDoFCommunity(communityDeleteDto.getEmail());
//
//        int check = communityRepository.TureOrFalseInCommunity(seq, id);
//
//        if(check < 1)
//        {
//            throw new RuntimeException("게시물의 작성자가 아닙니다.");
//        }
//
//        communityRepository.deleteCommunityById(id);
//    }
//
//
//}
