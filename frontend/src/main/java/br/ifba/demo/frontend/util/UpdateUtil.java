package br.ifba.demo.frontend.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UpdateUtil {
    public static boolean enviarImagem(MultipartFile imagem) {

        boolean sucessUpload = false;

        if(!imagem.isEmpty()){
            String arquiveName = imagem.getOriginalFilename();
            try {
<<<<<<< HEAD
                String diretorio = "C:\\frontendLayout\\frontend\\src\\main\\resources\\static\\imagens";
=======
                String diretorio = "C:\\Users\\201910070026\\Projetos\\frontend\\src\\main\\resources\\static\\imagens";
>>>>>>> f6309072843eeebf17c4e0db571c80b9cf995f72
                File dir = new File(diretorio);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath() + File.separator + arquiveName);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();

                System.out.println("O arquivo: " +arquiveName + " foi enviado com sucesso!!");
                sucessUpload = true;
            } catch (Exception e) {
                System.out.println("O envio do arquivo:" +arquiveName + "não foi executado com sucesso" +e.getMessage() );

            }

        }
        else{
                System.out.println("O arquivo está vazio!!");
            }
            return sucessUpload;
    }
}
