package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.repositories.ConfirmEmailRepository;
import com.aptech.bookingmovies.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmEmailService {
    private final UserRepository userRepository;
    private final ConfirmEmailRepository confirmEmailRepository;

}
