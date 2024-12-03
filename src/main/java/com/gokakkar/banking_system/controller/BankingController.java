package com.gokakkar.banking_system.controller;

import com.gokakkar.banking_system.dto.DepositAndWithdrawRequest;
import com.gokakkar.banking_system.dto.LoginDTO;
import com.gokakkar.banking_system.dto.TransferRequest;
import com.gokakkar.banking_system.model.Account;
import com.gokakkar.banking_system.model.User;
import com.gokakkar.banking_system.service.AccountService;
import com.gokakkar.banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.gokakkar.banking_system.util.AppConstants.*;

@RestController
@RequestMapping(BASE_ENDPOINT)
public class BankingController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    @PostMapping(REGISTER_USER_ENDPOINT)
    public String registerUser(@RequestBody User user)
    {
        if(userService.isUserExists(user.getEmail()))
        {
            return "User already exists!";
        }
        userService.registerUser(user);
        Account account = accountService.createAccount(user);
        return "User registered successfully with Account Number: "+account.getAccountNumber();
    }

    // Get user details
    @RequestMapping(GET_USER_ENDPOINT)
    public ResponseEntity<Account> getAccountDetails(@PathVariable String accountNumber)
    {
        Account account = accountService.getAccountDetails(accountNumber);
        if(account==null)

        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    // Delete account
    @DeleteMapping(DELETE_USER_ENDPOINT)
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber)
    {
        boolean deleted = accountService.deleteAccount(accountNumber);
        if(deleted)
        {
            return ResponseEntity.ok("Account with account number "+ accountNumber+" deleted successfully.");
        }
        else{
            return ResponseEntity.status(404).body("Account with account number " + accountNumber + " not found.");
        }
    }

    // Updating user details associated with the account
    @PutMapping(UPDATE_USER_ENDPOINT)
    public ResponseEntity<String> updateUserDetailsInAccount(@PathVariable String accountNumber, @RequestBody User updatedUser)
    {
        try{
            Account updatedAccount = accountService.updateUserDetailsInAccount(accountNumber, updatedUser);
            return ResponseEntity.ok("User details updated successfully for account number: "+updatedAccount.getAccountNumber());
        }catch(RuntimeException e)
        {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // User login
    @PostMapping(USER_LOGIN_ENDPOINT)
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO)
    {
        // Step1 : Fetch user email
        User user = userService.getUserByEmail(loginDTO.getEmail());

        // Step2 : Check if user exists
        if(user==null)
        {
            return ResponseEntity.status(404).body("User not found");
        }

        // Step3 : Compare password
        if(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
        {
            return ResponseEntity.ok("Login successful. Welcome, "+user.getName()+"!");
        }
        else{
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }

    // Deposit transaction
    @PostMapping(DEPOSIT_ENDPOINT)
    public String deposit(@RequestBody DepositAndWithdrawRequest request){
        return accountService.deposit(request.getAccountNumber(), request.getAmount());
    }

    // Withdraw transaction
    @PostMapping(WITHDRAW_ENDPOINT)
    public String withdraw(@RequestBody DepositAndWithdrawRequest request){
        return accountService.withdraw(request.getAccountNumber(), request.getAmount());
    }

    // Transfer transaction
    @PostMapping(TRANSFER_ACCOUNT)
    public String transfer(@RequestBody TransferRequest request)
    {
        if(request.getAmount() == null || request.getAmount() <= 0)
        {
            return "ALERT : Invalid transfer amount!";
        }
        if(request.getFromAccountNumber()==null || request.getToAccountNumber()==null)
        {
            return "ALERT : Both 'from' & 'to' account numbers are required!";
        }
        return accountService.transfer(request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount());
    }
}
