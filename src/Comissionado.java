import java.util.*;

public class Comissionado extends Empregado{
	private List<Venda> vendas = new LinkedList<Venda>();


	public Comissionado(int id, String cpf, String name, String address, float salarioComissao, String metodo) {
		super(id, cpf, name, address, salarioComissao, metodo);
	}

	public void addVenda(float valor, DataEHora data) {
		this.vendas.add(new Venda(valor, data));
	}
}
