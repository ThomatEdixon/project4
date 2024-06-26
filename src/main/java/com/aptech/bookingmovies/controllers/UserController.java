package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.UserDTO;
import com.aptech.bookingmovies.dtos.UserForgotPasswordDTO;
import com.aptech.bookingmovies.dtos.UserLoginDTO;
import com.aptech.bookingmovies.models.*;
import com.aptech.bookingmovies.services.confirmemail.ConfirmEmailService;
import com.aptech.bookingmovies.services.token.TokenService;
import com.aptech.bookingmovies.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ConfirmEmailService confirmEmailService;
    private final TokenService tokenService;
    @GetMapping("/findBillId")
    public ResponseEntity<?> findBillId(@RequestParam int id) throws Exception{
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            User newUser = userService.createUser(userDTO);
            ConfirmEmail confirmEmail = confirmEmailService.createConfirmEmail(newUser);
            confirmEmailService.sendEmail(newUser.getEmail(),confirmEmail.getConfirmCode());
            return ResponseEntity.ok(newUser);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            String token = userService.login(userLoginDTO);
            User userDetail = userService.getUserDetailsFromToken(token);
            tokenService.addToken(userDetail, token);
            return ResponseEntity.ok(token);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(
            @RequestBody String token
    ) throws Exception {
        User userDetail = userService.getUserDetailsFromRefreshToken(token);
        RefreshToken jwtToken = tokenService.refreshToken(token, userDetail);
        return ResponseEntity.ok().body(jwtToken);

    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody UserForgotPasswordDTO userForgotPasswordDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            boolean confirmUser = userService.forgotPassword(userForgotPasswordDTO.getEmail(), userForgotPasswordDTO.getPhoneNumber());
            if(confirmUser){
                User user = userService.findByPhoneNumber(userForgotPasswordDTO.getPhoneNumber());
                ConfirmEmail confirmEmail = confirmEmailService.createConfirmEmail(user);
                confirmEmailService.sendEmail(user.getEmail(),confirmEmail.getConfirmCode());
            }else {
                return ResponseEntity.badRequest().body("Email or phone number wrong");
            }
            return ResponseEntity.ok(confirmUser);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/checkOtp")
    public ResponseEntity<?> checkOtp(@RequestParam String otp, @RequestParam String email){
        if(confirmEmailService.checkingConfirmCode(otp,email)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestParam String phoneNumber, @RequestParam String newPassword,@RequestParam String confirmPassword){
        try{
            String result = userService.changePassword(phoneNumber,newPassword,confirmPassword);
            return ResponseEntity.ok(result);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
