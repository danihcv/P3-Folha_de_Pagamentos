import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UndoRedoHelper {
    public String stringfy(Empregado emp) {
        String str = "";
        str += emp.getType() + "\n" + //0
                emp.getId() + "\n" + //1
                emp.getCpf() + "\n" + //2
                emp.getName() + "\n" + //3
                emp.getAddress() + "\n" + //4
                emp.getSalario() + "\n" + //5
                emp.getMetodoDePagamento() + "\n" + //6
                emp.isSindicalista() + "\n" + //7
                emp.getSindicatoID() + "\n" + //8
                emp.getSindicatoTaxa() + "\n" + //9
                emp.getUltimoPagamento().getTime() + "\n" + //10
                emp.getAgenda() + "\n" + //11
                emp.getAgendaRef().getTime() + "\n" + //12
                emp.getAgendaDia() + "\n"; //13

        if(emp instanceof Horista) {
            for(int i = 0; i < ((Horista) emp).getTotalDePontosBatidos(); i++){
                if(i > 0)
                     str += ",";
                str += ((Horista) emp).getHorasAtIdx(i);
            }
            str += "\n";
        } else if(emp instanceof Comissionado) {
            for(int i = 0; i < ((Comissionado) emp).getTotalVendas(); i++){
                if(i > 0)
                    str += ",";
                str += ((Comissionado) emp).getVendaAtIdx(i).getValor() + ";" + ((Comissionado) emp).getVendaAtIdx(i).getData().getTime();
            }
            str += "\n" +
                    ((Comissionado) emp).getComissao() + "\n";
        }

        return str;
    }

    public Empregado objetify(String str){
        String[] parts = str.split("\n");
        String type = parts[0];
        int id = Integer.parseInt(parts[1]);
        String cpf = parts[2];
        String name = parts[3];
        String address = parts[4];
        float salario = Float.parseFloat(parts[5]);
        String metodo = parts[6];
        boolean sindicalista = Boolean.parseBoolean(parts[7]);
        int sindicatoID = Integer.parseInt(parts[8]);
        float sindicatoTaxa = Float.parseFloat(parts[9]);
        Date ultimoPagamento = new Date(Long.parseLong(parts[10]));
        String agenda = parts[11];
        Date agendaRef = new Date(Long.parseLong(parts[12]));
        String agendaDia = parts[13];

        if (type.equals("horista")) {
            List<String> list = new LinkedList<>();
            if(parts.length > 14) {
                String[] pontos = parts[14].split(",");
                list.addAll(Arrays.asList(pontos));
            }

            return new Horista(id, cpf, name, address, salario, metodo, sindicalista, sindicatoID, sindicatoTaxa, ultimoPagamento, agenda, agendaRef, agendaDia, list);
        } else if(type.equals("comissionado")) {
            List<Venda> list = new LinkedList<>();
            if(parts.length > 15) {
                String[] vendas = parts[14].split(",");
                for (String vend : vendas) {
                    if(!vend.equals("")) {
                        String[] line = vend.split(";");
                        list.add(new Venda(Float.parseFloat(line[0]), new Date(Long.parseLong(line[1]))));
                    }
                }
                Float comissao = Float.parseFloat(parts[15]);

                return new Comissionado(id, cpf, name, address, salario, metodo, sindicalista, sindicatoID, sindicatoTaxa, ultimoPagamento, agenda, agendaRef, agendaDia, list, comissao);
            }
            else {
                Float comissao = Float.parseFloat(parts[14]);

                return new Comissionado(id, cpf, name, address, salario, metodo, sindicalista, sindicatoID, sindicatoTaxa, ultimoPagamento, agenda, agendaRef, agendaDia, list, comissao);
            }
        } else {
            return new Assalariado(id, cpf, name, address, salario, metodo, sindicalista, sindicatoID, sindicatoTaxa, ultimoPagamento, agenda, agendaRef, agendaDia);
        }
    }
}
