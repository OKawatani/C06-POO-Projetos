package rpg.item;

import rpg.personagem.Personagem;

/** Arma equipável: ao ser equipada, aumenta o ataque do personagem. */
public class Arma extends Item implements Equipavel {

    private int dano;

    public Arma(String nome, int dano) {
        super(nome);
        this.dano = dano;
    }

    public int getDano() {
        return dano;
    }

    @Override
    public String descricao() {
        return nome + " (arma, dano +" + dano + ")";
    }

    @Override
    public void equipar(Personagem personagem) {
        personagem.aumentarAtaque(dano);
    }
}
