package rpg.personagem;

import java.util.ArrayList;
import java.util.List;

import rpg.excecao.AcaoInvalidaException;
import rpg.item.Equipavel;
import rpg.item.Item;
import rpg.item.Pocao;

/** Personagem: tem uma classe, um inventário próprio e atributos de combate. */
public class Personagem {

    private String nome;
    private int vidaAtual;
    private int ataqueExtra;
    private int defesa;
    private ClassePersonagem classe;

    // Composição: o inventário é criado aqui e pertence só a este personagem.
    private Inventario inventario = new Inventario();
    private List<Equipavel> equipados = new ArrayList<>();

    public Personagem(String nome, ClassePersonagem classe) {
        this.nome = nome;
        this.classe = classe;
        this.vidaAtual = classe.getVidaBase();
    }

    public String getNome() {
        return nome;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getDefesa() {
        return defesa;
    }

    public ClassePersonagem getClasse() {
        return classe;
    }

    public void receberItem(Item item) {
        inventario.adicionar(item);
    }

    /** Devolve uma cópia da lista de itens do inventário. */
    public List<Item> getItens() {
        return inventario.getItens();
    }

    public boolean estaEquipado(Item item) {
        for (Equipavel equipavel : equipados) {
            if (equipavel == item) {
                return true;
            }
        }
        return false;
    }

    /** Dano total: ataque da classe mais o bônus das armas equipadas. */
    public int atacar() {
        return classe.atacar() + ataqueExtra;
    }

    /** Dano sofrido é reduzido pela defesa.*/
    public void receberDano(int dano) {
        int danoReal = Math.max(1, dano - defesa);
        vidaAtual = Math.max(0, vidaAtual - danoReal);
    }

    /** Chamado pelas armas ao serem equipadas. */
    public void aumentarAtaque(int valor) {
        ataqueExtra += valor;
    }

    /** Chamado pelas armaduras ao serem equipadas. */
    public void aumentarDefesa(int valor) {
        defesa += valor;
    }


    public void equipar(Equipavel equipavel) throws AcaoInvalidaException {
        for (Equipavel ja : equipados) {
            if (ja == equipavel) {
                throw new AcaoInvalidaException("Este item ja esta equipado.");
            }
        }
        equipavel.equipar(this);
        equipados.add(equipavel);
    }

    /** Consome uma poção do inventário e recupera vida*/
    public void usar(Pocao pocao) throws AcaoInvalidaException {
        if (vidaAtual >= classe.getVidaBase()) {
            throw new AcaoInvalidaException("Sua vida ja esta cheia.");
        }
        if (!inventario.remover(pocao)) {
            throw new AcaoInvalidaException("Voce nao possui " + pocao.getNome() + ".");
        }
        vidaAtual = Math.min(classe.getVidaBase(), vidaAtual + pocao.getCura());
    }
}
