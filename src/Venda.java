
public class Venda {
	private float valor;
	private DataEHora data;

	public Venda(float valor, DataEHora data) {
		this.valor = valor;
		this.data = data;
	}

	public float getValor() {
		return valor;
	}

	public DataEHora getData() {
		return data;
	}

}
