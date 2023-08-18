package br.ifba.demo.frontend.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PostResponse {

    private Integer id;
	private String titulo;
    private String comentario;
	private String tags;
    private String id_arquivo;
    private String path_arquivo;
    private String nome;
    private Timestamp data_cadastro;
    private List<ComentarioDTO> comentarios;

    public PostResponse(){}

	public PostResponse(Integer id, String titulo, String comentario, String tags, String id_arquivo,
            String path_arquivo, String nome, Timestamp data_cadastro, List<ComentarioDTO> comentarios) {
        this.id = id;
        this.titulo = titulo;
        this.comentario = comentario;
        this.tags = tags;
        this.id_arquivo = id_arquivo;
        this.path_arquivo = path_arquivo;
        this.nome = nome;
        this.data_cadastro = data_cadastro;
        this.comentarios = comentarios;
    }
    

    


    
}
