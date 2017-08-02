import java.util.*;

public class Venda {
    private float valor;
    private Date data;

    public Venda(float valor, Date data) {
        this.valor = valor;
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

}