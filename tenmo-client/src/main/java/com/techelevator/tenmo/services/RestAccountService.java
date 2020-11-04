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
import com.techelevator.tenmo.models.Account;

public class RestAccountService  {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	
	public RestAccountService(String url) {
		this.BASE_URL = url;
	}
	
	public Account getAccountBalance(int id) throws AccountServiceException {
		Account balance = null;
		try {
			balance = restTemplate.exchange(BASE_URL + "accounts/balance", HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return balance;
	}

	
	  private HttpEntity makeAuthEntity() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity entity = new HttpEntity<>(headers);
		    return entity;
		  }

	

}
