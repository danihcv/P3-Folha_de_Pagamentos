public class Assalariado extends Empregado {
    public Assalariado(int id, String cpf, String name, String address, float salario, String metodo) {
        super(id, cpf, name, address, salario, metodo);
    }

    public Assalariado(Empregado emp) {
        super(emp);
    }
}
