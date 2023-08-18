package com.suchorski.sces.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-11-25T15:36:41.185-0300")
@StaticMetamodel(Cliente.class)
public class Cliente_ {
	public static volatile SingularAttribute<Cliente, Integer> id;
	public static volatile SingularAttribute<Cliente, String> cpf;
	public static volatile SingularAttribute<Cliente, String> nomeExibicao;
	public static volatile ListAttribute<Cliente, Controle> controlesAdministradoresEntradas;
	public static volatile ListAttribute<Cliente, Controle> controlesUsuariosEntradas;
	public static volatile ListAttribute<Cliente, Controle> controlesAdministradoresSaidas;
	public static volatile ListAttribute<Cliente, Controle> controlesUsuariosSaidas;
}
