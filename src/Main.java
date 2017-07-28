import java.util.*;

public class Main {
	static Scanner scan = new Scanner(System.in);
	static int qntdTotalEmpregados = 0;

	static private List<Empregado> assalariados = new LinkedList<Empregado>();
	static private List<Horista> horistas = new LinkedList<Horista>();
	static private List<Comissionado> comissionados = new LinkedList<Comissionado>();

	public static void main(String[] args) {
		boolean menu = true;
		do{
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("\t\tFOLHA DE PAGAMENTOS");
			System.out.println("\n-Selecione uma opção:");
			System.out.println("1. Adicionar um empregado");
			System.out.println("2. Remover um empregado");
			if(horistas.size() > 0)
				System.out.println(horistas.get(0).getName());
			int sel = scan.nextInt();
			scan.nextLine();
			switch(sel) {
				case 1: addEmpregado();
					break;
				case 2: removerEmpregado();
					break;
				case 3: editarEmpregado();
				default: menu = false;
					break;
			}
		}while(menu);
	}

	public static void addEmpregado() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("\tADIÇÃO DE EMPREGADO\n");

		System.out.print("-CPF: ");
		String cpf = scan.nextLine();
		System.out.print("-Nome: ");
		String name = scan.nextLine();
		System.out.print("-Endereço: ");
		String address = scan.nextLine();
		System.out.println("\n-Selecione o tipo: ");
		System.out.println("1. Horista");
		System.out.println("2. Assalariado");
		System.out.println("3. Comissionado");
		int tipo;
		do{
			tipo = scan.nextInt();
			scan.nextLine();
		}while(tipo <= 0 || tipo > 3);
		switch(tipo){
			case 1:
				System.out.println("-Salário por hora: ");
				break;
			case 2:
				System.out.println("-Salário por mês: ");
				break;
			case 3:
				System.out.println("-Comissão (%): ");
				break;
		}
		int salario = scan.nextInt();
		scan.nextLine();

		int sel;
		do{
			System.out.println("\n-Selecione um método de pagamento: ");
			System.out.println("1. Cheque pelos correios");
			System.out.println("2. Cheque em mãos");
			System.out.println("3. Depósito em conta");
			sel = scan.nextInt();
			scan.nextLine();
		}while(sel <= 0 || sel > 3);

		String metodoDePagamento = "";
		switch(sel){
			case 1: metodoDePagamento = "cheque-correios";
				break;
			case 2: metodoDePagamento = "cheque-maos";
				break;
			case 3: metodoDePagamento = "deposito";
				break;
		}

		switch(tipo){
			case 1: horistas.add(new Horista(++qntdTotalEmpregados, cpf, name, address, salario, metodoDePagamento));
				break;
			case 2: assalariados.add(new Empregado(++qntdTotalEmpregados, cpf, name, address, salario, metodoDePagamento));
				break;
			case 3: comissionados.add(new Comissionado(++qntdTotalEmpregados, cpf, name, address, salario, metodoDePagamento));
				break;
		}
	}

	public static void removerEmpregado(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("\tREMOÇÃO DE EMPREGADO\n");
		System.out.println("-Selecione o tipo:");
		int sel;
		do{
			System.out.println("1. Horista");
			System.out.println("2. Assalariado");
			System.out.println("3. Comissionado");
			sel = scan.nextInt();
			scan.nextLine();
		}while(sel <= 0 || sel > 3);

		System.out.print("\n-CPF: ");
		String cpf = scan.nextLine();
		int idx = -1;
		switch(sel){
			case 1:
				int len = horistas.size();
				for(int i = 0; i < len && idx == -1; i++){
					if(horistas.get(i).getCpf().equals(cpf))
						idx = i;
				}
				if(idx != -1)
					horistas.remove(idx);
				break;
			case 2:
				len = assalariados.size();
				for(int i = 0; i < len && idx == -1; i++){
					if(assalariados.get(i).getCpf().equals(cpf))
						idx = i;
				}
				if(idx != -1)
					assalariados.remove(idx);
				break;
			case 3:
				len = comissionados.size();
				for(int i = 0; i < len && idx == -1; i++){
					if(comissionados.get(i).getCpf().equals(cpf))
						idx = i;
				}
				if(idx != -1)
					comissionados.remove(idx);
				break;
		}
	}

	public static void editarEmpregado(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("\tEDITAR EMPREGADO\n");
		System.out.println("-Selecione o tipo:");
		int sel;
		do{
			System.out.println("1. Horista");
			System.out.println("2. Assalariado");
			System.out.println("3. Comissionado");
			sel = scan.nextInt();
			scan.nextLine();
		}while(sel <= 0 || sel > 3);
	}
}
