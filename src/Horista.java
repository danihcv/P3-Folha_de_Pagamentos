import java.util.*;


public class Horista extends Empregado {
    private List<String> pontosHoras = new LinkedList<>();
    private Date ultimoPonto = null;

    public Horista(int id, String cpf, String name, String address, float salarioHora, String metodo) {
        super(id, cpf, name, address, salarioHora, metodo);
        this.setType("horista");
        this.setAgenda("bi-semanal");
        this.setAgendaRef(new Date(2*7*24*60*60*1000));
    }

    public Horista(Horista emp){
        super(emp);
        this.pontosHoras = emp.pontosHoras;
    }

    public void baterPonto(int horas){
        this.pontosHoras.add(Integer.toString(horas));
    }

    public int getHorasAtIdx(int idx){
        return Integer.parseInt(this.pontosHoras.get(idx));
    }

    public Date getUltimoPonto(){
        return this.ultimoPonto;
    }

    public void setUltimoPonto(Date ponto){
        this.ultimoPonto = ponto;
    }

    public int getTotalDePontosBatidos(){
        return this.pontosHoras.size();
    }

    public void resetPontos(){
        this.pontosHoras = new LinkedList<>();
    }
}