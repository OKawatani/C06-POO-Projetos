package rpg.item;

import rpg.personagem.Personagem;

/** Armadura equipável: ao ser equipada, aumenta a defesa do personagem. */
public class Armadura extends Item implements Equipavel {

    private int defesa;

    public Armadura(String nome, int defesa) {
        super(nome);
        this.defesa = defesa;
    }

    public int getDefesa() {
        return defesa;
    }

    @Override
    public String descricao() {
        return nome + " (armadura, defesa +" + defesa + ")";
    }

    @Override
    public void equipar(Personagem personagem) {
        personagem.aumentarDefesa(defesa);
    }
}
