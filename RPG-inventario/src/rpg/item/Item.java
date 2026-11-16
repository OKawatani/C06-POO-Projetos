package rpg.item;

/** Classe abstrata base de todos os itens */
public abstract class Item {

    protected String nome;

    protected Item(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /** Cada tipo de item descreve a si mesmo de um jeito. */
    public abstract String descricao();

}
