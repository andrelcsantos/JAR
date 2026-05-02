package school.sptech;

import school.sptech.service.ExcelBasico;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        String caminho = "/home/andre/Downloads/municipio_agua_esgoto.xlsx";

        try (InputStream arquivo = new FileInputStream(caminho)) {
            Conexao conexao = new Conexao();
            ExcelBasico service = new ExcelBasico(conexao.getConexao());
            service.processar(arquivo);
            System.out.println("Tudo concluído!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}