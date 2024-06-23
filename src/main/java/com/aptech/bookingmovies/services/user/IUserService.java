package com.aptech.bookingmovies.services.user;

import com.aptech.bookingmovies.dtos.UserDTO;
import com.aptech.bookingmovies.dtos.UserLoginDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.ConfirmEmail;
import com.aptech.bookingmovies.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(UserLoginDTO userLoginDTO) throws Exception;
    String changePassword(String phoneNumber,String newPassword,String confirmPassword) throws Exception;
    boolean forgotPassword(String email,String phoneNumber)throws Exception;
    User findByPhoneNumber(String phoneNumber) throws  Exception;
    User findById(int id) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User getUserDetailsFromRefreshToken(String token) throws Exception;

}
