package school.sptech.model;

public class MunicipioBasico {
    private Integer ano;
    private Integer idMunicipio;
    private String siglaUf;

    public MunicipioBasico(Integer ano, Integer idMunicipio, String siglaUf){
        this.ano = ano;
        this.idMunicipio = idMunicipio;
        this.siglaUf = siglaUf;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }

    @Override
    public String toString() {
        return "MunicipioBasico{" +
                "ano=" + ano +
                ", idMunicipio=" + idMunicipio +
                ", siglaUf='" + siglaUf + '\'' +
                '}';
    }
}
