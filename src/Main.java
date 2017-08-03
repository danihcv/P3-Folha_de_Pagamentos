import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.text.*;

public class Main {
    static DateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm");
    static DateFormat formatDay = new SimpleDateFormat("EEE");
    static DataEHora datOperator = new DataEHora();
    static List<Date> agendaRefsDate = new LinkedList<>();
    static List<String> agendaRefsDia = new LinkedList<>();
    static List<String> agendaRefsString = new LinkedList<>();

    static Gson gson = new Gson();
    static Type typeHor = new TypeToken<LinkedList<Horista>>() {}.getType();
    static Type typeAss = new TypeToken<LinkedList<Empregado>>() {}.getType();
    static Type typeCom = new TypeToken<LinkedList<Comissionado>>() {}.getType();

    static Scanner scan = new Scanner(System.in);
    static int currentID = 0;
    static Stack<String> pilhaUndoAss = new Stack<>();
    static Stack<String> pilhaUndoHor = new Stack<>();
    static Stack<String> pilhaUndoCom = new Stack<>();
    static Stack<String> pilhaRedoAss = new Stack<>();
    static Stack<String> pilhaRedoHor = new Stack<>();
    static Stack<String> pilhaRedoCom = new Stack<>();

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
            System.out.println("9. Redo");
            System.out.println("10. Rodar folha de pagamentos");
            System.out.println("11. Criar nova agenda de pagamento");
            System.out.println("12. Mostrar todos os empregados");
            System.out.println("-1. Sair");

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
                case 9: redo();
                    break;
                case 10: rodarFolha();
                    break;
                case 11: criarNovaAgenda();
                    break;
                case 12: mostrarEmpregados();
                    break;
                case -1: menu = false;
                    break;
            }
        }while(menu);
    }

    public static void addEmpregado() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tADIÇÃO DE EMPREGADO\n");

        String cpf;
        do {
            System.out.print("-CPF: ");
             cpf = scan.nextLine();
             for(Empregado emp: empregados){
                 if(emp.getCpf().equals(cpf)) {
                     System.out.println(">>CPF JÁ CADASTRADO!\n");
                     cpf = "";
                     break;
                 }
             }
        }while (cpf.equals(""));
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

        makeChange();
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

        System.out.print("\n-Digite o CPF: ");
        String cpf = scan.nextLine();
        int idx = -1;
        int len = empregados.size();
        for(int i = 0; i < len && idx == -1; i++){
            if(empregados.get(i).getCpf().equals(cpf))
                idx = i;
        }
        if(idx != -1) {
            makeChange();
            empregados.remove(idx);
        }
    }

    public static void editarEmpregado(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tEDITAR EMPREGADO");

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
                    emp = new Empregado(empregados.get(i));
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
            if(sel == -1)
                return;
        }while(sel <= 0 || sel > 6);

        makeChange();

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

        Date dataEntrada = null, dataSaida = null;
        do {
            System.out.print("-Digite a data da entrada (dd/MM/aaaa HH:mm): ");
            String line = scan.nextLine();
            try {
                dataEntrada = format.parse(line);
            } catch (ParseException e) {
                System.out.println(">>Formato errado!");
            }
        }while (dataEntrada == null);

        do {
            System.out.print("-Digite a data da saída (dd/MM/aaaa HH:mm): ");
            String line = scan.nextLine();
            try {
                dataSaida = format.parse(line);
            } catch (ParseException e) {
                System.out.println(">>Formato errado!");
            }
        }while (dataSaida == null);

        makeChange();
        long hrs = datOperator.getTimeDifference(dataEntrada, dataSaida).getTime();
        hrs /= 60*60*1000;
        System.out.println("PORRA: "+ hrs);
        ((Horista)empregados.get(idxEmp)).baterPonto((int)hrs);
        ((Horista)empregados.get(idxEmp)).setUltimoPonto(dataSaida);
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

        Date data = null;
        do {
            System.out.print("-Digite a data da venda (dd/MM/aaaa HH:mm): ");
            String line = scan.nextLine();
            try {
                data = format.parse(line);
            } catch (ParseException e) {
                System.out.println(">>Formato errado!");
            }
        }while (data == null);

        makeChange();
        ((Comissionado)empregados.get(idxEmp)).addVenda(valor, data);
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

        makeChange();
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
            System.out.println("KRL: "+ cpf);
            if(cpf.equals("-1"))
                return;
            for(int i = 0; i < empregados.size(); i++){
                if(empregados.get(i).getCpf().equals(cpf))
                    idxEmp = i;
            }
            if(idxEmp == -1)
                System.out.println(">>Não existe nenhum empregado com este CPF!");
        }while(idxEmp == -1);

        makeChange();
        System.out.printf("• A agenda atual é %s.\n", empregados.get(idxEmp).getAgenda());
        int sel;
        do {
            System.out.println("\n-Selecione a nova agenda de pagamento");
            System.out.println("1. Semanal");
            System.out.println("2. Bi-semanal");
            System.out.println("3. Mensal");
            for(int i = 0; i < agendaRefsString.size(); i++){
                System.out.printf("%d. %s\n", i+4, agendaRefsString.get(i));
            }
            sel = scan.nextInt();
            scan.nextLine();
        }while(sel <= 0 || sel > 3 + agendaRefsString.size());

        makeChange();
        if(sel == 1) {
            empregados.get(idxEmp).setAgenda("semanal");
            empregados.get(idxEmp).setAgendaRef(new Date(7*24*60*60*1000));
            empregados.get(idxEmp).setAgendaDia("");
        } else if(sel == 2) {
            empregados.get(idxEmp).setAgenda("bi-semanal");
            empregados.get(idxEmp).setAgendaRef(new Date(2*7*24*60*60*1000));
            empregados.get(idxEmp).setAgendaDia("");
        } else if(sel == 3){
            empregados.get(idxEmp).setAgenda("mensal");
            empregados.get(idxEmp).setAgendaRef(new Date((long)30*24*60*60*1000));
            empregados.get(idxEmp).setAgendaDia("");
        } else {
            empregados.get(idxEmp).setAgenda(agendaRefsString.get(sel-4));
            empregados.get(idxEmp).setAgendaRef(new Date(agendaRefsDate.get(sel-4).getTime()));
            empregados.get(idxEmp).setAgendaDia(agendaRefsDia.get(sel-4));

            String[] parts = agendaRefsString.get(sel-4).split(" ");
            if(parts[0].equals("semanal")) {
                if (empregados.get(idxEmp).getUltimoPagamento() == null) {
                    empregados.get(idxEmp).setUltimoPagamento(new Date());
                }
                while (!formatDay.format(empregados.get(idxEmp).getUltimoPagamento()).equals(agendaRefsDia.get(sel - 4))) {
                    Date curr = new Date(empregados.get(idxEmp).getUltimoPagamento().getTime());
                    empregados.get(idxEmp).setUltimoPagamento(new Date(curr.getTime() - 24 * 60 * 60 * 1000));
                }
            } else {
                if (empregados.get(idxEmp).getUltimoPagamento() == null) {
                    empregados.get(idxEmp).setUltimoPagamento(new Date());
                }
                if(agendaRefsDia.get(sel-4).equals("$")){
                    while(empregados.get(idxEmp).getUltimoPagamento().getDate() < new Date(empregados.get(idxEmp).getUltimoPagamento().getTime() + 24 * 60 * 60 * 1000).getDate()){
                        Date curr = new Date(empregados.get(idxEmp).getUltimoPagamento().getTime());
                        empregados.get(idxEmp).setUltimoPagamento(new Date(curr.getTime() - 24 * 60 * 60 * 1000));
                    }
                    while (formatDay.format(empregados.get(idxEmp).getUltimoPagamento()).equals("Dom") || formatDay.format(empregados.get(idxEmp).getUltimoPagamento()).equals("Sab")){
                        Date curr = new Date(empregados.get(idxEmp).getUltimoPagamento().getTime());
                        empregados.get(idxEmp).setUltimoPagamento(new Date(curr.getTime() - 24 * 60 * 60 * 1000));
                    }
                } else {
                    while (empregados.get(idxEmp).getUltimoPagamento().getDate() != Integer.parseInt(agendaRefsDia.get(sel - 4))) {
                        System.out.println(empregados.get(idxEmp).getUltimoPagamento().getDate());
                        System.out.println(Integer.parseInt(agendaRefsDia.get(sel - 4)));
                        long curr = empregados.get(idxEmp).getUltimoPagamento().getTime();
                        empregados.get(idxEmp).setUltimoPagamento(new Date(curr - 24 * 60 * 60 * 1000));
                    }
                }
            }
        }
    }

    public static void undo(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tUNDO\n");
        if(pilhaUndoHor.size() + pilhaUndoAss.size() + pilhaUndoCom.size() > 0) {
            List<Horista> hors = new LinkedList<>();
            List<Empregado> asss = new LinkedList<>();
            List<Comissionado> coms = new LinkedList<>();
            for(int i = 0; i < empregados.size(); i++){
                switch (empregados.get(i).getType()){
                    case "horista": hors.add((Horista)empregados.get(i));
                        break;
                    case "assalariado": asss.add(empregados.get(i));
                        break;
                    case "comissionado": coms.add((Comissionado)empregados.get(i));
                        break;
                }
            }
            pilhaRedoHor.push(gson.toJson(hors, typeHor));
            pilhaRedoAss.push(gson.toJson(asss, typeAss));
            pilhaRedoCom.push(gson.toJson(coms, typeCom));

            hors = gson.fromJson(pilhaUndoHor.pop(), typeHor);
            asss = gson.fromJson(pilhaUndoAss.pop(), typeAss);
            coms = gson.fromJson(pilhaUndoCom.pop(), typeCom);
            empregados = new LinkedList<>();
            empregados.addAll(hors);
            empregados.addAll(asss);
            empregados.addAll(coms);
            System.out.println("• Última alteração desfeita com sucesso!\n");
        } else {
            System.out.println("• Não há nenhuma alteração para ser desfeita!\n");
        }
        System.out.println("APERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public static void redo(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tREDO\n");
        if(pilhaRedoHor.size() + pilhaRedoAss.size() + pilhaRedoCom.size() > 0) {
            List<Horista> hors = new LinkedList<>();
            List<Empregado> asss = new LinkedList<>();
            List<Comissionado> coms = new LinkedList<>();
            for(int i = 0; i < empregados.size(); i++){
                switch (empregados.get(i).getType()){
                    case "horista": hors.add((Horista)empregados.get(i));
                        break;
                    case "assalariado": asss.add(empregados.get(i));
                        break;
                    case "comissionado": coms.add((Comissionado)empregados.get(i));
                        break;
                }
            }
            pilhaUndoHor.push(gson.toJson(hors, typeHor));
            pilhaUndoAss.push(gson.toJson(asss, typeAss));
            pilhaUndoCom.push(gson.toJson(coms, typeCom));

            hors = gson.fromJson(pilhaRedoHor.pop(), typeHor);
            asss = gson.fromJson(pilhaRedoAss.pop(), typeAss);
            coms = gson.fromJson(pilhaRedoCom.pop(), typeCom);
            empregados = new LinkedList<>();
            empregados.addAll(hors);
            empregados.addAll(asss);
            empregados.addAll(coms);
            System.out.println("• Última alteração refeita com sucesso!\n");
        } else {
            System.out.println("• Não há nenhuma alteração para ser refeita!\n");
        }
        System.out.println("APERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public static void rodarFolha(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tRODAR FOLHA DE PAGAMENTO\n");

        makeChange();

        Date dataAtual = null;
        do {
            System.out.print("-Digite a data atual (dd/MM/aaaa HH:mm): ");
            String line = scan.nextLine();
            try {
                dataAtual = format.parse(line);
            } catch (ParseException e) {
                System.out.println(">>Formato errado!\n");
            }
        }while(dataAtual == null);

        float pagamentoTotal = 0;
        List<Empregado> emps = new LinkedList<>();
        for (Empregado empregado : empregados) {
            if (empregado.getUltimoPagamento() == null) {
                //pagamentoTotal += empregado.getSalario();
                empregado.setUltimoPagamento(dataAtual);
                emps.add(empregado);
            } else {
                Date dif = datOperator.getTimeDifference(empregado.getUltimoPagamento(), dataAtual);
                if (empregado.getUltimoPagamento() == null || empregado.getAgendaRef().before(dif) || dif.compareTo(empregado.getAgendaRef()) == 0) {
                    //pagamentoTotal += empregado.getSalario();
                    empregado.setUltimoPagamento(dataAtual);
                    emps.add(empregado);
                }
            }
        }

        System.out.println("\n• Empregados pagos:");
        System.out.println("    NOME    |    TIPO    |    SALÁRIO LÍQUIDO");
        for(Empregado emp: emps){
            float salario = emp.getSalario();
            if(emp.getType().equals("horista")){
                salario = 0;
                for(int i = 0; i < ((Horista)emp).getTotalDePontosBatidos(); i++){
                    salario += ((Horista)emp).getHorasAtIdx(i) * emp.getSalario();
                }
                ((Horista)emp).resetPontos();
            } else if(emp.getType().equals("comissionado")){
                for(int i = 0; i < ((Comissionado)emp).getTotalVendas(); i++) {
                    salario += ((Comissionado)emp).getValorVendaAtIdx(i) * (((Comissionado)emp).getComissao() / 100);
                }
                ((Comissionado)emp).resetVendas();
            }
            System.out.printf("%s | %s | %.02f\n", emp.getName(), emp.getType(), salario - emp.getSindicatoTaxa());
            pagamentoTotal += salario;
        }
        System.out.printf("-------------------------\n• Total pago: R$ %.02f\n", pagamentoTotal);

        System.out.println("\nAPERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public static void criarNovaAgenda(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tCRIAR NOVA AGENDA DE PAGAMENTO\n");

        System.out.print("-Digite a nova agenda: ");
        String agenda = scan.nextLine();

        String[] parts = agenda.split(" ");

        if(parts[0].equals("mensal")){
            agendaRefsDate.add(new Date((long) 30 * 24 * 60 * 60 * 1000));
            try {
                int day = Integer.parseInt(parts[1]);
                agendaRefsDia.add(parts[1]);
                agendaRefsString.add(agenda);
            } catch (Exception e) {
                agendaRefsDia.add("$");
                agendaRefsString.add(parts[0] + " $");
            }
        } else if(parts[0].equals("semanal")) {
            agendaRefsString.add(agenda);
            agendaRefsDate.add(new Date((long)7*24*60*60*1000*Integer.parseInt(parts[1])));
            switch (parts[2]){
                case "domingo": agendaRefsDia.add("Dom");
                    break;
                case "segunda": agendaRefsDia.add("Seg");
                    break;
                case "terça": agendaRefsDia.add("Ter");
                    break;
                case "quarta": agendaRefsDia.add("Qua");
                    break;
                case "quinta": agendaRefsDia.add("Qui");
                    break;
                case "sexta": agendaRefsDia.add("Sex");
                    break;
                case "sabado": agendaRefsDia.add("Sab");
                    break;
            }
        }
    }

    public static void mostrarEmpregados(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tMOSTRAR EMPREGADOS\n");
        System.out.println("• Empregados");
        int sindicalistas = 0;
        for(int i = 0; i < empregados.size(); i++){
            if(empregados.get(i).isSindicalista())
                sindicalistas++;
            System.out.println(empregados.get(i));
            if(empregados.get(i).getType().equals("horista")){
                if(((Horista)empregados.get(i)).getTotalDePontosBatidos() > 0){
                    Date ponto = ((Horista)empregados.get(i)).getUltimoPonto();
                    System.out.printf("\t-Ultimo ponto batido: %s\n", (ponto == null ? "null" : format.format(ponto)));
                }
            } else if(empregados.get(i).getType().equals("comissionado")){
                if(((Comissionado)empregados.get(i)).getTotalVendas() > 0){
                    Venda venda = ((Comissionado)empregados.get(i)).getUltimaVenda();
                    System.out.printf("\t-Ultima venda efetuada: R$ %.02f; %s\n", venda.getValor(), format.format(venda.getData()));
                }
            }
            System.out.println("-------------------------");
        }

        System.out.printf("• Quantidade atual de sindicalistas: %d\n", sindicalistas);
        System.out.println("\n\nAPERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public static void makeChange(){
        pilhaRedoAss = new Stack<>();
        pilhaRedoHor = new Stack<>();
        pilhaRedoCom = new Stack<>();
        List<Horista> hors = new LinkedList<>();
        List<Empregado> asss = new LinkedList<>();
        List<Comissionado> coms = new LinkedList<>();
        for(int i = 0; i < empregados.size(); i++) {
            switch (empregados.get(i).getType()) {
                case "horista": hors.add((Horista) empregados.get(i));
                    break;
                case "assalariado": asss.add(empregados.get(i));
                    break;
                case "comissionado": coms.add((Comissionado)empregados.get(i));
                    break;
            }
        }
        pilhaUndoHor.push(gson.toJson(hors, typeHor));
        pilhaUndoAss.push(gson.toJson(asss, typeAss));
        pilhaUndoCom.push(gson.toJson(coms, typeCom));
    }
}