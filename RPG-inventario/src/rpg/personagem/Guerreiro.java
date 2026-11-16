package rpg.personagem;

/** Combatente resistente: muita vida e dano físico moderado. */
public class Guerreiro extends ClassePersonagem {

    public Guerreiro() {
        super("Guerreiro", 120);
    }

    @Override
    public int atacar() {
        return 15;
    }
}
