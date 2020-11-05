package com.techelevator.tenmo.services;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;

public class RestTransferService {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();

	public RestTransferService(String url) {
		this.BASE_URL = url;
	}
	public Transfer[] getTransfersforUser() {
		Transfer[] transfers;
		transfers = restTemplate.exchange(BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		return transfers;
	}
	public Transfer getTransferById() {
		Transfer transfer = null;
		transfer = restTemplate.exchange(BASE_URL + "transfers/{id}", HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
		return transfer;
	}
	public void sendTransfer(Transfer transfer) {
		restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST, makeAuthEntity(), Transfer.class);
	}


	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}

}
