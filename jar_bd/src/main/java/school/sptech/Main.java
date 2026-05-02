package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import school.sptech.service.ExcelBasico;

import java.io.FileInputStream;
import java.io.InputStream;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        String caminho = "/home/andre/Downloads/municipio_agua_esgoto.xlsx";
        String nomeArquivo = caminho.substring(caminho.lastIndexOf('/') + 1);

        log.info("Iniciando o arquivo: {}", nomeArquivo);

        try (InputStream arquivo = new FileInputStream(caminho)) {
            Conexao conexao = new Conexao();
            ExcelBasico service = new ExcelBasico(conexao.getConexao());
            service.processar(arquivo);
            long fim = System.currentTimeMillis();
            log.info("Processamento concluído com sucesso. Tempo total: {} segundos", (fim - inicio) / 1000.0);
        } catch (Exception e) {
            log.error("Erro durante o processamento do arquivo: {}:", caminho, e);
            System.exit(1);
        }
    }
}