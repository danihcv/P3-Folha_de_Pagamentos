import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.text.*;

public class Main {
    public static void main(String[] args) {
        DataBase db = new DataBase();
        Scanner scan = new Scanner(System.in);

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
                case 1: db.addEmpregado();
                    break;
                case 2: db.removerEmpregado();
                    break;
                case 3: db.editarEmpregado();
                    break;
                case 4: db.baterPonto();
                    break;
                case 5: db.fazerVenda();
                    break;
                case 6: db.atualizarTaxaSindical();
                    break;
                case 7: db.atualizarAgendaPagamento();
                    break;
                case 8: db.undo();
                    break;
                case 9: db.redo();
                    break;
                case 10: db.rodarFolha();
                    break;
                case 11: db.criarNovaAgenda();
                    break;
                case 12: db.mostrarEmpregados();
                    break;
                case -1: menu = false;
                    break;
            }
        }while(menu);
    }
}