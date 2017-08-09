import javax.smartcardio.CommandAPDU;
import java.util.*;

public class Comissionado extends Empregado{
    private List<Venda> vendas = new LinkedList<>();
    private float comissao;

    public Comissionado(int id, String cpf, String name, String address, float salario, float comissao, String metodo) {
        super(id, cpf, name, address, salario, metodo);
        this.comissao = comissao;
        this.setType("comissionado");
        this.setAgenda("bi-semanal");
        this.setAgendaRef(new Date(2*7*24*60*60*1000));
    }

    public Comissionado(int id, String cpf, String name, String address, float salario, String metodo, boolean sindicalista,
                       int sindicatoID, float sindicatoTaxa, Date ultimoPagamento, String agenda, Date agendaRef, String agendaDia, List<Venda> vendas, Float comissao) {
        super(id, cpf, name, address, salario, metodo, sindicalista, sindicatoID, sindicatoTaxa, ultimoPagamento, agenda, agendaRef, agendaDia);
        this.setType("comissionado");
        this.vendas = vendas;
        this.comissao = comissao;
    }

    public Comissionado(Comissionado emp){
        super(emp);
        this.setType("comissionado");
        this.vendas = emp.vendas;
        this.comissao = emp.comissao;
    }

    public void addVenda(float valor, Date data) {
        this.vendas.add(new Venda(valor, data));
    }

    public float getComissao() {
        return comissao;
    }

    public void setComissao(float comissao) {
        this.comissao = comissao;
    }

    public int getTotalVendas(){
        return this.vendas.size();
    }

    public Venda getUltimaVenda(){
        return this.vendas.get(this.vendas.size() - 1);
    }

    public float getValorVendaAtIdx(int i){
        return this.vendas.get(i).getValor();
    }

    public Venda getVendaAtIdx(int i){
        return this.vendas.get(i);
    }

    public void resetVendas(){
        this.vendas = new LinkedList<>();
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n\t-Comissão: "+ this.comissao +"%";
    }
}
