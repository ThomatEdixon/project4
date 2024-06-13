package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.UserDTO;
import com.aptech.bookingmovies.dtos.UserForgotPasswordDTO;
import com.aptech.bookingmovies.dtos.UserLoginDTO;
import com.aptech.bookingmovies.models.*;
import com.aptech.bookingmovies.services.ConfirmEmailService;
import com.aptech.bookingmovies.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
            return ResponseEntity.ok(token);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
    public ResponseEntity<?> changePassword(@RequestParam int userId, @RequestParam String newPassword,@RequestParam String confirmPassword){
        try{
            String result = userService.changePassword(userId,newPassword,confirmPassword);
            return ResponseEntity.ok(result);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
