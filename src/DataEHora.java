
public class DataEHora {
	private int dia;
	private int mes;
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

	private int ano;
	private int hora;
	private int minutos;
	private int segundos;

	public DataEHora(int dia, int mes, int ano, int hora, int minutos, int segundos) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		this.hora = hora;
		this.minutos = minutos;
		this.segundos = segundos;
	}

	public DataEHora distancia(DataEHora ponto){
		int segsTotal = ponto.getDataInSeconds();
		segsTotal -= this.getDataInSeconds();
		int resAno = segsTotal%(60*60*60*60*60);
		segsTotal -= resAno*60*60*60*60*60;
		int resMes = segsTotal%(60*60*60*60);
		segsTotal -= resMes*60*60*60*60;
		int resDia = segsTotal%(60*60*60);
		segsTotal -= resDia*60*60*60;
		int resHora = segsTotal%(60*60);
		segsTotal -= resHora*60*60;
		int resMinutos = segsTotal%(60);
		segsTotal -= resMinutos*60;
		int resSegs = segsTotal;
		return new DataEHora(resDia, resMes, resAno, resHora, resMinutos, resSegs);
	}

	public int getDataInSeconds(){
		return this.segundos + this.minutos*60 + this.hora*60*60 + this.dia*60*60*60 + this.mes*60*60*60*60 + this.ano*60*60*60*60*60;
	}
}