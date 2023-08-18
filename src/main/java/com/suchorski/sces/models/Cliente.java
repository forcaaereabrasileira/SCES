package com.suchorski.sces.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class Cliente implements Serializable {

	private static final long serialVersionUID = 5582524292249887779L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false)
	private Integer id;

	@Column(updatable=false)
	private String cpf;

	@Column(name="nome_exibicao")
	private String nomeExibicao;

	@OneToMany(mappedBy="administradorEntrada")
	private List<Controle> controlesAdministradoresEntradas;

	@OneToMany(mappedBy="usuarioEntrada")
	private List<Controle> controlesUsuariosEntradas;

	@OneToMany(mappedBy="administradorSaida")
	private List<Controle> controlesAdministradoresSaidas;

	@OneToMany(mappedBy="usuarioSaida")
	private List<Controle> controlesUsuariosSaidas;
	
	public Cliente(String cpf, String nomeExibicao) {
		this.cpf = cpf;
		this.nomeExibicao = nomeExibicao;
		this.controlesAdministradoresEntradas = new ArrayList<Controle>();
		this.controlesUsuariosEntradas = new ArrayList<Controle>();
		this.controlesAdministradoresSaidas = new ArrayList<Controle>();
		this.controlesUsuariosSaidas = new ArrayList<Controle>();
	}
	
	@Override
	public String toString() {
		return nomeExibicao;
	}

}