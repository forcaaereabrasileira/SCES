package com.suchorski.sces.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.Setter;

public class ScatiAPI {
	
	public static final String CLIENT_PUBLIC = "SCATI_PUBLIC_TOKEN";
	public static final String CLIENT_SECRET = "SCATI_SECRET_TOKEN";
	public static final String SCOPE = "scv";
	
	public static final String URI_CLIENT = "SCATI_URL";
	public static final String URI_SERVER = URI_CLIENT;
	
	private static final String URI_OAUTH = String.format("http://%s/scati/oauth/v2/login", URI_SERVER);
	private static final String URI_LOGOFF = String.format("http://%s/scati/oauth/v2/logoff?token=%%s", URI_SERVER);
	private static final String URI_RESOURCE = String.format("http://%s/scati/resource/v2/userinfo/%%s/%%s", URI_SERVER);
	
	@Getter private String responseType;
	@Getter private String publicKey;
	@Getter private String privateKey;
	@Getter private String redirectUri;
	@Getter private String scope;
	@Getter @Setter private String state;
	@Getter @Setter private String token;
	
	public ScatiAPI() {
		this.responseType = "code";
		this.scope = "none";
		this.state = UUID.randomUUID().toString().replaceAll("-", "").substring(1, 16);
	}
	
	public ScatiAPI setPublicKey(String publicKey) {
		this.publicKey = publicKey;
		return this;
	}
	
	public ScatiAPI setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		return this;
	}
	
	public ScatiAPI setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
		return this;
	}
	
	public ScatiAPI setScope(String scope) {
		this.scope = scope;
		return this;
	}
	
	public String generateLogonUri() {
		String uriEncoded;
		try {
			uriEncoded = URLEncoder.encode(redirectUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			uriEncoded = redirectUri;
		}
		this.state = UUID.randomUUID().toString().replaceAll("-", "").substring(1, 10);
		return String.format("%s?response_type=%s&client_id=%s&redirect_uri=%s&scope=%s&state=%s", URI_OAUTH, responseType, publicKey, uriEncoded, scope, state);
	}
	
	public ResourceUserData getResourceUserData() {
		if (token == null || token.isEmpty()) {
			return new ResourceUserData("Token não pode ser nulo");
		}
		HttpResponse<ResourceUserData> response = Unirest.get(String.format(URI_RESOURCE, privateKey, token)).header("Content-Type", "application/json").asObject(ResourceUserData.class);
		return response.getBody();
	}
	
	public String logoffUri() {
		return String.format(URI_LOGOFF, token);
	}

}
