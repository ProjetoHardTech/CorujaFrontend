package br.ifba.demo.frontend.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ComentarioDTO {

    private Long id;
    private Long id_usuario;
    private Long id_post;
    private String nome_usuario;
	private String comentario;
    private Timestamp data_cadastro;

    public ComentarioDTO(){
        super();
    }

    public ComentarioDTO(Long id, Long id_usuario, Long id_post, String nome_usuario, String comentario, Timestamp data_cadastro) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_post = id_post;
        this.nome_usuario = nome_usuario;
        this.comentario = comentario;
        this.data_cadastro = data_cadastro;
    }

}
