package com.suchorski.sces.oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceUserData {
	
	private boolean valid;
	private String message;
	private String cpf;
	private String saram;
	private String nomeCompleto;
	private String nomeGuerra;
	private String patente;
	private String om;
	private String zimbra;
	private List<String> perfis;
	
	public ResourceUserData(String message) {
		this.valid = false;
		this.message = message;
		perfis = new ArrayList<String>();
	}
	
	public boolean hasPerfil(String codigos) {
		return Arrays.asList(codigos.split(" ")).stream().anyMatch(c -> getPerfis().stream().anyMatch(p -> p.equalsIgnoreCase(c)));
	}
	
}
