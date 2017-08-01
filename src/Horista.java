import java.util.*;


public class Horista extends Empregado {
    private List<Date> pontos = new LinkedList<>();

    public Horista(int id, String cpf, String name, String address, float salarioHora, String metodo) {
        super(id, cpf, name, address, salarioHora, metodo);
        this.setType("horista");
        this.setAgenda("bi-semanal");
        this.setAgendaRef(new Date(2*7*24*60*60*1000));
    }

    public void baterPonto(Date ponto){
        this.pontos.add(ponto);
    }

    public Date getPontoAtIdx(int idx){
        return this.pontos.get(idx);
    }

    public Date getUltimoPonto(){
    	return this.pontos.get(this.pontos.size()-1);
    }

    public int getTotalDePontosBatidos(){
    	return this.pontos.size();
    }

    @Override
    public boolean fazerPagamento(Date dataAtual){
        return false;
    }
}