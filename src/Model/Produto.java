package Model;

import java.io.Serializable;

public class Produto implements Serializable {

    private int idproduto;
    private String descricao;
    private Usuario matricula;

    public int getId() {
        return idproduto;
    }

    public void setId(int id) {
        this.idproduto = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getMatricula() {
        return matricula;
    }

    public void setMatricula(Usuario matricula) {
        this.matricula = matricula;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
