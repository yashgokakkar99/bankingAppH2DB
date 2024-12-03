package com.gokakkar.banking_system.service;

import com.gokakkar.banking_system.model.Account;
import com.gokakkar.banking_system.model.Transaction;
import com.gokakkar.banking_system.model.User;
import com.gokakkar.banking_system.repository.AccountRepository;
import com.gokakkar.banking_system.repository.TransactionRepository;
import com.gokakkar.banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private TransactionRepository transactionRepo;

    private String generateAccountNumber()
    {
        Random random = new Random();
        int accountNumber = random.nextInt(900000)+100000;
        return "ACC" + accountNumber;
    }

    public Account createAccount(User user)
    {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User must be provided and saved before creating an account.");
        }

        Account account = new Account();
        String accountNumber = generateAccountNumber();

        // Appending into the Account table
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0);
        account.setUser(user);

        return accountRepo.save(account);
    }

    public Account getAccountDetails(String accountNumber)
    {
        return accountRepo.findById(accountNumber).orElse(null);
    }

    public boolean deleteAccount(String accountNumber)
    {
        if(accountRepo.existsById(accountNumber))
        {
            accountRepo.deleteById(accountNumber);
            return true;
        }
        return false;
    }

    public Account updateUserDetailsInAccount(String accountNumber, User updatedUser)
    {
        Account account = accountRepo.findById(accountNumber)
                .orElseThrow(()->new RuntimeException("Account with account number "+accountNumber+" not found."));
        User existingUser = account.getUser();
        if(existingUser != null){
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            userRepo.save(existingUser);
        }
        else{
            throw new RuntimeException("No user associated with the account "+accountNumber);
        }
        return account;
    }

    // DEPOSIT METHOD
    public String deposit(String accountNumber, Double amount)
    {
        if(amount<=0)
        {
            return "ALERT : Deposit amount must be greater than zero.";
        }
        Account account = accountRepo.findByAccountNumber(accountNumber);
        if(account==null)
        {
            return "ALERT : Account not found.";
        }

        // Update balance
        account.setBalance(account.getBalance()+amount);
        accountRepo.save(account);

        // Logging transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setToAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transactionRepo.save(transaction);

        return "MESSAGE : Deposit successful !! Balance : "+account.getBalance();
    }

    // WITHDRAW METHOD
    public String withdraw(String accountNumber, Double amount)
    {
        if(amount<=0)
        {
            return "ALERT : Withdrawal amount must be greater than zero.";
        }
        Account account = accountRepo.findByAccountNumber(accountNumber);
        if(account == null)
        {
            return "ALERT : Account not found.";
        }
        if(account.getBalance()<amount){
            return "ALERT : Insufficient balance.";
        }
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);

        // Logging transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setFromAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transactionRepo.save(transaction);

        return "MESSAGE : Withdrawal successful !! Balance : "+account.getBalance();
    }

    // TRANSFER METHOD
    public String transfer(String fromAccountNumber, String toAccountNumber, Double amount){
        if(amount<=0)
        {
            return "ALERT : Transfer amount must be greater than zero.";
        }
        if(fromAccountNumber.equals(toAccountNumber))
        {
            return "ALERT : Sender & reciever accounts cannot be the same.";
        }

        Account fromAccount = accountRepo.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepo.findByAccountNumber(toAccountNumber);

        if(fromAccount == null || toAccount==null)
        {
            return "ALERT : One or both the accounts not found.";
        }
        if(fromAccount.getBalance()<amount){
            return "ALERT : Insufficient balance.";
        }

        // Performing transfer
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);

        // Logging transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setFromAccount(fromAccountNumber);
        transaction.setToAccount(toAccountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType("TRANS FER");
        transactionRepo.save(transaction);

        return "Transfer successful !! Balance: " + fromAccount.getBalance();
    }
}
