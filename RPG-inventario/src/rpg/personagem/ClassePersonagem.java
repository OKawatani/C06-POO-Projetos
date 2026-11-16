package rpg.personagem;

/** Classe abstrata que define a calsse do personagem (vida base e forma de atacar). */
public abstract class ClassePersonagem {

    protected String nome;
    protected int vidaBase;

    protected ClassePersonagem(String nome, int vidaBase) {
        this.nome = nome;
        this.vidaBase = vidaBase;
    }

    public String getNome() {
        return nome;
    }

    public int getVidaBase() {
        return vidaBase;
    }

    /** Dano base do ataque. Cada classe implementa do seu jeito*/
    public abstract int atacar();
}
