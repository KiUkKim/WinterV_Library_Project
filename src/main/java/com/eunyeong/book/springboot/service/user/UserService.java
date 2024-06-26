package com.eunyeong.book.springboot.service.user;

import com.eunyeong.book.springboot.domain.books.Books;
import com.eunyeong.book.springboot.domain.user.Notice;
import com.eunyeong.book.springboot.domain.user.NoticeRepository;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.domain.user.UserRepository;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final NoticeRepository noticeRepository;

    @Transactional
    public Long save(UserDto.UserdDto userDto)

    {
        return userRepository.save(userDto.toEntity2()).getSeq();
    }

    @Transactional
    public Boolean checkUserDuplicate(String email)
    {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User findUserInfo(String id)
    {
        User user = userRepository.findUserById(id);

        return user;
    }

    @Transactional
    public User findUser(Long user_id) {
        return userRepository.findUserByid(user_id);
    }

    @Transactional
    public String update(String id, UserDto.UserUpdateRequestDto userUpdateRequestDto)
    {
        User user = userRepository.findUserById(id);

        user.accessTokenUpdate(userUpdateRequestDto.getAccessToken());

        return id;
    }

    ///////////////////////////// User 정보 출력 부분 //////////////////////////
    // User 전체 출력
    @Transactional
    public List<UserDto.UserListRequestDto> searchAllDescUser()
    {
        return userRepository.findAllByUser().stream()
                .map(UserDto.UserListRequestDto::new)
                .collect(Collectors.toList());
    }


    // User email에 대한 출력
    @Transactional
    public List<UserDto.UserListRequestDto> searchUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email).stream()
                .map(UserDto.UserListRequestDto::new)
                .collect(Collectors.toList());
    }

    //////////////////// NOTICE 관련 Transactional 공간 //////////////////////////

    // 전달받은 이메일을 통해서 db에 해당 유저의 id 반환
    @Transactional
    public Long SearchUser(String email)
    {
        Long seq = noticeRepository.userIdForEmail(email);

        return seq;
    }

    @Transactional
    public User findNotice(Long seq)
    {
        User user2 = noticeRepository.findNoticeUSer(seq);

        return user2;
    }


    // Notice 정보 저장
    @Transactional
    public Long saveNotice(UserDto.NoticeSaveRequestDto noticeSaveRequestDto)
    {
        return noticeRepository.save(noticeSaveRequestDto.toEntity()).getId();
    }

    // Notice 조회수 증가
    @Transactional
    public int updateView(Long id)
    {
        return noticeRepository.updateView(id);
    }

    // Notice에서 필요한 정보 출력 ( User와 연결되어서 해당되는 모든 정보 들고옴 -> 나중에 필요할 수도 있어서 남겨둠. )
    @Transactional
    public List<UserDto.NoticeListResponseDto> searchUserNotice(Long user_id){
        return noticeRepository.findNoticeById(user_id).stream()
                .map(UserDto.NoticeListResponseDto::new)
                .collect(Collectors.toList());
    }


    // Notice에서 외래키 id 찾기
    @Transactional
    public Long findNoticeUserId(Long id)
    {
        return noticeRepository.findNoticeUserId(id);
    }

    // 전체 조회
    @Transactional
    public List<UserDto.NoticeListResponseDto> searchAllDesc()
    {
        return noticeRepository.findNoticeByIdOrderByIdDesc().stream()
                .map(UserDto.NoticeListResponseDto::new)
                .collect(Collectors.toList());
    }


    /////// Update 관련 ///////////

    /*
    front의 현재 접속되어 있는 유저와, Notice table에 저장되어있는 유저를 비교하기 위함 ( Update를 위한 Transactional)
     */

    @Transactional
    public Long update(Long id, UserDto.NoticeUpdateRequestDto noticeUpdateRequestDto)
    {
        Notice notice = noticeRepository.findNotice(id);

        Long seq = noticeRepository.userIdForEmail(noticeUpdateRequestDto.getEmail());

        int check = noticeRepository.TrueOrFalseInNotice(seq, id);

        System.out.println(check);

        if(check < 1)
        {
            throw new RuntimeException("게시물의 작성자가 아닙니다.");
        }

        notice.update(noticeUpdateRequestDto.getTitle(), noticeUpdateRequestDto.getContent());
        return id;
    }


    ///////// Delete 관련 /////////////
    @Transactional
    public void delete(Long id, UserDto.NoticeDeleteRequestDto noticeDeleteRequestDto)
    {
        Long seq = noticeRepository.userIdForEmail(noticeDeleteRequestDto.getEmail());

        int check = noticeRepository.TrueOrFalseInNotice(seq, id);

        System.out.println(check);

        if(check < 1)
        {
            throw new RuntimeException("게시물의 작성자가 아닙니다.");
        }

        noticeRepository.deleteNotice(id);
    }

}
