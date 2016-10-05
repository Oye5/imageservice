package com.image.service;

import com.image.model.Accounts;

public interface AccountService {

	Accounts getAccountByAuthToken(String authToken);

}
