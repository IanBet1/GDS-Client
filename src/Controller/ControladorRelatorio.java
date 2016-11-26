package Controller;

import Model.Relatorio;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorRelatorio {

    private Relatorio relatorio;
    private String erro;
    private String endereco;

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public ControladorRelatorio() {
        this.relatorio = new Relatorio();
        this.endereco = "localhost";
    }

    public List<Relatorio> relatorioSaida(Date datainic, Date datafim) {
        List<Relatorio> relatorios = new ArrayList();
        Relatorio r = new Relatorio();
        r.setDescricao("");
        r.setDate1(datainic);
        r.setDate2(datafim);
        r.setQtd1(0);
        r.setQtd2(0);

        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando relatório de saída de produtos na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(13);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromServer2 = new ObjectInputStream(clientSocket.getInputStream());
                outToServer2.writeObject(r);
                relatorios = (List<Relatorio>) inFromServer2.readObject();
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            clientSocket.close();
            return relatorios;
        } catch (Exception e) {
            this.erro = "Impossível fazer o relatório de saída!";
            return null;
        }
    }

    public List<Relatorio> relatorioValidade(Date datainic, Date datafim) {
        List<Relatorio> relatorios = new ArrayList();
        Relatorio r = new Relatorio();
        r.setDescricao("");
        r.setDate1(datainic);
        r.setDate2(datafim);
        r.setQtd1(0);
        r.setQtd2(0);

        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando relatório de validade de produtos na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(14);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromServer2 = new ObjectInputStream(clientSocket.getInputStream());
                outToServer2.writeObject(r);
                relatorios = (List<Relatorio>) inFromServer2.readObject();
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            clientSocket.close();
            return relatorios;
        } catch (Exception e) {
            this.erro = "Impossível fazer o relatório de validade!";
            return null;
        }
    }
    
    public boolean venceEmSete() {
        boolean validade = false;

        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando checagem de validade de produtos na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(15);
            if (inFromServer.readBoolean() == true) {
                validade = inFromServer.readBoolean();
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            clientSocket.close();
            return validade;
        } catch (Exception e) {
            this.erro = "Impossível fazer a checagem de validade!";
            return false;
        }
    }
}
