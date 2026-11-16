package rpg.excecao;

/** Lançada quando o arquivo de itens não pode ser lido */
public class PersistenciaException extends RPGException {

    public PersistenciaException(String mensagem) {
        super(mensagem);
    }
}
