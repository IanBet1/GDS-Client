package Model;

import java.io.Serializable;
import java.util.Date;

public class Relatorio implements Serializable {
    private String descricao;
    private int qtd1;
    private int qtd2;
    private Date date1;
    private Date date2;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtd1() {
        return qtd1;
    }

    public void setQtd1(int qtd1) {
        this.qtd1 = qtd1;
    }

    public int getQtd2() {
        return qtd2;
    }

    public void setQtd2(int qtd2) {
        this.qtd2 = qtd2;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }
}
