import java.util.*;


public class Horista extends Empregado {
	private List<DataEHora> pontosEntrada = new LinkedList<DataEHora>();
	private List<DataEHora> pontosSaida = new LinkedList<DataEHora>();

	public Horista(int id, int cpf, String name, String address, float salarioHora, String metodo) {
		super(id, cpf, name, address, salarioHora, metodo);
	}

	public void addPontoEntrada(DataEHora ponto){
		this.pontosEntrada.add(ponto);
	}

	public DataEHora getPontoEntradaAtIdx(int idx){
		return this.pontosEntrada.get(idx);
	}

	public void addPontoSaida(DataEHora ponto){
		this.pontosSaida.add(ponto);
	}

	public DataEHora getPontoSaidaAtIdx(int idx){
		return this.pontosSaida.get(idx);
	}

	@Override
	public boolean fazerPagamento(DataEHora dataAtual){
		return false;
	}
}
