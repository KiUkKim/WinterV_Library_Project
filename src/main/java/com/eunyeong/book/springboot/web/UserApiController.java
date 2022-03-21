package com.eunyeong.book.springboot.web;

import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;


    @PostMapping("/auth/google/user")
    @ResponseBody
    public Object userInformation(@RequestBody UserDto.UserdDto userDto){

        boolean check = userService.checkUserDuplicate(userDto.getUserInfo().getEmail()); // userInfo 담겨있는지 체크

        if(check) // 유저 정보가 담겨있다면, accessToken만 update
        {
            User user = userService.findUserInfo(userDto.getUserInfo().getId());

            // Bean NULL CHECK POINT
            Assert.notNull(userDto, "userDto must not be NULL");
            Assert.notNull(user, "user must not be NULL");
            // END CHECK POINT

            UserDto.UserUpdateRequestDto userUpdateRequestDto = new UserDto.UserUpdateRequestDto();

            BeanUtils.copyProperties(user, userUpdateRequestDto);

            userUpdateRequestDto.setAccessToken(userDto.getAccessToken());

            userService.update(userDto.getUserInfo().getId() , userUpdateRequestDto);
        }
        else // 유저 정보가 담겨있지 않다면, 유저 정보 db에 저장
        {
            userService.save(userDto);
        }
        

        UserDto.UserdDto user = new UserDto.UserdDto(userDto.getAccessToken(), userDto.getUserInfo());
        return user;
    }
}
