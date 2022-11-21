package com.eunyeong.book.springboot.service.subjectCommunity;

import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunity;
import com.eunyeong.book.springboot.domain.SubjectCommunity.SubjectCommunityRepository;
import com.eunyeong.book.springboot.domain.user.Community;
import com.eunyeong.book.springboot.domain.user.NoticeRepository;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.web.dto.SubjectDto;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Slf4j
@RequiredArgsConstructor
@Service
public class SubjectCommunityService {

    private final SubjectCommunityRepository subjectCommunityRepository;

    private final NoticeRepository noticeRepository;


    @Transactional
    public Long saveSubjectCommunity(SubjectDto.SubjectCommunitySaveDto subjectCommunitySaveDto)
    {
        return subjectCommunityRepository.save(subjectCommunitySaveDto.toEntity()).getId();
    }

    /*
    게시글 업데이트 관련 Service
    front 에 현재 접속되어 있는 유저와, Community Table에 저장되어 있는 유저를 비교
     */

    @Transactional
    public Long SubjectCommunityUpdate(Long id, SubjectDto.SubjectCommunitySaveDto.SubjectCommunityUpdateDto communityUpdateDto)
    {
        // 해당 ID에 해당하는 게시물 찾기
        SubjectCommunity community = subjectCommunityRepository.findSubjectCommunityById(id);

        Long seq = subjectCommunityRepository.userIDForSubCommunity(communityUpdateDto.getEmail());

        // Null Check Point
//        Assert.notNull(seq, "Seq must not be null");

        int check = subjectCommunityRepository.TureOrFalseInSubCommunity(seq, id);

        if(check < 1)
        {
            throw new RuntimeException(communityUpdateDto.getTitle() + " 게시물의 작성자가 아닙니다.");
        }

        community.update(communityUpdateDto.getTitle(), communityUpdateDto.getContent());
        return id;
    }

    // 전달받은 이메일을 통해서 db에 해당 유저의 id 반환
    @Transactional
    public Long SearchUser(String email)
    {
        Long seq = subjectCommunityRepository.userIDForSubCommunity(email);

        return seq;
    }


    // 해당 seq로 User객체 찾아오기
    @Transactional
    public User findNotice(Long seq)
    {
        User user = noticeRepository.findNoticeUSer(seq);

        return user;
    }

        /*
     Delete 관련
      */
    @Transactional
    public void deleteCommunity(Long id, SubjectDto.SubCommunityDeleteDto subCommunityDeleteDto)
    {
        Long seq = subjectCommunityRepository.userIDForSubCommunity(subCommunityDeleteDto.getEmail());

        int check = subjectCommunityRepository.TureOrFalseInSubCommunity(seq, id);

        if(check < 1)
        {
            throw new RuntimeException("게시물의 작성자가 아닙니다.");
        }

        subjectCommunityRepository.deleteSubCommunityById(id);
    }

        /*
    community 전체 조회 관련 Transactional
     */
        // Community 전체 출력
    @Transactional
    public List<SubjectDto.SubCommunityListResponseDto> searchAllDescSubCommunity()
    {
        return subjectCommunityRepository.findSubCommunityByIdOrderDesc().stream()
                .map(SubjectDto.SubCommunityListResponseDto::new)
                .collect(Collectors.toList());
    }
}
