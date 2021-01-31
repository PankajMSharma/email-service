package com.emailservice.service;

public interface EmailHandelerService {
	public void sendBasicEmail(String[] to, String subject, String message);
}
