package com.ig.amr.service;

import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ig.amr.domain.Account;
import com.ig.amr.dto.account.AccountResponse;
import com.ig.amr.dto.account.SignUpRequest;
import com.ig.amr.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountResponse signUp(SignUpRequest signUpRequest) throws BadRequestException {
        boolean isExist = accountRepository
            .existsByEmail(signUpRequest.getEmail());
        if (isExist) throw new BadRequestException("이미 존재하는 이메일입니다.");
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Account account = new Account(
            signUpRequest.getEmail(),
            encodedPassword,
            signUpRequest.getNickname());

        account = accountRepository.save(account);
        return AccountResponse.of(account);
    }
}