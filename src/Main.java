import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static int currentID = 0;
    static Stack<Empregado> pilhaEmpregados = new Stack<Empregado>();
    static Stack<String> pilhaActions = new Stack<String>();
/*
    static private List<Empregado> assalariados = new LinkedList<Empregado>();
    static private List<Horista> horistas = new LinkedList<Horista>();
    static private List<Comissionado> comissionados = new LinkedList<Comissionado>();
    */
    static private List<Empregado> empregados = new LinkedList<Empregado>();
    static private Sindicato sindicato = new Sindicato();

    public static void main(String[] args) {
        boolean menu = true;
        do{
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("\t\tFOLHA DE PAGAMENTOS");
            System.out.println("\n-Selecione uma opção:");
            System.out.println("1. Adicionar um empregado");
            System.out.println("2. Remover um empregado");
            System.out.println("3. Editar um empregado");
            System.out.println("4. Bater ponto");
            System.out.println("5. Lançar uma venda");
            System.out.println("6. Lançar uma taxa de serviço");
            System.out.println("7. Atualizar agenda de pagamento");
            System.out.println("8. Undo");
            System.out.println("10. Mostrar todos os empregados");

            int sel = scan.nextInt();
            scan.nextLine();
            switch(sel) {
                case 1: addEmpregado();
                    break;
                case 2: removerEmpregado();
                    break;
                case 3: editarEmpregado();
                    break;
                case 4: baterPonto();
                	break;
                case 5: fazerVenda();
                	break;
                case 6: atualizarTaxaSindical();
                	break;
                case 7: atualizarAgendaPagamento();
                	break;
                case 8: undo();
                	break;
                case 10: mostrarEmpregados();
                    break;
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
        float comissao = 0;
        do{
            tipo = scan.nextInt();
            scan.nextLine();
        }while(tipo <= 0 || tipo > 3);
        switch(tipo){
            case 1:
                System.out.print("-Salário por hora: ");
                break;
            case 2:
                System.out.print("-Salário por mês: ");
                break;
            case 3:
                System.out.print("-Comissão (%): ");
                comissao = scan.nextFloat();
                scan.nextLine();
                System.out.print("-Salário por semana: ");
                break;
        }
        float salario = scan.nextFloat();
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
            case 1: empregados.add(new Horista(++currentID, cpf, name, address, salario, metodoDePagamento));
                break;
            case 2: empregados.add(new Empregado(++currentID, cpf, name, address, salario, metodoDePagamento));
                break;
            case 3: empregados.add(new Comissionado(++currentID, cpf, name, address, salario, comissao, metodoDePagamento));
                break;
        }
    }

    public static void removerEmpregado(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tREMOÇÃO DE EMPREGADO\n");
/*
        System.out.println("-Selecione o tipo:");
        int sel;
        do{
            System.out.println("1. Horista");
            System.out.println("2. Assalariado");
            System.out.println("3. Comissionado");
            sel = scan.nextInt();
            scan.nextLine();
        }while(sel <= 0 || sel > 3);
*/
        System.out.print("\n-Digite o CPF: ");
        String cpf = scan.nextLine();
        int idx = -1;
        int len = empregados.size();
        for(int i = 0; i < len && idx == -1; i++){
            if(empregados.get(i).getCpf().equals(cpf))
                idx = i;
        }
        if(idx != -1)
            empregados.remove(idx);
    }

    public static void editarEmpregado(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tEDITAR EMPREGADO");
    /*
        int tipo;
        do{
            System.out.println("\n-Selecione o tipo:");
            System.out.println("1. Horista");
            System.out.println("2. Assalariado");
            System.out.println("3. Comissionado");
            tipo = scan.nextInt();
            scan.nextLine();
        }while(tipo <= 0 || tipo > 3);
	*/
        String cpf;
        Empregado emp = null;
        int idxEmp = -1;
        do {
            System.out.print("\n-Informe o CPF do empregado: ");
            cpf = scan.nextLine();
            if(cpf.equals("-1"))
                return;
            for(int i = 0; i < empregados.size() && idxEmp == -1; i++) {
                if(empregados.get(i).getCpf().equals(cpf)) {
                    idxEmp = i;
                    emp = empregados.get(i);
                }
            }
        }while(idxEmp == -1);

        int sel;
        do{
            System.out.println("\n-Selecione o que deseja alterar:");
            System.out.println("1. Nome");
            System.out.println("2. Endereço");
            System.out.println("3. Tipo");
            System.out.println("4. Método de pagamento");
            System.out.println("5. Participação no sindicato");
            System.out.println("6. Identificação no sindicato");
            System.out.println("7. Taxa sindical");
            sel = scan.nextInt();
            scan.nextLine();
        }while(sel <= 0 || sel > 6);

        String newName, newAddress, newMetodoDePagamento;
        float newTaxa;
        switch (sel) {
            case 1:
                System.out.println("\nALTERAR NOME\n");
                System.out.printf("-Nome atual: %s\n", emp.getName());
                System.out.print("-Digite o novo nome: ");
                newName = scan.nextLine();
                empregados.get(idxEmp).setName(newName);
                break;
            case 2:
                System.out.println("\nALTERAR ENDEREÇO\n");
                System.out.printf("-Endereço atual: %s\n", emp.getAddress());
                System.out.print("-Digite o novo endereço: ");
                newAddress = scan.nextLine();
                empregados.get(idxEmp).setAdress(newAddress);
                break;
            case 3:
                System.out.println("\nALTERAR TIPO\n");
                System.out.printf("-Tipo atual: %s", emp.getType());
                int newType;
                do {
                    System.out.println("\n-Selecione o novo tipo:");
                    System.out.println("1. Horista");
                    System.out.println("2. Assalariado");
                    System.out.println("3. Comissionado");
                    newType = scan.nextInt();
                    scan.nextLine();
                }while (newType <= 0 || newType > 3);
                if(newType == 1)
                	empregados.add(new Horista(emp.getId(), emp.getCpf(), emp.getName(), emp.getAddress(), emp.getSalario(), emp.getMetodoDePagamento()));
                else if(newType == 2)
                	empregados.add(new Empregado(emp.getId(), emp.getCpf(), emp.getName(), emp.getAddress(), emp.getSalario(), emp.getMetodoDePagamento()));
                else{
                    System.out.println("-Digite a comissão (%): ");
                    float comissao = scan.nextFloat();
                    scan.nextLine();
                    empregados.add(new Comissionado(emp.getId(), emp.getCpf(), emp.getName(), emp.getAddress(), emp.getSalario(), comissao, emp.getMetodoDePagamento()));
                }
                empregados.remove(idxEmp);
                break;
            case 4:
                System.out.println("\nALTERAR MÉTODO DE PAGAMENTO\n");
                System.out.printf("-Método atual: %s\n", emp.getMetodoDePagamento());
                int met;
                do {
                    System.out.println("\n-Selecione o novo método de pagamento");
                    System.out.println("1. Cheque pelos correios");
                    System.out.println("2. Cheque em mãos");
                    System.out.println("3. Depósito em conta");
                    met = scan.nextInt();
                    scan.nextLine();
                }while(met <= 0 || met > 3);
                if(met == 1)
                    newMetodoDePagamento = "cheque-correios";
                else if(met == 2)
                    newMetodoDePagamento = "cheque-maos";
                else
                    newMetodoDePagamento = "deposito";
                empregados.get(idxEmp).setMetodoDePagamento(newMetodoDePagamento);
                break;
            case 5:
                System.out.println("\nALTERAR PARTICIPAÇÃO NO SINDICATO\n");
                System.out.printf("-O empregado atualmente %sparticipa do sindicato.\n", emp.isSindicalista() ? "" : "não ");
                String res;
                do {
                    System.out.printf("\nVocê deseja alterar o status do empregado para '%ssindicalista'? (s | n) ", emp.isSindicalista() ? "não " : "");
                    res = scan.nextLine();
                }while(!res.equals("s") && !res.equals("n"));

                if(res.equals("s") && !emp.isSindicalista()){
                    System.out.print("-Digite a taxa sindical: ");
                    Float taxa = scan.nextFloat();
                    scan.nextLine();

                	empregados.get(idxEmp).setSindicalista(true);
                	empregados.get(idxEmp).setSindicatoTaxa(taxa);
                	empregados.get(idxEmp).setSindicatoID(sindicato.getCurrentID());
                    sindicato.addSindicalista(empregados.get(idxEmp));
                    sindicato.setCurrentID(sindicato.getCurrentID() + 1);
                } else if(res.equals("s")) {
                	empregados.get(idxEmp).setSindicalista(false);
                	empregados.get(idxEmp).setSindicatoTaxa(0);
                	empregados.get(idxEmp).setSindicatoID(-1);
                    sindicato.removeSindicalista(cpf);
                }
                break;
            case 6:
            	System.out.println("\nALTERAR IDENTIFICAÇÃO NO SINDICATO\n");
            	System.out.printf("• O ID atual do empregado no sindicato é %d\n", emp.getSindicatoID());
            	System.out.print("-Insira a nova identificação desejada (int): ");
            	int newID = scan.nextInt();
            	scan.nextLine();
            	empregados.get(idxEmp).setSindicatoID(newID);
            	sindicato.setCurrentID(newID + 1);
            	break;
            case 7:
                System.out.println("\nALTERAR TAXA SINDICAL\n");
                System.out.printf("-Taxa atual: %f\n", emp.getSindicatoTaxa());
                System.out.print("-Digite a nova taxa: ");
                newTaxa = scan.nextFloat();
                scan.nextLine();
                empregados.get(idxEmp).setSindicatoTaxa(newTaxa);
                break;
        }
    }

    public static void baterPonto(){
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    	System.out.println("\tBATER PONTO\n");

    	String cpf;
    	int idxEmp = -1;
    	do{
    		System.out.print("\n-Digite o CPF do horista: ");
    		cpf = scan.nextLine();
    		if(cpf.equals("-1"))
    			return;
    		for(int i = 0; i < empregados.size() && idxEmp == -1; i++){
    			if(empregados.get(i).getCpf().equals(cpf) && empregados.get(i).getType().equals("horista"))
    				idxEmp = i;
    		}
    	}while(idxEmp == -1);

    	int dia, mes, ano, hora, minuto, segundo;
    	System.out.print("-Digite o dia atual: ");
    	dia = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite o mês atual: ");
    	mes = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite o ano atual: ");
    	ano = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite a hora atual: ");
    	hora = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite os minutos atuais: ");
    	minuto = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite os segundos atuais: ");
    	segundo = scan.nextInt();
    	scan.nextLine();

    	((Horista)empregados.get(idxEmp)).baterPonto(new DataEHora(dia, mes, ano, hora, minuto, segundo));
    }

    public static void fazerVenda(){
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\nFAZER UMA VENDA\n");

        String cpf;
    	int idxEmp = -1;
    	do{
    		System.out.print("\n-Digite o CPF do comissionado: ");
    		cpf = scan.nextLine();
    		if(cpf.equals("-1"))
    			return;
    		for(int i = 0; i < empregados.size() && idxEmp == -1; i++){
    			if(empregados.get(i).getCpf().equals(cpf) && empregados.get(i).getType().equals("comissionado"))
    				idxEmp = i;
    		}
    	}while(idxEmp == -1);

    	float valor;
    	System.out.print("-Informe o valor da venda: ");
    	valor = scan.nextFloat();
    	scan.nextLine();

    	int dia, mes, ano, hora, minuto, segundo;
    	System.out.print("-Digite o dia da venda: ");
    	dia = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite o mês da venda: ");
    	mes = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite o ano da venda: ");
    	ano = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite a hora da venda: ");
    	hora = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite os minutos da venda: ");
    	minuto = scan.nextInt();
    	scan.nextLine();
    	System.out.print("-Digite os segundos da venda: ");
    	segundo = scan.nextInt();
    	scan.nextLine();

    	((Comissionado)empregados.get(idxEmp)).addVenda(valor, new DataEHora(dia, mes, ano, hora, minuto, segundo));
    }

    public static void atualizarTaxaSindical(){
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\nATUALIZAR TAXA SINDICAL\n");

        String cpf;
    	int idxEmp = -1;
    	do{
    		System.out.print("\n-Digite o CPF do empregado: ");
    		cpf = scan.nextLine();
    		if(cpf.equals("-1"))
    			return;
    		for(int i = 0; i < empregados.size() && idxEmp == -1; i++){
    			if(empregados.get(i).getCpf().equals(cpf) && empregados.get(i).isSindicalista())
    				idxEmp = i;
    		}
    		if(idxEmp == -1)
    			System.out.println(">>Não existe nenhum empregado com este CPF associado ao sindicato!");
    	}while(idxEmp == -1);

    	System.out.print("-Digite a nova taxa: ");
    	float newTaxa = scan.nextFloat();
    	scan.nextLine();

    	empregados.get(idxEmp).setSindicatoTaxa(newTaxa);
    }

    public static void atualizarAgendaPagamento(){
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tESCOLHER NOVA AGENDA\n");

        String cpf;
    	int idxEmp = -1;
    	do{
    		System.out.print("\n-Digite o CPF do empregado: ");
    		cpf = scan.nextLine();
    		if(cpf.equals("-1"))
    			return;
    		for(int i = 0; i < empregados.size() && idxEmp == -1; i++){
    			if(empregados.get(i).getCpf().equals(cpf) && empregados.get(i).isSindicalista())
    				idxEmp = i;
    		}
    		if(idxEmp == -1)
    			System.out.println(">>Não existe nenhum empregado com este CPF!");
    	}while(idxEmp == -1);

    	System.out.printf("• A agenda atual é %s.\n", empregados.get(idxEmp).getAgenda());
    	int sel;
        do {
            System.out.println("\n-Selecione a nova agenda de pagamento");
            System.out.println("1. Semanal");
            System.out.println("2. Bi-semanal");
            System.out.println("3. Mensal");
            sel = scan.nextInt();
            scan.nextLine();
        }while(sel <= 0 || sel > 3);

        if(sel == 1) {
        	empregados.get(idxEmp).setAgenda("semanal");
        } else if(sel == 2) {
        	empregados.get(idxEmp).setAgenda("bi-semanal");
        } else {
        	empregados.get(idxEmp).setAgenda("mensal");
        }
    }

    public static void undo(){

    }

    public static void mostrarEmpregados(){
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tMOSTRAR EMPREGADOS\n");
        System.out.println("• Empregados");
        for(int i = 0; i < empregados.size(); i++){
            System.out.println(empregados.get(i));
            if(empregados.get(i).getType().equals("horista")){
            	if(((Horista)empregados.get(i)).getTotalDePontosBatidos() > 0){
            		DataEHora ponto = ((Horista)empregados.get(i)).getUltimoPonto();
            		System.out.printf("\t-Ultimo ponto batido: %02d/%02d/%04d %02d:%02d:%02d\n", ponto.getDia(), ponto.getMes(), ponto.getAno(), ponto.getHora(), ponto.getMinutos(), ponto.getSegundos());
            	}
            } else if(empregados.get(i).getType().equals("comissionado")){
            	if(((Comissionado)empregados.get(i)).getTotalVendas() > 0){
            		Venda venda = ((Comissionado)empregados.get(i)).getUltimaVenda();
            		DataEHora data = venda.getData();
            		System.out.printf("\t-Ultima venda efetuada: R$ %.02f; %02d/%02d/%04d %02d:%02d:%02d\n", venda.getValor(), data.getDia(), data.getMes(), data.getAno(), data.getHora(), data.getMinutos(), data.getSegundos());
            	}
            }
            System.out.println("-------------------------");
        }

        System.out.printf("• Quantidade atual de sindicalistas: %d\n", sindicato.getCurrentSindicalistsQuantity());
        System.out.println("\n\nAPERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }
}