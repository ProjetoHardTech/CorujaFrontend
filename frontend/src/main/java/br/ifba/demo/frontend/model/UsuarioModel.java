package br.ifba.demo.frontend.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {
	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private Boolean termos_aceite;
	private Long id_perfil;
	private Date data_cadastro;
	private Date data_modificacao;
	private Date data_criacao;
	private Date data_conclusao;
	// private Boolean status;
}