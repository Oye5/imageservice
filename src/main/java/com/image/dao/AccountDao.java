package com.image.dao;

import com.image.model.Accounts;

public interface AccountDao {
	Accounts getAccountByAuthToken(String authToken);

}
