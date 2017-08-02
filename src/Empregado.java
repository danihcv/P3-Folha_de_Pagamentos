import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Empregado {
    private String type;
    private int id;
    private String cpf;
    private String name;
    private String address;
    private float salario;
    private String metodoDePagamento;
    private boolean sindicalista;
    private int sindicatoID = -1;
    private float sindicatoTaxa = 0;
    private Date ultimoPagamento = null;
    private String agenda;
    private Date agendaRef;
    private String agendaDia = "";

    public Empregado(int id, String cpf, String name, String address, float salario, String metodo) {
        this.type = "assalariado";
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.salario = salario;
        this.metodoDePagamento = metodo;
        this.agenda = "mensal";
        this.agendaRef = new Date((long)30*24*60*60*1000);
    }

    public Empregado(Empregado emp){
        this.type = emp.getType();
        this.id = emp.getId();
        this.cpf = emp.getCpf();
        this.name = emp.getName();
        this.address = emp.getAddress();
        this.salario = emp.getSalario();
        this.metodoDePagamento = emp.getMetodoDePagamento();
        this.sindicalista = emp.isSindicalista();
        this.sindicatoID = emp.getSindicatoID();
        this.sindicatoTaxa = emp.getSindicatoTaxa();
        this.ultimoPagamento = emp.getUltimoPagamento();
        this.agenda = emp.getAgenda();
    }

    public String getAgendaDia() {
        return agendaDia;
    }

    public void setAgendaDia(String agendaDia) {
        this.agendaDia = agendaDia;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public Date getAgendaRef() {
        return agendaRef;
    }

    public void setAgendaRef(Date agendaRef) {
        this.agendaRef = agendaRef;
    }

    public String getCpf() {
        return cpf;
    }

    public int getId() {
        return id;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public String getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(String metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }

    public boolean isSindicalista() {
        return sindicalista;
    }

    public void setSindicalista(boolean sindicalista) {
        this.sindicalista = sindicalista;
    }

    public int getSindicatoID() {
        return sindicatoID;
    }

    public void setSindicatoID(int sindicatoID) {
        this.sindicatoID = sindicatoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String address) {
        this.address = address;
    }

    public Date getUltimoPagamento() {
        return ultimoPagamento;
    }

    public void setUltimoPagamento(Date ultimoPagamento) {
        this.ultimoPagamento = ultimoPagamento;
    }

    public float getSindicatoTaxa() {
        return sindicatoTaxa;
    }

    public void setSindicatoTaxa(float sindicatoTaxa) {
        this.sindicatoTaxa = sindicatoTaxa;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return "\t-ID: "+ this.id +
                "\n\t-Tipo: "+ this.type +
                "\n\t-CPF: "+ this.cpf +
                "\n\t-Nome: "+ this.name +
                "\n\t-Endereço: "+ this.address +
                "\n\t-Salário: "+ this.salario +
                "\n\t-Método de pagamento: "+ this.metodoDePagamento +
                "\n\t-Agenda de pagamento: "+ this.agenda +
                "\n\t-Último pagamento: "+ this.ultimoPagamento +
                "\n\t-Próximo pagamento: "+ (this.ultimoPagamento == null ? "null" : new Date(this.ultimoPagamento.getTime() + this.agendaRef.getTime()))+
                "\n\t-Sindicalista: "+ this.sindicalista +
                "\n\t-Sindicato ID: "+ this.sindicatoID +
                "\n\t-Sindicato Taxa: "+ this.sindicatoTaxa;
    }
}