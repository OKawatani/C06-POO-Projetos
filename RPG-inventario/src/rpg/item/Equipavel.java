package rpg.item;

import rpg.personagem.Personagem;

public interface Equipavel {

    /** Aplica os status do item no personagem. */
    void equipar(Personagem personagem);
}
