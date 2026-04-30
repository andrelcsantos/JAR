package school.sptech.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.model.MunicipioBasico;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.*;

public class ExcelBasico {
    private final JdbcTemplate jdbc;
    private List<MunicipioBasico> municipios = new ArrayList<>();

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

    private void salvarLote() {
        String sql = "INSERT INTO municipio_basico (ano, id_municipio, sigla_uf) VALUES (?, ?, ?)";
    }


    public void processar(InputStream inputStream) throws Exception {
        IOUtils.setByteArrayMaxOverride(500_000_000);

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            int totalLinhas = sheet.getLastRowNum();
            int tamanhoLote = 500;
            int inseridos = 0;
            Map<String, Integer> mapearHeard = new HashMap<>();


            for (int i = 0; i <= 3; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                if (i == 0) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        cell.setCellType(CellType.STRING);
                        String nomeColuna = cell.getStringCellValue();
                        mapearHeard.put(nomeColuna, cell.getColumnIndex());
                        System.out.println(nomeColuna);
                    }
                    continue;
                }
                Cell celulaAno = row.getCell(mapearHeard.get("ano"));
                Cell celulaId = row.getCell(mapearHeard.get("id_municipio"));
                Cell celulaUf = row.getCell(mapearHeard.get("sigla_uf"));

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

                MunicipioBasico municipio = new MunicipioBasico(ano, idMunicipio, siglaUf);
                System.out.println(municipio);
                //municipios.add(municipio);


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