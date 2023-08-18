# SCES

Sistema de Controle de Entrada e Saída

# Instalação

## Servidor Wildifly

Sistema criado e rodando no Wildfly versão 21.

## Configurações

1. Ter rodando um servidor [SCATI](https://github.com/forcaaereabrasileira/SCATI);
2. Configurar o LDAP da FAB no servidor Wildfly: `java:global/ldap/fab`;
3. Configurar o banco de dados no serdivod Wildfly: `java:/SCES`
4. Configurar o banco de dados com o arquivo do MySql Workbench.
5. Configurar 3 variáveis no arquivo: `src\main\java\com\suchorski\sces\oauth\ScatiAPI.java`;

# Dúvidas?

Entre em contato!