package Controller;

import Model.Produto;
import Model.ProdutoDados;
import Model.Usuario;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ControladorProduto {

    private Produto produto;
    private String erro;
    private String endereco;

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public ControladorProduto() {
        this.produto = new Produto();
        this.endereco = "localhost";
    }

    public void criarProduto(Produto produtoCriado) throws IOException {
        int idproduto = contagemProduto();
        produtoCriado.setId(idproduto + 1);
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando inserção de produto na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(1);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                outToServer2.writeObject(produtoCriado);
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            inFromServer.close();
            clientSocket.close();
        } catch (Exception e) {
            this.erro = "Erro ao inserir produto: " + e.getMessage();
        }
    }

    public int contagemProduto() throws IOException {
        int contagem = 0;
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando contagem de produtos na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(0);
            contagem = Integer.parseInt(inFromServer.readUTF());

            outToServer1.close();
            clientSocket.close();
            return contagem;
        } catch (Exception e) {
            this.erro = "Impossível fazer a contagem de produtos!";
            return 0;
        }
    }

    public int contagemProdutoDados() throws IOException {
        int contagem = 0;
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando contagem de dados dos produtos na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(6);
            contagem = Integer.parseInt(inFromServer.readUTF());

            outToServer1.close();
            clientSocket.close();
            return contagem;
        } catch (Exception e) {
            this.erro = "Impossível fazer a contagem dos dados de produtos!";
            return 0;
        }
    }

    public List<Produto> preencheCmbBox() {
        List<Produto> produtos = new ArrayList();
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando listagem de produtos na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(5);
            if (inFromServer.readBoolean() == true) {
                ObjectInputStream inFromServer2 = new ObjectInputStream(clientSocket.getInputStream());
                produtos = (List<Produto>) inFromServer2.readObject();
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            clientSocket.close();
            return produtos;
        } catch (Exception e) {
            this.erro = "Impossível fazer a contagem de produtos!";
            return null;
        }
    }

    public boolean verificaProdutoDados(ProdutoDados pd) {
        boolean validacao = false;
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando verificação de dados dos produtos na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(7);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                outToServer2.writeObject(pd);
                validacao = inFromServer.readBoolean();
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            clientSocket.close();
            return validacao;
        } catch (Exception e) {
            this.erro = "Impossível fazer a contagem dos dados de produtos!";
            return false;
        }
    }

    public void adicionarProdutoDados(ProdutoDados pd, Usuario u) {
        if (verificaProdutoDados(pd) == false) {
            try (Socket clientSocket = new Socket(this.endereco, 1094)) {
                System.out.println("Cliente solicitando adição de dados dos produtos inexistentes na porta 1094.");

                DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

                outToServer1.writeInt(8);
                if (inFromServer.readBoolean() == true) {
                    ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                    outToServer2.writeObject(pd);
                    this.erro = inFromServer.readUTF();
                }
                outToServer1.close();
                clientSocket.close();
            } catch (Exception e) {
                this.erro = "Impossível fazer a adição dos dados de produtos inexistentes!";
            }
        } else {
            try (Socket clientSocket = new Socket(this.endereco, 1094)) {
                System.out.println("Cliente solicitando adição de dados dos produtos existentes na porta 1094.");

                DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

                outToServer1.writeInt(9);
                if (inFromServer.readBoolean() == true) {
                    ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                    outToServer2.writeObject(pd);
                    outToServer2.writeObject(u);
                    this.erro = inFromServer.readUTF();
                }

                outToServer1.close();
                clientSocket.close();
            } catch (Exception e) {
                this.erro = "Impossível fazer a adição dos dados de produtos existentes!";
            }
        }
    }

    public void removerProdutoDados(ProdutoDados pd, Usuario u) {
        if (verificaProdutoDados(pd) == false) {
            this.erro = "Produto com a data de validade especificada não encontrado. Impossível remover.";
        } else {
            try (Socket clientSocket = new Socket(this.endereco, 1094)) {
                System.out.println("Cliente solicitando remoção de dados dos produtos existentes na porta 1094.");

                DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

                outToServer1.writeInt(10);
                if (inFromServer.readBoolean() == true) {
                    ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                    outToServer2.writeObject(pd);
                    outToServer2.writeObject(u);
                    this.erro = inFromServer.readUTF();
                }
                outToServer1.close();
                clientSocket.close();
            } catch (Exception e) {
                this.erro = "Impossível fazer a remoção dos dados de produtos existentes!";
            }
        }
    }

    public int qtdDisp(ProdutoDados pd) {
        int contagem = 0;
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando quantidade de produtos disponíveis na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(11);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                outToServer2.writeObject(pd);
                contagem = inFromServer.readInt();
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            clientSocket.close();
            return contagem;
        } catch (Exception e) {
            this.erro = "Impossível checar a quantidade de produtos disponível!";
            return 0;
        }
    }

    public List<ProdutoDados> preencheCmbValidade(Produto p) {
        List<ProdutoDados> produtos = new ArrayList();
        try (Socket clientSocket = new Socket(this.endereco, 1094)) {
            System.out.println("Cliente solicitando listagem de validades para um produto na porta 1094.");

            DataOutputStream outToServer1 = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

            outToServer1.writeInt(12);
            if (inFromServer.readBoolean() == true) {
                ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromServer2 = new ObjectInputStream(clientSocket.getInputStream());
                outToServer2.writeObject(p);
                produtos = (List<ProdutoDados>) inFromServer2.readObject();
                this.erro = inFromServer.readUTF();
            }

            outToServer1.close();
            clientSocket.close();
            return produtos;
        } catch (Exception e) {
            this.erro = "Impossível fazer a listagem de validades!";
            return null;
        }
    }
}
