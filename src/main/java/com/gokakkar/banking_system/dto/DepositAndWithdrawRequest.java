package com.gokakkar.banking_system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepositAndWithdrawRequest {
    private String accountNumber;
    private Double amount;

    public DepositAndWithdrawRequest(){

    }
}
