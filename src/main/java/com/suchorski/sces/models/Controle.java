package com.suchorski.sces.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class Controle implements Serializable {

	private static final long serialVersionUID = -2413313209576813106L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false)
	private Long id;

	private String contato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_entrada", insertable=false, updatable=false)
	private Date dataEntrada;

	@Column(name="data_saida", insertable=false)
	private Date dataSaida;

	private String descricao;

	private String dinf;

	private String sau;
	
	@ManyToOne
	@JoinColumn(name="tipo_equipamento_id")
	private TipoEquipamento tipoEquipamento;

	@ManyToOne
	@JoinColumn(name="administrador_entrada_id")
	private Cliente administradorEntrada;
	
	@ManyToOne
	@JoinColumn(name="usuario_entrada_id")
	private Cliente usuarioEntrada;

	@ManyToOne
	@JoinColumn(name="administrador_saida_id")
	private Cliente administradorSaida;

	@ManyToOne
	@JoinColumn(name="usuario_saida_id")
	private Cliente usuarioSaida;
	
	public Controle(String sau, String dinf, String contato, TipoEquipamento tipoEquipamento, String descricao, Cliente administrador, Cliente usuario) {
		this.sau = sau;
		this.dinf = dinf;
		this.contato = contato;
		this.tipoEquipamento = tipoEquipamento;
		this.descricao = descricao;
		this.administradorEntrada = administrador;
		this.usuarioEntrada = usuario;
	}

}