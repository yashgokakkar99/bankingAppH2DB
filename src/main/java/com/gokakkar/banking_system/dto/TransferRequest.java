package com.gokakkar.banking_system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    private String fromAccountNumber;
    private String toAccountNumber;
    private Double amount;

    public TransferRequest()
    {

    }
}
