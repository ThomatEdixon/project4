package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.components.JwtTokenUtil;
import com.aptech.bookingmovies.dtos.UserDTO;
import com.aptech.bookingmovies.dtos.UserLoginDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.*;
import com.aptech.bookingmovies.repositories.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserStatusRepository userStatusRepository;
    private final RankCustomerRepository rankCustomerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(()-> new DataNotFoundException("Role not found"));
        if(role.getRoleName().toUpperCase().equals(Role.ADMIN)){
            throw new DataNotFoundException("You cannot register an admin account");
        }
        UserStatus userStatus= userStatusRepository.findById(userDTO.getUserStatusId())
                .orElseThrow(()-> new DataNotFoundException(" Status not found"));
        RankCustomer rankCustomer = rankCustomerRepository.findById(userDTO.getRankCustomerId())
                .orElseThrow(()->new DataNotFoundException("Rank not Found"));
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = User.builder()
                .point(userDTO.getPoint())
                .userName(userDTO.getUserName())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(encodedPassword)
                .isActive(true)
                .role(role)
                .rankCustomer(rankCustomer)
                .userStatus(userStatus)
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        Optional<User> users = userRepository.findByPhoneNumber(userLoginDTO.getPhoneNumber());
        if(users.isEmpty()){
            throw new DataNotFoundException("Can not found user with "+ userLoginDTO.getPhoneNumber());
        }
        User existingUser = users.get();
        if(!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())){
            throw new BadCredentialsException("Wrong number or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword(),existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        String token = jwtTokenUtil.generateToken(existingUser);
        Date expiredTime = jwtTokenUtil.extractClaim(token,Claims::getExpiration);
        // RefreshToken
        RefreshToken refreshToken = RefreshToken.builder()
                .Token(token)
                .expiredTime(expiredTime)
                .user(existingUser)
                .build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }
}
