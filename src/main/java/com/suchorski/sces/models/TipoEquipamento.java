package com.suchorski.sces.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="tipo_equipamento")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class TipoEquipamento implements Serializable {

	private static final long serialVersionUID = 940958248620847166L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(insertable=false, updatable=false)
	private Integer id;

	@Column(insertable=false, updatable=false)
	private String descricao;
	
	@Override
	public String toString() {
		return descricao;
	}

}