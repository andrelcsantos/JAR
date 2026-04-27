package school.sptech;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Conexao conexao = new Conexao();
        JdbcTemplate template = new JdbcTemplate(conexao.getConexao());

        template.execute("DROP TABLE IF EXISTS PESSOA");

        template.execute("CREATE TABLE PESSOA" + "(id int primary key auto_increment, nome varchar(50), data_nascimento DATE, isAdmin bit)");

        template.update("INSERT INTO PESSOA VALUES(DEFAULT, ?, ?, ?)","Pedro", "2023-08-19", 0);

        List<Pessoa> pessoas = template.query("SELECT * FROM PESSOA", new BeanPropertyRowMapper<>(Pessoa.class));

        System.out.println(pessoas);

        System.out.println("UPDATE");
        template.update("UPDATE PESSOA SET nome = ? WHERE id = ?", "Claudio", 1);
        pessoas = template.query("SELECT * FROM PESSOA", new BeanPropertyRowMapper<>(Pessoa.class));
        System.out.println(pessoas);
    }
}
