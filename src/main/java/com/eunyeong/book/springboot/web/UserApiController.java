package com.eunyeong.book.springboot.web;


import com.eunyeong.book.springboot.domain.ErrorMessage.errorMessage;
import com.eunyeong.book.springboot.domain.user.User;
import com.eunyeong.book.springboot.service.user.UserService;
import com.eunyeong.book.springboot.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
            Assert.notNull(userDto, "userDto must nxxot be NULL");
            Assert.notNull(user, "user must not be NULL");
            // END CHECK POINT

            UserDto.UserUpdateRequestDto userUpdateRequestDto = new UserDto.UserUpdateRequestDto();

            BeanUtils.copyProperties(user, userUpdateRequestDto);

            userUpdateRequestDto.setAccessToken(userDto.getAccessToken());

            userService.update(userDto.getUserInfo().getId() , userUpdateRequestDto);

            errorMessage errormessage = errorMessage.builder()
                    .status("201").user(user).build();

            // 기존 계정이 있다면 201 ok 반환
            return new ResponseEntity<>(errormessage, HttpStatus.CREATED);

        }

//        else // 유저 정보가 담겨있지 않다면, 유저 정보 db에 저장
//        {
//            userService.save(userDto);
//            UserDto.UserdDto user = new UserDto.UserdDto(userDto.getAccessToken(), userDto.getUserInfo());
//
//            return user;
//        }

        else // 유저 정보가 담겨있지 않다면, 유저 정보 db에 저장
        {
            userService.save(userDto);

            User user = userService.findUserInfo(userDto.getUserInfo().getId());

//            UserDto.UserdDto user = new UserDto.UserdDto(userDto.getAccessToken(), userDto.getUserInfo());

            errorMessage errormessage = errorMessage.builder()
                    .status("200").user(user).build();

            return new ResponseEntity<>(errormessage, HttpStatus.OK);

//            return new ResponseEntity<User> (user, HttpStatus.OK);
        }

//        UserDto.UserdDto user = new UserDto.UserdDto(userDto.getAccessToken(), userDto.getUserInfo());
//        return user;
    }

    /*
    전체 유저 조회
     */

    @GetMapping("/user")
    @ResponseBody
    public List<UserDto.UserListRequestDto> searchAllUser(){
        return userService.searchAllDescUser();
    }


    /*
    특정 유저 조회 (email로 조회)
     */
    @GetMapping("/user/search")
    @ResponseBody
    public List<UserDto.UserListRequestDto> searchUser(@RequestBody HashMap<String, Object> param){
        String email = param.get("email").toString();

        return userService.searchUserByEmail(email);
    }


}
