package rpg.arquivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rpg.excecao.PersistenciaException;
import rpg.item.Arma;
import rpg.item.Armadura;
import rpg.item.Item;
import rpg.item.Pocao;

public class RepositorioItemArquivo {

    private static final String SEPARADOR = ";";

    private String caminho;

    public RepositorioItemArquivo(String caminho) {
        this.caminho = caminho;
    }

    public List<Item> carregar() throws PersistenciaException {
        List<Item> itens = new ArrayList<>();
        int numeroDaLinha = 0;

        try (BufferedReader entrada = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = entrada.readLine()) != null) {
                numeroDaLinha++;
                linha = linha.trim();
                itens.add(lerItem(linha.split(SEPARADOR)));
            }
        } catch (FileNotFoundException e) {
            throw new PersistenciaException("Arquivo " + caminho + " nao encontrado.");
        } catch (IOException e) {
            throw new PersistenciaException("Erro ao ler o arquivo " + caminho
                    + " (" + e.getMessage() + ").");
        } catch (NumberFormatException e) {
            throw new PersistenciaException("Linha " + numeroDaLinha
                    + ": o valor do item precisa ser um numero inteiro.");
        } catch (PersistenciaException e) {
            throw new PersistenciaException("Linha " + numeroDaLinha + ": " + e.getMessage());
        }
        return itens;
    }

    private Item lerItem(String[] partes) throws PersistenciaException {
        if (partes.length != 3) {
            throw new PersistenciaException("use o formato TIPO;nome;valor.");
        }
        String tipo = partes[0].trim();
        String nome = partes[1].trim();
        int valor = Integer.parseInt(partes[2].trim());

        if (nome.isEmpty()) {
            throw new PersistenciaException("o nome do item esta vazio.");
        }
        if (valor <= 0) {
            throw new PersistenciaException("o valor do item deve ser maior que zero.");
        }

        if (tipo.equals("ARMA")) {
            return new Arma(nome, valor);
        }
        if (tipo.equals("ARMADURA")) {
            return new Armadura(nome, valor);
        }
        if (tipo.equals("POCAO")) {
            return new Pocao(nome, valor);
        }
        throw new PersistenciaException("tipo desconhecido: " + tipo + ".");
    }
}
