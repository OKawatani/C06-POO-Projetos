package rpg.excecao;

/** Exceção base: todas as exceções próprias herdam desta classe. */
public class RPGException extends Exception {

    public RPGException(String mensagem) {
        super(mensagem);
    }
}
