import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataBase {
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm");
    DateFormat formatDay = new SimpleDateFormat("EEE");
    DataEHora datOperator = new DataEHora();
    List<Date> agendaRefsDate = new LinkedList<>();
    List<String> agendaRefsDia = new LinkedList<>();
    List<String> agendaRefsString = new LinkedList<>();

    UndoRedoHelper dson = new UndoRedoHelper();
/*
    Scanner scanString = new Scanner(System.in);
    Scanner scanInt = new Scanner(System.in);
    Scanner scanFloat = new Scanner(System.in);
*/
    Scanner scan = new Scanner(System.in);
    int currentID = 0;

    private Stack<List<String>> pilhaUndo = new Stack<>();
    private Stack<List<String>> pilhaRedo = new Stack<>();

    private List<Empregado> empregados = new LinkedList<>();
    private Sindicato sindicato = new Sindicato();

    public void addEmpregado() throws NumberFormatException {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tADIÇÃO DE EMPREGADO\n");

        String cpf;
        do {
            System.out.print("-CPF: ");
            cpf = scan.nextLine();
            for (Empregado emp : empregados) {
                if (emp.getCpf().equals(cpf)) {
                    System.out.println(">>CPF JÁ CADASTRADO!\n");
                    cpf = "";
                    break;
                }
            }
        } while (cpf.equals(""));
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
        do {
            tipo = Integer.parseInt(scan.nextLine());
        } while (tipo <= 0 || tipo > 3);
        switch (tipo) {
            case 1:
                System.out.print("-Salário por hora: ");
                break;
            case 2:
                System.out.print("-Salário por mês: ");
                break;
            case 3:
                System.out.print("-Comissão (%): ");
                comissao = Float.parseFloat(scan.nextLine());
                System.out.print("-Salário por semana: ");
                break;
        }
        float salario = Float.parseFloat(scan.nextLine());

        int sel;
        do {
            System.out.println("\n-Selecione um método de pagamento: ");
            System.out.println("1. Cheque pelos correios");
            System.out.println("2. Cheque em mãos");
            System.out.println("3. Depósito em conta");
            sel = Integer.parseInt(scan.nextLine());
        } while (sel <= 0 || sel > 3);

        String metodoDePagamento = "";
        switch (sel) {
            case 1:
                metodoDePagamento = "cheque-correios";
                break;
            case 2:
                metodoDePagamento = "cheque-maos";
                break;
            case 3:
                metodoDePagamento = "deposito";
                break;
        }

        makeChange();
        switch (tipo) {
            case 1:
                empregados.add(new Horista(++currentID, cpf, name, address, salario, metodoDePagamento));
                break;
            case 2:
                empregados.add(new Assalariado(++currentID, cpf, name, address, salario, metodoDePagamento));
                break;
            case 3:
                empregados.add(new Comissionado(++currentID, cpf, name, address, salario, comissao, metodoDePagamento));
                break;
        }
    }

    public void removerEmpregado() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tREMOÇÃO DE EMPREGADO\n");

        int idxEmp;
        do {
            idxEmp = this.getEmpregadoIdxByCpf();
            if (idxEmp == -1) {
                System.out.println(">>Não existe nenhum empregado com este CPF!\n");
            }
        } while (idxEmp == -1);
        if (idxEmp == -2) {
            return;
        }

        makeChange();
        empregados.remove(idxEmp);
    }

    public void editarEmpregado() throws NumberFormatException {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tEDITAR EMPREGADO");

        int idxEmp;
        do {
            idxEmp = this.getEmpregadoIdxByCpf();

            if (idxEmp == -1) {
                System.out.println(">>Não existe nenhuma empregado com este CPF!\n");
            }
        } while (idxEmp == -1);
        if (idxEmp == -2) {
            return;
        }
        Empregado emp = new Assalariado(empregados.get(idxEmp));

        int sel;
        do {
            System.out.println("\n-Selecione o que deseja alterar:");
            System.out.println("1. Nome");
            System.out.println("2. Endereço");
            System.out.println("3. Tipo");
            System.out.println("4. Método de pagamento");
            System.out.println("5. Participação no sindicato");
            System.out.println("6. Identificação no sindicato");
            System.out.println("7. Taxa sindical");
            sel = Integer.parseInt(scan.nextLine());

            if (sel == -1)
                return;
        } while (sel <= 0 || sel > 6);

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
                    newType = Integer.parseInt(scan.nextLine());
                } while (newType <= 0 || newType > 3);
                if (newType == 1)
                    empregados.add(new Horista(emp.getId(), emp.getCpf(), emp.getName(), emp.getAddress(), emp.getSalario(), emp.getMetodoDePagamento()));
                else if (newType == 2)
                    empregados.add(new Assalariado(emp.getId(), emp.getCpf(), emp.getName(), emp.getAddress(), emp.getSalario(), emp.getMetodoDePagamento()));
                else {
                    System.out.println("-Digite a comissão (%): ");
                    float comissao = Float.parseFloat(scan.nextLine());
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
                    met = Integer.parseInt(scan.nextLine());
                } while (met <= 0 || met > 3);
                if (met == 1)
                    newMetodoDePagamento = "cheque-correios";
                else if (met == 2)
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
                } while (!res.equals("s") && !res.equals("n"));

                if (res.equals("s") && !emp.isSindicalista()) {
                    System.out.print("-Digite a taxa sindical: ");
                    Float taxa = Float.parseFloat(scan.nextLine());

                    empregados.get(idxEmp).setSindicalista(true);
                    empregados.get(idxEmp).setSindicatoTaxa(taxa);
                    empregados.get(idxEmp).setSindicatoID(sindicato.getCurrentID());
                    sindicato.addSindicalista(empregados.get(idxEmp));
                    sindicato.setCurrentID(sindicato.getCurrentID() + 1);
                } else if (res.equals("s")) {
                    empregados.get(idxEmp).setSindicalista(false);
                    empregados.get(idxEmp).setSindicatoTaxa(0);
                    empregados.get(idxEmp).setSindicatoID(-1);
                    sindicato.removeSindicalista(emp.getCpf());
                }
                break;
            case 6:
                System.out.println("\nALTERAR IDENTIFICAÇÃO NO SINDICATO\n");
                System.out.printf("• O ID atual do empregado no sindicato é %d\n", emp.getSindicatoID());
                System.out.print("-Insira a nova identificação desejada (int): ");
                int newID = Integer.parseInt(scan.nextLine());

                empregados.get(idxEmp).setSindicatoID(newID);
                sindicato.setCurrentID(newID + 1);
                break;
            case 7:
                System.out.println("\nALTERAR TAXA SINDICAL\n");
                System.out.printf("-Taxa atual: %f\n", emp.getSindicatoTaxa());
                System.out.print("-Digite a nova taxa: ");
                newTaxa = Float.parseFloat(scan.nextLine());
                empregados.get(idxEmp).setSindicatoTaxa(newTaxa);
                break;
        }
    }

    public void baterPonto() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tBATER PONTO\n");

        int idxEmp;
        do {
            idxEmp = this.getEmpregadoIdxByCpf();

            if (idxEmp == -2) {
                return;
            }
            if (idxEmp == -1 || !empregados.get(idxEmp).getType().equals("horista")) {
                System.out.println(">>Não existe nenhum horista com este CPF!\n");
                idxEmp = -1;
            }
        } while (idxEmp == -1);

        Date dataEntrada = null, dataSaida = null;
        do {
            System.out.print("-Digite a data da entrada (dd/MM/aaaa HH:mm): ");
            String line = scan.nextLine();
            try {
                dataEntrada = format.parse(line);
            } catch (ParseException e) {
                System.out.println(">>Formato errado!");
            }
        } while (dataEntrada == null);

        do {
            System.out.print("-Digite a data da saída (dd/MM/aaaa HH:mm): ");
            String line = scan.nextLine();
            try {
                dataSaida = format.parse(line);
            } catch (ParseException e) {
                System.out.println(">>Formato errado!");
            }
        } while (dataSaida == null);

        makeChange();
        long hrs = datOperator.getTimeDifference(dataEntrada, dataSaida).getTime();
        hrs /= 60 * 60 * 1000;
        System.out.println("PORRA: " + hrs);
        ((Horista) empregados.get(idxEmp)).baterPonto((int) hrs);
        ((Horista) empregados.get(idxEmp)).setUltimoPonto(dataSaida);
    }

    public void fazerVenda() throws NumberFormatException {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\nFAZER UMA VENDA\n");

        int idxEmp;
        do {
            idxEmp = this.getEmpregadoIdxByCpf();

            if (idxEmp == -2) {
                return;
            }
            if (idxEmp == -1 || !empregados.get(idxEmp).getType().equals("comissionado")) {
                System.out.println(">>Não existe nenhum comissionado com este CPF!\n");
                idxEmp = -1;
            }
        } while (idxEmp == -1);

        float valor;
        System.out.print("-Informe o valor da venda: ");
        valor = Float.parseFloat(scan.nextLine());

        Date data = null;
        do {
            System.out.print("-Digite a data da venda (dd/MM/aaaa HH:mm): ");
            String line = scan.nextLine();
            try {
                data = format.parse(line);
            } catch (ParseException e) {
                System.out.println(">>Formato errado!");
            }
        } while (data == null);

        makeChange();
        ((Comissionado) empregados.get(idxEmp)).addVenda(valor, data);
    }

    public void atualizarTaxaSindical() throws NumberFormatException {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\nATUALIZAR TAXA SINDICAL\n");

        int idxEmp;
        do {
            idxEmp = this.getEmpregadoIdxByCpf();

            if (idxEmp == -2) {
                return;
            }
            if (idxEmp == -1 || !empregados.get(idxEmp).isSindicalista()) {
                System.out.println(">>Não existe nenhum empregado com este CPF associado ao sindicato!\n");
                idxEmp = -1;
            }
        } while (idxEmp == -1);

        System.out.print("-Digite a nova taxa: ");
        float newTaxa = Float.parseFloat(scan.nextLine());

        makeChange();
        empregados.get(idxEmp).setSindicatoTaxa(newTaxa);
    }

    public void atualizarAgendaPagamento() throws NumberFormatException {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tESCOLHER NOVA AGENDA\n");

        int idxEmp;
        do {
            idxEmp = this.getEmpregadoIdxByCpf();
            if (idxEmp == -1)
                System.out.println(">>Não existe nenhum empregado com este CPF!");
        } while (idxEmp == -1);
        if (idxEmp == -2) {
            return;
        }

        makeChange();
        System.out.printf("• A agenda atual é %s.\n", empregados.get(idxEmp).getAgenda());
        int sel;
        do {
            System.out.println("\n-Selecione a nova agenda de pagamento");
            System.out.println("1. Semanal");
            System.out.println("2. Bi-semanal");
            System.out.println("3. Mensal");
            for (int i = 0; i < agendaRefsString.size(); i++) {
                System.out.printf("%d. %s\n", i + 4, agendaRefsString.get(i));
            }
            sel = Integer.parseInt(scan.nextLine());
        } while (sel <= 0 || sel > 3 + agendaRefsString.size());

        makeChange();
        if (sel == 1) {
            empregados.get(idxEmp).setAgenda("semanal");
            empregados.get(idxEmp).setAgendaRef(new Date(7 * 24 * 60 * 60 * 1000));
            empregados.get(idxEmp).setAgendaDia("-");
        } else if (sel == 2) {
            empregados.get(idxEmp).setAgenda("bi-semanal");
            empregados.get(idxEmp).setAgendaRef(new Date(2 * 7 * 24 * 60 * 60 * 1000));
            empregados.get(idxEmp).setAgendaDia("-");
        } else if (sel == 3) {
            empregados.get(idxEmp).setAgenda("mensal");
            empregados.get(idxEmp).setAgendaRef(new Date((long) 30 * 24 * 60 * 60 * 1000));
            empregados.get(idxEmp).setAgendaDia("-");
        } else {
            empregados.get(idxEmp).setAgenda(agendaRefsString.get(sel - 4));
            empregados.get(idxEmp).setAgendaRef(new Date(agendaRefsDate.get(sel - 4).getTime()));
            empregados.get(idxEmp).setAgendaDia(agendaRefsDia.get(sel - 4));

            String[] parts = agendaRefsString.get(sel - 4).split(" ");
            if (parts[0].equals("semanal")) {
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
                if (agendaRefsDia.get(sel - 4).equals("$")) {
                    while (empregados.get(idxEmp).getUltimoPagamento().getDate() < new Date(empregados.get(idxEmp).getUltimoPagamento().getTime() + 24 * 60 * 60 * 1000).getDate()) {
                        Date curr = new Date(empregados.get(idxEmp).getUltimoPagamento().getTime());
                        empregados.get(idxEmp).setUltimoPagamento(new Date(curr.getTime() - 24 * 60 * 60 * 1000));
                    }
                    while (formatDay.format(empregados.get(idxEmp).getUltimoPagamento()).equals("Dom") || formatDay.format(empregados.get(idxEmp).getUltimoPagamento()).equals("Sab")) {
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

    public void undo() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tUNDO\n");
        if (pilhaUndo.size() > 0) {
            List<String> list = new LinkedList<>();
            for (Empregado emp : empregados) {
                list.add(dson.stringfy(emp));
            }
            pilhaRedo.push(list);

            empregados = new LinkedList<>();
            list = pilhaUndo.pop();
            for (String emp : list) {
                Empregado obj = dson.objetify(emp);
                if (obj instanceof Assalariado) {
                    empregados.add((Assalariado) obj);
                } else if (obj instanceof Horista) {
                    empregados.add((Horista) obj);
                } else if (obj instanceof Comissionado) {
                    empregados.add((Comissionado) obj);
                }
            }
            System.out.println("• Última alteração desfeita com sucesso!\n");
        } else {
            System.out.println("• Não há nenhuma alteração para ser desfeita!\n");
        }
        System.out.println("APERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public void redo() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tREDO\n");
        if (pilhaRedo.size() > 0) {
            List<String> list = new LinkedList<>();
            for (Empregado emp : empregados) {
                list.add(dson.stringfy(emp));
            }
            pilhaUndo.push(list);

            empregados = new LinkedList<>();
            list = pilhaRedo.pop();
            for (String emp : list) {
                Empregado obj = dson.objetify(emp);
                if (obj instanceof Assalariado) {
                    empregados.add((Assalariado) obj);
                } else if (obj instanceof Horista) {
                    System.out.println("porra");
                    empregados.add((Horista) obj);
                } else if (obj instanceof Comissionado) {
                    empregados.add((Comissionado) obj);
                }
            }
            System.out.println("• Última alteração refeita com sucesso!\n");
        } else {
            System.out.println("• Não há nenhuma alteração para ser refeita!\n");
        }
        System.out.println("APERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public void rodarFolha() {
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
        } while (dataAtual == null);

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
        for (Empregado emp : emps) {
            float salario = emp.getSalario();
            if (emp.getType().equals("horista")) {
                salario = 0;
                for (int i = 0; i < ((Horista) emp).getTotalDePontosBatidos(); i++) {
                    salario += ((Horista) emp).getHorasAtIdx(i) * emp.getSalario();
                }
                ((Horista) emp).resetPontos();
            } else if (emp.getType().equals("comissionado")) {
                for (int i = 0; i < ((Comissionado) emp).getTotalVendas(); i++) {
                    salario += ((Comissionado) emp).getValorVendaAtIdx(i) * (((Comissionado) emp).getComissao() / 100);
                }
                ((Comissionado) emp).resetVendas();
            }
            System.out.printf("%s | %s | %.02f\n", emp.getName(), emp.getType(), salario - emp.getSindicatoTaxa());
            pagamentoTotal += salario;
        }
        System.out.printf("-------------------------\n• Total pago: R$ %.02f\n", pagamentoTotal);

        System.out.println("\nAPERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public void criarNovaAgenda() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tCRIAR NOVA AGENDA DE PAGAMENTO\n");

        System.out.print("-Digite a nova agenda: ");
        String agenda = scan.nextLine();

        String[] parts = agenda.split(" ");

        if (parts[0].equals("mensal")) {
            agendaRefsDate.add(new Date((long) 30 * 24 * 60 * 60 * 1000));
            try {
                int day = Integer.parseInt(parts[1]);
                agendaRefsDia.add(parts[1]);
                agendaRefsString.add(agenda);
            } catch (Exception e) {
                agendaRefsDia.add("$");
                agendaRefsString.add(parts[0] + " $");
            }
        } else if (parts[0].equals("semanal")) {
            agendaRefsString.add(agenda);
            agendaRefsDate.add(new Date((long) 7 * 24 * 60 * 60 * 1000 * Integer.parseInt(parts[1])));
            switch (parts[2]) {
                case "domingo":
                    agendaRefsDia.add("Dom");
                    break;
                case "segunda":
                    agendaRefsDia.add("Seg");
                    break;
                case "terça":
                    agendaRefsDia.add("Ter");
                    break;
                case "quarta":
                    agendaRefsDia.add("Qua");
                    break;
                case "quinta":
                    agendaRefsDia.add("Qui");
                    break;
                case "sexta":
                    agendaRefsDia.add("Sex");
                    break;
                case "sabado":
                    agendaRefsDia.add("Sab");
                    break;
            }
        }
    }

    public void mostrarEmpregados() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\tMOSTRAR EMPREGADOS\n");
        System.out.println("• Empregados");
        int sindicalistas = 0;
        for (int i = 0; i < empregados.size(); i++) {
            if (empregados.get(i).isSindicalista())
                sindicalistas++;
            System.out.println(empregados.get(i));
            if (empregados.get(i).getType().equals("horista")) {
                if (((Horista) empregados.get(i)).getTotalDePontosBatidos() > 0) {
                    Date ponto = ((Horista) empregados.get(i)).getUltimoPonto();
                    System.out.printf("\t-Ultimo ponto batido: %s\n", (ponto == null ? "null" : format.format(ponto)));
                }
            } else if (empregados.get(i).getType().equals("comissionado")) {
                if (((Comissionado) empregados.get(i)).getTotalVendas() > 0) {
                    Venda venda = ((Comissionado) empregados.get(i)).getUltimaVenda();
                    System.out.printf("\t-Ultima venda efetuada: R$ %.02f; %s\n", venda.getValor(), format.format(venda.getData()));
                }
            }
            System.out.println("-------------------------");
        }

        System.out.printf("• Quantidade atual de sindicalistas: %d\n", sindicalistas);
        System.out.println("\n\nAPERTE ENTER PARA CONTINUAR");
        scan.nextLine();
    }

    public void makeChange() {
        pilhaRedo = new Stack<>();
        List<Horista> hors = new LinkedList<>();
        List<Empregado> asss = new LinkedList<>();
        List<Comissionado> coms = new LinkedList<>();
        for (int i = 0; i < empregados.size(); i++) {
            switch (empregados.get(i).getType()) {
                case "horista":
                    hors.add((Horista) empregados.get(i));
                    break;
                case "assalariado":
                    asss.add(empregados.get(i));
                    break;
                case "comissionado":
                    coms.add((Comissionado) empregados.get(i));
                    break;
            }
        }
        List<String> list = new LinkedList<>();
        for (Empregado emp : empregados) {
            list.add(dson.stringfy(emp));
        }
        pilhaUndo.push(list);
    }

    public int getEmpregadoIdxByCpf() {
        String cpf;
        System.out.print("-Digite o CPF do empregado: ");
        cpf = scan.nextLine();
        if (cpf.equals("-1"))
            return -2;
        for (int i = 0; i < empregados.size(); i++) {
            if (empregados.get(i).getCpf().equals(cpf))
                return i;
        }
        return -1;
    }
}
