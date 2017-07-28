import java.util.*;

public class Sindicato {
	private List<Empregado> sindicalistas = new LinkedList<Empregado>();
	private float taxa;

	public Sindicato(float taxa) {
		this.setTaxa(taxa);
	}

	public float getTaxa() {
		return taxa;
	}

	public void setTaxa(float taxa) {
		this.taxa = taxa;
	}

	public void addSindicalista(Empregado empregado){
		this.sindicalistas.add(empregado);
	}

}
