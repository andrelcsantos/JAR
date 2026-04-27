package school.sptech;

import java.time.LocalDate;

public class Pessoa {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private Integer isAdmin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "dataNascimento=" + dataNascimento +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
