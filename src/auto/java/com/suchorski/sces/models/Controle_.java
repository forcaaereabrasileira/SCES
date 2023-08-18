package com.suchorski.sces.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-12-04T14:02:49.995-0300")
@StaticMetamodel(Controle.class)
public class Controle_ {
	public static volatile SingularAttribute<Controle, Long> id;
	public static volatile SingularAttribute<Controle, String> contato;
	public static volatile SingularAttribute<Controle, Date> dataEntrada;
	public static volatile SingularAttribute<Controle, Date> dataSaida;
	public static volatile SingularAttribute<Controle, String> descricao;
	public static volatile SingularAttribute<Controle, String> dinf;
	public static volatile SingularAttribute<Controle, String> sau;
	public static volatile SingularAttribute<Controle, TipoEquipamento> tipoEquipamento;
	public static volatile SingularAttribute<Controle, Cliente> administradorEntrada;
	public static volatile SingularAttribute<Controle, Cliente> usuarioEntrada;
	public static volatile SingularAttribute<Controle, Cliente> administradorSaida;
	public static volatile SingularAttribute<Controle, Cliente> usuarioSaida;
}
