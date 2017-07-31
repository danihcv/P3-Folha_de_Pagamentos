import java.util.*;

public class Comissionado extends Empregado{
    private List<Venda> vendas = new LinkedList<Venda>();
    private float comissao;

    public Comissionado(int id, String cpf, String name, String address, float salario, float comissao, String metodo) {
        super(id, cpf, name, address, salario, metodo);
        this.comissao = comissao;
        this.setType("comissionado");
        this.setAgenda("bi-semanal");
    }

    public void addVenda(float valor, DataEHora data) {
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

    @Override
    public String toString() {
        return super.toString() +
                "\n\t-Comissão: "+ this.comissao +"%";
    }
}
