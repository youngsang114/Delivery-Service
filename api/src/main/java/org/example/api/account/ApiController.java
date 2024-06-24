package org.example.api.account;

import lombok.RequiredArgsConstructor;
import org.example.db.Account.AccountEntity;
import org.example.db.Account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class ApiController {

    private final AccountRepository accountRepository;

    @GetMapping("")
    public void save(){
        AccountEntity account = AccountEntity.builder().build();
        accountRepository.save(account);
    }
}
