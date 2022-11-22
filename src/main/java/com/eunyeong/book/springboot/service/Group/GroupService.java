package com.eunyeong.book.springboot.service.Group;

import com.eunyeong.book.springboot.domain.Group.GroupRepository;
import com.eunyeong.book.springboot.web.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@RequiredArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public Long GroupCommunitySaveService(GroupDto.GroupCommunitySaveDto groupCommunitySaveDto)
    {
        return groupRepository.save(groupCommunitySaveDto.toEntity()).getGroup_Id();
    }

    @Transactional
    public List<GroupDto.groupCommunityListResponseDto> searchAllDescGroupCommunity()
    {
        return groupRepository.findAllGroupCommunity().stream()
                .map(GroupDto.groupCommunityListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void GroupViewCount(Long group_Id)
    {
        groupRepository.updateGroupCommunityView(group_Id);
    }

    @Transactional
    public Long FindUserByGroup(Long group_Id)
    {
        return groupRepository.findGroupCommunityUser(group_Id);
    }


    // 해당 그룹 map 형식으로 뿌려주는 부분
    @Transactional
    public List<GroupDto.groupCommunityListResponseDto> searchUserByGroupCommunity(Long user_id, Long id){
        return groupRepository.findGroupCommunityDetail(user_id, id).stream()
                .map(GroupDto.groupCommunityListResponseDto::new)
                .collect(Collectors.toList());
    }
}
