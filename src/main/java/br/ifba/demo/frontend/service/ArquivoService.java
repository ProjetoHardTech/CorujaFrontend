package br.ifba.demo.frontend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class ArquivoService {
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
	
	public boolean upload(MultipartFile file, String id_usuario ) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("file", new FileSystemResource(convert(file)));
		formData.add("id_usuario", id_usuario);
        Mono<Boolean> retorno = this.webClient
									.method(HttpMethod.POST)
									.uri("arquivo")
									.contentType(MediaType.MULTIPART_FORM_DATA)
									.body(BodyInserters.fromMultipartData(formData))
									.retrieve()
									.bodyToMono(Boolean.class);
		Boolean tm = retorno.block();
		return tm.booleanValue();
	}

	public ResponseEntity<byte[]> get_imagem(String id) {
		
		byte[] imageBytes = this.webClient
								.method(HttpMethod.GET)
								.uri("arquivo/displayImage/{id}", id)
								.retrieve()
								.bodyToMono(byte[].class)
								// .exchangeStrategies(exchangeStrategies)
								.block();

		String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(id))
				.body(imageBytes);
	}

	public String get_url_file(String id) {
		String url = this.webClient
								.method(HttpMethod.GET)
								.uri("arquivo/view/{filename}", "teste.png")
								.retrieve()
								.bodyToMono(String.class)
								// .exchangeStrategies(exchangeStrategies)
								.block();
		return url;
	}
}
