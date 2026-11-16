package rpg.personagem;

/** Conjurador frágil: pouca vida e dano mágico alto. */
public class Mago extends ClassePersonagem {

    public Mago() {
        super("Mago", 80);
    }

    @Override
    public int atacar() {
        return 20;
    }
}
