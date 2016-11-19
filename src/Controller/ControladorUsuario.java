package Controller;

import Model.Usuario;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ControladorUsuario {

    private final Usuario usuario;
    private String erro;
    private final String endereco;

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public ControladorUsuario() {
        this.usuario = new Usuario();
        this.endereco = "localhost";
    }

    public void criarUsuario(Usuario usuarioCriado) throws IOException {
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando criação de usuário na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(2);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                outToServer2.writeObject(usuarioCriado);
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            inFromServer.close();
            clientSocket.close();
        } catch (Exception e) {
            this.erro = "Erro ao criar usuário: " + e.getMessage();
        }
    }

    public Usuario validarUsuario(Usuario validar) {
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando validação de usuário na porta 1094.");
            Usuario validado = null;

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(3);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromServer2 = new ObjectInputStream(clientSocket.getInputStream());
                outToServer2.writeObject(validar);
                this.erro = inFromServer.readUTF();
                validado = (Usuario) inFromServer2.readObject();
            }
            outToServer1.close();
            inFromServer.close();
            clientSocket.close();
            return validado;
        } catch (Exception e) {
            this.erro = "Erro ao logar no sistema: " + e.getMessage();
            return null;
        }
    }

    public void deslogarUsuario(Usuario logado) {
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando desconexão de usuário na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(4);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                outToServer2.writeObject(logado);
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            inFromServer.close();
            clientSocket.close();
        } catch (Exception e) {
            this.erro = "Erro ao criar usuário: " + e.getMessage();
        }
    }
}
