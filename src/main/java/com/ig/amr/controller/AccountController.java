package com.ig.amr.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ig.amr.config.security.JwtProvider;
import com.ig.amr.domain.AccountDetails;
import com.ig.amr.dto.account.AccountResponse;
import com.ig.amr.dto.account.SignUpRequest;
import com.ig.amr.dto.account.TokenResponse;
import com.ig.amr.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    public AccountResponse signUp(
        @RequestBody SignUpRequest signUpRequest
    ) throws BadRequestException {
        return accountService.signUp(signUpRequest);
    }

    @GetMapping("/reissue")
    public TokenResponse reissue(
        @AuthenticationPrincipal AccountDetails accountDetails
    ) throws JsonProcessingException {
        AccountResponse accountResponse = AccountResponse.of(accountDetails.getAccount());
        return jwtProvider.reissueAtk(accountResponse);
    }
}