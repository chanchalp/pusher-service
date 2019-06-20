package com.infinity.pusher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Value("${com.infinity.pusher.host}")
	String host;
	
	@Value("${com.infinity.pusher.port}")
	String port;
	
	@Value("${com.infinity.pusher.user}")
	String user;
	
	@Value("${com.infinity.pusher.password}")
	String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
