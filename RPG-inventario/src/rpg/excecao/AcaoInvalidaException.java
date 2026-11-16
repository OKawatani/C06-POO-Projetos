package rpg.excecao;

/** Lançada quando uma regra do jogo impede a ação */
public class AcaoInvalidaException extends RPGException {

    public AcaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
