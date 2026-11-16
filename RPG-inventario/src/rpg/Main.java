package rpg;

import rpg.menu.Menu;
import rpg.arquivos.RepositorioItemArquivo;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu(new RepositorioItemArquivo("itens.txt"));
        menu.iniciar();
    }
}
