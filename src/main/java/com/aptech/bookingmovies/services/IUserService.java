package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.UserDTO;
import com.aptech.bookingmovies.dtos.UserLoginDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.ConfirmEmail;
import com.aptech.bookingmovies.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(UserLoginDTO userLoginDTO) throws Exception;

}
