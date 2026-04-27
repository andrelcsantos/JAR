package school.sptech;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class Conexao {

    private DataSource conexao;

    public Conexao() {

        DriverManagerDataSource driver = new DriverManagerDataSource();

        driver.setUsername("dev_user");
        driver.setPassword("senha123");
        driver.setUrl("jdbc:mysql://localhost:3306/exemplo_conexao?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        driver.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.conexao = driver;
    }

    public DataSource getConexao(){
        return this.conexao;
    }
}
