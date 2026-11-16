package rpg.item;


public class Pocao extends Item {

    private int cura;

    public Pocao(String nome, int cura) {
        super(nome);
        this.cura = cura;
    }

    public int getCura() {
        return cura;
    }

    @Override
    public String descricao() {
        return nome + " (pocao, cura " + cura + ")";
    }
}
