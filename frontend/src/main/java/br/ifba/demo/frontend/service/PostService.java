package br.ifba.demo.frontend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.ifba.demo.frontend.dto.ComentarioDTO;
import br.ifba.demo.frontend.dto.PostForm;
import br.ifba.demo.frontend.dto.PostResponse;
import reactor.core.publisher.Mono;


	
@Service
public class PostService {

	@Autowired
	private WebClient webClient;

	private Path convert(MultipartFile file) {
        try {
            Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save uploaded file", e);
        }
    }
	
	public boolean add(PostForm postForm) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
		formData.add("id_usuario", postForm.getId_usuario());
		formData.add("titulo", postForm.getTitulo());
		formData.add("comentario", postForm.getComentario());
		formData.add("tags", postForm.getTags());
		formData.add("file", new FileSystemResource(convert(postForm.getFile())));
		
		Mono<Boolean> retorno = this.webClient
									.method(HttpMethod.POST)
									.uri("post")
									.contentType(MediaType.MULTIPART_FORM_DATA)
									.body(BodyInserters.fromMultipartData(formData))
									.retrieve()
									.bodyToMono(Boolean.class);
		Boolean tm = retorno.block();
		return tm.booleanValue();
	}

	public List<PostResponse> listall() {
		Mono<List<PostResponse>> postResponseList = this.webClient
														.method(HttpMethod.GET)
														.uri("post/listall")
														.retrieve()
														.bodyToFlux(PostResponse.class)
														.collectList();
		List<PostResponse> list = postResponseList.block();
		return list;
	}

	public boolean add_comentario(ComentarioDTO comentarioDTO) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
		formData.add("id_usuario", comentarioDTO.getId_usuario());
		formData.add("comentario", comentarioDTO.getComentario());
		formData.add("id_post", comentarioDTO.getId_post());
		
		Mono<Boolean> retorno = this.webClient
									.method(HttpMethod.POST)
									.uri("post/add_comentario")
									.contentType(MediaType.MULTIPART_FORM_DATA)
									.body(BodyInserters.fromMultipartData(formData))
									.retrieve()
									.bodyToMono(Boolean.class);
		Boolean tm = retorno.block();
		return tm.booleanValue();
	}
}
