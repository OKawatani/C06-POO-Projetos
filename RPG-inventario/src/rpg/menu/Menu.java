package rpg.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import rpg.excecao.PersistenciaException;
import rpg.excecao.RPGException;
import rpg.item.Equipavel;
import rpg.item.Item;
import rpg.item.Pocao;
import rpg.personagem.ClassePersonagem;
import rpg.personagem.Guerreiro;
import rpg.personagem.Mago;
import rpg.personagem.Personagem;
import rpg.arquivos.RepositorioItemArquivo;

public class Menu {

    private Scanner teclado = new Scanner(System.in);
    private RepositorioItemArquivo repositorio;
    private Personagem personagem;

    public Menu(RepositorioItemArquivo repositorio) {
        this.repositorio = repositorio;
    }



    /** Metodos para entrada de dados */

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return teclado.nextLine().trim();
    }

    /** Lê um número inteiro; se o usuário digitar outra coisa, avisa e pergunta de novo. */
    private int lerInteiro(String mensagem) {
        while (true) {
            String linha = lerTexto(mensagem);
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro.");
            }
        }
    }


    /** Executa o jogo: cria o personagem, entrega os itens do arquivo e abre o menu principal. */
    public void iniciar() {
        try {
            System.out.println("=== RPG: Gerenciador de Itens ===");
            personagem = criarPersonagem();
            carregarItens();
            menuPrincipal();
        } catch (NoSuchElementException e) {
            System.out.println("\nEntrada encerrada.");
        }
    }


    /** Metodo auxiliar do Menu que coleta os dados do usuário e depois chama o construtor */
    private Personagem criarPersonagem() {
        String nome = lerTexto("Nome do personagem: ");
        if (nome.isEmpty()) {
            nome = "Aventureiro";
        }

        int opcao = 0;
        while (opcao != 1 && opcao != 2) {
            System.out.println("Classe: 1) Guerreiro  2) Mago");
            opcao = lerInteiro("Escolha: ");
        }

        ClassePersonagem classe;
        if (opcao == 1) {
            classe = new Guerreiro();
        } else {
            classe = new Mago();
        }
        return new Personagem(nome, classe); //Chamando o construtor da classe personagem depois de receber os dados
    }


    /** Se o arquivo falhar, o jogo segue sem itens*/
    private void carregarItens() {
        try {
            List<Item> itens = repositorio.carregar();
            for (Item item : itens) {
                personagem.receberItem(item);
            }
            System.out.println(itens.size() + " itens carregados.");
        } catch (PersistenciaException e) {
            System.out.println("Nao foi possivel carregar os itens: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------- menu

    private void menuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Menu ---");
            System.out.println("1) Ver ficha e itens");
            System.out.println("2) Equipar item");
            System.out.println("3) Usar pocao");
            System.out.println("4) Atacar");
            System.out.println("5) Sofrer ataque inimigo (20 de dano)");
            System.out.println("0) Sair");
            opcao = lerInteiro("Escolha: ");

            try {
                switch (opcao) {
                    case 1:
                        mostrarFicha();
                        break;
                    case 2:
                        equipar();
                        break;
                    case 3:
                        usarPocao();
                        break;
                    case 4:
                        System.out.println(personagem.getNome() + " ataca causando "
                                + personagem.atacar() + " de dano!");
                        break;
                    case 5:
                        sofrerAtaque();
                        break;
                    case 0:
                        System.out.println("Ate a proxima!");
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (RPGException e) {
                // Qualquer regra violada vira uma mensagem, sem derrubar o programa.
                System.out.println("Nao foi possivel: " + e.getMessage());
            }
        }
    }

    private void mostrarFicha() {
        System.out.println("\n" + personagem.getNome() + " - " + personagem.getClasse().getNome());
        System.out.println("Vida: " + personagem.getVidaAtual() + "/" + personagem.getClasse().getVidaBase() + " | Ataque: " + personagem.atacar() + " | Defesa: " + personagem.getDefesa());
        List<Item> itens = personagem.getItens();
        if (itens.size() == 0) {
            System.out.println("Sem itens.");
        } else {
            System.out.println("Itens:");
            for (Item item : itens) {
                System.out.println("  - " + descricaoComEstado(item));
            }
        }
    }

    private void equipar() throws RPGException {
        List<Item> equipaveis = new ArrayList<>();
        for (Item item : personagem.getItens()) {
            if (item instanceof Equipavel) {
                equipaveis.add(item);
                System.out.println("  " + equipaveis.size() + ") " + descricaoComEstado(item));
            }
        }
        if (equipaveis.size() == 0) {
            System.out.println("Voce nao tem nada para equipar.");
            return;
        }
        int escolha = lerInteiro("Equipar (0 para voltar): ");
        if (escolha == 0) {
            return;
        }
        if (escolha < 1 || escolha > equipaveis.size()) {
            System.out.println("Opcao invalida.");
            return;
        }
        personagem.equipar((Equipavel) equipaveis.get(escolha - 1));
        System.out.println("Item equipado! Ataque: " + personagem.atacar() + " | Defesa: " + personagem.getDefesa());
    }

    private void usarPocao() throws RPGException {
        List<Pocao> pocoes = new ArrayList<>();
        for (Item item : personagem.getItens()) {
            if (item instanceof Pocao) {
                pocoes.add((Pocao) item);
                System.out.println("  " + pocoes.size() + ") " + item.descricao());
            }
        }
        if (pocoes.size() == 0) {
            System.out.println("Voce nao tem pocoes.");
            return;
        }
        int escolha = lerInteiro("Usar (0 para voltar): ");
        if (escolha == 0) {
            return;
        }
        if (escolha < 1 || escolha > pocoes.size()) {
            System.out.println("Opcao invalida.");
            return;
        }
        Pocao pocao = pocoes.get(escolha - 1);
        personagem.usar(pocao);
        System.out.println("Voce usou " + pocao.getNome() + ". Vida: " + personagem.getVidaAtual() + "/" + personagem.getClasse().getVidaBase());
    }

    private void sofrerAtaque() {
        int antes = personagem.getVidaAtual();
        personagem.receberDano(20);
        System.out.println("Um inimigo ataca! Defesa " + personagem.getDefesa() + " reduz o golpe. Vida: " + antes + " -> " + personagem.getVidaAtual());
    }

    private String descricaoComEstado(Item item) {
        if (personagem.estaEquipado(item)) {
            return item.descricao() + " [equipado]";
        }
        return item.descricao();
    }

}
