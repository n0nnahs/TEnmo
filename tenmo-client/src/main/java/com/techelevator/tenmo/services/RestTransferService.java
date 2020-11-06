package com.techelevator.tenmo.services;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferDTO;

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
	public void sendTransfer(TransferDTO transferDto) throws TransferServiceException {
		try {
			restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST, makeAuthTransferDTO(transferDto), Transfer.class);
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
	}
	public Account[] viewAvailableAccounts() throws AccountServiceException {
		Account[] accounts = null;
		try {
			accounts = restTemplate.exchange(BASE_URL + "accounts", HttpMethod.GET, makeAuthEntity(), Account[].class).getBody();
			return accounts;
		 } catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
	}


	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	private HttpEntity makeAuthTransferDTO(TransferDTO transferDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(transferDto, headers);
		return entity;
	}

}
