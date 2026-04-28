package school.sptech.service;

import org.apache.poi.util.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.InputStream;

public class ExcelBasico {
    private final JdbcTemplate jdbc;

    public ExcelBasico(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("CREATE TABLE IF NOT EXISTS municipio_basico (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "ano INT, " +
                "id_municipio INT, " +
                "sigla_uf VARCHAR(2)" +
                ")");
        System.out.println("Tabela 'municipio_basico' pronta.");
    }

    public void processar(InputStream inputStream) throws Exception {
        IOUtils.setByteArrayMaxOverride(500_000_000);

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            int totalLinhas = sheet.getLastRowNum();
            int inseridos = 0;

            for (int i = 1; i <= totalLinhas; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                Cell celulaAno = row.getCell(0);
                Cell celulaId  = row.getCell(1);
                Cell celulaUf  = row.getCell(2);

                if (celulaAno == null) continue;

                int ano = 0;
                int idMunicipio = 0;
                String siglaUf = "";

                if (celulaAno.getCellType() == CellType.NUMERIC) {
                    ano = (int) celulaAno.getNumericCellValue();
                } else if (celulaAno.getCellType() == CellType.STRING) {
                    ano = Integer.parseInt(celulaAno.getStringCellValue().trim());
                }

                if (celulaId != null) {
                    if (celulaId.getCellType() == CellType.NUMERIC) {
                        idMunicipio = (int) celulaId.getNumericCellValue();
                    } else if (celulaId.getCellType() == CellType.STRING) {
                        idMunicipio = Integer.parseInt(celulaId.getStringCellValue().trim());
                    }
                }

                if (celulaUf != null) {
                    siglaUf = celulaUf.getStringCellValue().trim();
                }

                jdbc.update(
                        "INSERT INTO municipio_basico (ano, id_municipio, sigla_uf) VALUES (?, ?, ?)",
                        ano, idMunicipio, siglaUf
                );
                inseridos++;

                if (i % 500 == 0) {
                    System.out.println("Processadas " + i + " linhas...");
                }
            }

            System.out.println("Total de linhas processadas: " + totalLinhas);
            System.out.println("Registros inseridos: " + inseridos);
        }
    }
}