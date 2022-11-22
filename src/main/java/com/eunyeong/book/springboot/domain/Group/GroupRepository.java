package com.eunyeong.book.springboot.domain.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT gc FROM GroupCommunity gc")
    List<Group> findAllGroupCommunity();

        /*
    Group Community view 증가 ( 조회수 관련 )
     */
    @Modifying
    @Query("update GroupCommunity c set c.view_count = c.view_count + 1 WHERE c.group_Id = :group_Id")
    void updateGroupCommunityView(Long group_Id);

        /*
    Group Commuity Table에 등록된 User seq찾기
     */
    @Query("SELECT g.user.seq FROM GroupCommunity g WHERE g.group_Id = :group_Id")
    Long findGroupCommunityUser(Long group_Id);

    // 해당 그룹 List로 출력하기 위함
    @Query("SELECT g, g.user.userInfo.name FROM GroupCommunity g WHERE g.user.seq = :user_id and g.group_Id = :group_Id")
    List<Group> findGroupCommunityDetail(Long user_id, Long group_Id);

}
