package rpg.personagem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rpg.item.Item;

public class Inventario {

    private List<Item> itens = new ArrayList<>();

    public void adicionar(Item item) {
        itens.add(item);
    }

    public boolean remover(Item procurado) {
        Iterator<Item> it = itens.iterator();
        while (it.hasNext()) {
            if (it.next() == procurado) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Item> getItens() {
        return new ArrayList<>(itens);
    }
}