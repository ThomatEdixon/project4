package com.aptech.bookingmovies.services.user;

import com.aptech.bookingmovies.components.JwtTokenUtil;
import com.aptech.bookingmovies.dtos.UserDTO;
import com.aptech.bookingmovies.dtos.UserLoginDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.exceptions.ExpiredTokenException;
import com.aptech.bookingmovies.exceptions.PasswordNotMatch;
import com.aptech.bookingmovies.models.*;
import com.aptech.bookingmovies.repositories.*;
import com.aptech.bookingmovies.services.user.IUserService;
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

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserStatusRepository userStatusRepository;
    private final RankCustomerRepository rankCustomerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenRepository tokenRepository;
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
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        User existingUser = findByPhoneNumber(userLoginDTO.getPhoneNumber());
        if(!passwordEncoder.matches(userLoginDTO.getPassword(), existingUser.getPassword())){
            throw new BadCredentialsException("Wrong number or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword(),existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public String changePassword(String phoneNumber,String newPassword, String confirmPassword) throws Exception{
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new DataNotFoundException("Can not found user "));
        if(newPassword.equals(confirmPassword)){
            String newPasswordEncode = passwordEncoder.encode(newPassword);
            user.setPassword(newPasswordEncode);
            userRepository.save(user);
        }else {
            throw new PasswordNotMatch("Password does not match");
        }
        return "Change password success";
    }

    @Override
    public boolean forgotPassword(String email, String phoneNumber) throws Exception{
        User existingUser = findByPhoneNumber(phoneNumber);
        if(existingUser.getEmail().equals(email)){
            return true;
        }
        return false;
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) throws Exception {
        Optional<User> users = userRepository.findByPhoneNumber(phoneNumber);
        if(users.isEmpty()){
            throw new DataNotFoundException("Can not found user with "+ phoneNumber);
        }
        User existingUser = users.get();
        return existingUser;
    }

    @Override
    public User findById(int id) throws Exception {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("can not found user"));
        return existingUser;
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String subject = jwtTokenUtil.getSubject(token);
        Optional<User> user;
        user = userRepository.findByPhoneNumber(subject);
        return user.orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User getUserDetailsFromRefreshToken(String token) throws Exception {
        RefreshToken existingToken = tokenRepository.findByRefreshToken(token);
        return getUserDetailsFromToken(existingToken.getToken());
    }
}
