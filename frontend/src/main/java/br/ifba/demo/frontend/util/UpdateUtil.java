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
                String diretorio = "C:\\Users\\diogo\\Documents\\CoorujaFrontend\\frontend\\src\\main\\resources\\static\\imagens";
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
