package com.gokakkar.banking_system.util;

public class AppConstants {

    // Postman string messages
    public static final String USER_EXISTS_MSG = "User already exists!";


    // API endpoints
    public static final String BASE_ENDPOINT = "/api/banking";
    public static final String REGISTER_USER_ENDPOINT = "/register";
    public static final String GET_USER_ENDPOINT = "/{accountNumber}";
    public static final String DELETE_USER_ENDPOINT = "/{accountNumber}";
    public static final String UPDATE_USER_ENDPOINT = "/{accountNumber}/user";
    public static final String USER_LOGIN_ENDPOINT = "/login";
    public static final String DEPOSIT_ENDPOINT = "/deposit";
    public static final String WITHDRAW_ENDPOINT = "/withdraw";
    public static final String TRANSFER_ACCOUNT = "/transfer";

}