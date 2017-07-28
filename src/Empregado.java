
public class Empregado {
	private int id;
	private int cpf;
	private String name;
	private String adress;
	private float salario;
	private String metodoDePagamento;
	private boolean sindicalista;
	private int sindicatoID;
	private DataEHora ultimoPagamento;

	public Empregado(int id, int cpf, String name, String address, float salario, String metodo) {
		this.id = id;
		this.cpf = cpf;
		this.name = name;
		this.adress = address;
		this.salario = salario;
		this.metodoDePagamento = metodo;
	}

	public int getCpf() {
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
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
}
