package school.sptech;

import school.sptech.service.S3Service;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        S3Service s3Service = new S3Service();
        String chaveObjeto = "municipiose_saneamento.csv";
        String caminhoDestino = "./municipiose_saneamento.csv";

        try (InputStream is = s3Service.obterArquivo(chaveObjeto)) {
            Files.copy(is, Path.of(caminhoDestino), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Download concluído! Arquivo salvo em: " + caminhoDestino);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            System.exit(1);
        }
    }
}