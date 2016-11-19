package Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ProdutoDados implements Serializable {

    private int idprodutodados;
    private int quantidade;
    private Date datavalidade;
    private Produto idproduto;

    public int getIdprodutodados() {
        return idprodutodados;
    }

    public void setIdprodutodados(int idprodutodados) {
        this.idprodutodados = idprodutodados;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(Date datavalidade) {
        this.datavalidade = datavalidade;
    }

    public Produto getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Produto idproduto) {
        this.idproduto = idproduto;
    }

}
