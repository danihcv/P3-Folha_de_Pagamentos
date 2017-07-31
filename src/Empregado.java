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
    private DataEHora ultimoPagamento;
    private String agenda;

    public Empregado(int id, String cpf, String name, String address, float salario, String metodo) {
        this.type = "assalariado";
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.salario = salario;
        this.metodoDePagamento = metodo;
        this.agenda = "mensal";
    }

    public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
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

    public boolean fazerPagamento(DataEHora dataAtual){
        switch(dataAtual.getMes()) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                if(dataAtual.getDia() == 31){
                    this.setUltimoPagamento(dataAtual);
                    return true;
                }
                break;
            case 2:
                if(dataAtual.getDia() >= 28){
                    this.setUltimoPagamento(dataAtual);
                    return true;
                }
                break;
            case 4: case 6: case 9: case 11:
                if(dataAtual.getDia() == 30){
                    this.setUltimoPagamento(dataAtual);
                    return true;
                }
                break;
        }
        return false;
    }

    public DataEHora getUltimoPagamento() {
        return ultimoPagamento;
    }

    public void setUltimoPagamento(DataEHora ultimoPagamento) {
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
                "\n\t-Sindicalista: "+ this.sindicalista +
                "\n\t-Sindicato ID: "+ this.sindicatoID +
                "\n\t-Sindicato Taxa: "+ this.sindicatoTaxa;
    }
}