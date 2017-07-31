import java.util.*;

public class Sindicato {
    private int currentID = 1;
    private List<Empregado> sindicalistas = new LinkedList<Empregado>();

    public Sindicato() { }

    public void addSindicalista(Empregado empregado){
        this.sindicalistas.add(empregado);
    }

    public  void removeSindicalista(String cpf){
        int idx = -1;
        for(int i = 0; i < sindicalistas.size() && idx == -1; i++){
            if(sindicalistas.get(i).getCpf().equals(cpf))
                idx = i;
        }
        if(idx != -1)
            sindicalistas.remove(idx);
    }

    public int getCurrentID() {
        return currentID;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }

    public int getCurrentSindicalistsQuantity(){
        return sindicalistas.size();
    }
}