package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class RestAccountService implements UserService {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	
	public RestAccountService(String url) {
		this.BASE_URL = url;
	}

	@Override
	public User getAccountBalance(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User sendBucks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User requestBucks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User viewTransferHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User viewPendingTransfers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User listTransactionsForAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getTransactionsDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	  private HttpEntity makeAuthEntity() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity entity = new HttpEntity<>(headers);
		    return entity;
		  }

	

}
