import java.util.Date;

public class Assalariado extends Empregado {
    public Assalariado(int id, String cpf, String name, String address, float salario, String metodo) {
        super(id, cpf, name, address, salario, metodo);
    }

    public Assalariado(int id, String cpf, String name, String address, float salario, String metodo, boolean sindicalista,
                       int sindicatoID, float sindicatoTaxa, Date ultimoPagamento, String agenda, Date agendaRef, String agendaDia) {
        super(id, cpf, name, address, salario, metodo, sindicalista, sindicatoID, sindicatoTaxa, ultimoPagamento, agenda, agendaRef, agendaDia);
    }

    public Assalariado(Empregado emp) {
        super(emp);
    }
}
