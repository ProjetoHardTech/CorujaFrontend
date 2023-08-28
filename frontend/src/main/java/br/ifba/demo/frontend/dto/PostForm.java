package br.ifba.demo.frontend.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostForm {
	private Long id_usuario;
    private String titulo;
	private String comentario;
	private String tags;
    private MultipartFile file;
}
