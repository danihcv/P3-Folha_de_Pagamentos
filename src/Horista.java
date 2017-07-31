import java.util.*;


public class Horista extends Empregado {
    private List<DataEHora> pontos = new LinkedList<DataEHora>();

    public Horista(int id, String cpf, String name, String address, float salarioHora, String metodo) {
        super(id, cpf, name, address, salarioHora, metodo);
        this.setType("horista");
        this.setAgenda("bi-semanal");
    }

    public void baterPonto(DataEHora ponto){
        this.pontos.add(ponto);
    }

    public DataEHora getPontoAtIdx(int idx){
        return this.pontos.get(idx);
    }

    public DataEHora getUltimoPonto(){
    	return this.pontos.get(this.pontos.size()-1);
    }

    public int getTotalDePontosBatidos(){
    	return this.pontos.size();
    }

    @Override
    public boolean fazerPagamento(DataEHora dataAtual){
        return false;
    }
}