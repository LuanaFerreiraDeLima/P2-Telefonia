import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Telefonia {
	 private Assinante[] assinantes;
	 private int numAssinantes;

    public Telefonia(int maxAssinantes) {
        this.assinantes = new Assinante[maxAssinantes];
        this.numAssinantes = 0;
    }

    public void cadastrarAssinante(Assinante assinante) {
        if (numAssinantes < assinantes.length) {
            assinantes[numAssinantes] = assinante;
            numAssinantes++;
            System.out.println("Assinante cadastrado com sucesso!");
        } else {
            System.out.println("Não é possível cadastrar mais assinantes. Limite máximo atingido.");
        }
    }

    public void listarAssinantes() {
    	System.out.println("Lista de assinantes pré-pagos:");
        for (int i = 0; i < numAssinantes; i++) {
            if (assinantes[i] instanceof PrePago) {
                System.out.println(assinantes[i].toString());
            }
        }
        System.out.println("Lista de assinantes pós-pagos:");
        for (int i = 0; i < numAssinantes; i++) {
            if (assinantes[i] instanceof PosPago) {
                System.out.println(assinantes[i].toString());
            }
        }
    }

    public void fazerChamada(Assinante assinante)
    {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("\n-- Fazer Chamada --");

        System.out.print("Digite a duracao da chamada: ");
        int duracao = scanner.nextInt();
        scanner.nextLine(); //limpar buffer
        
        System.out.print("Digite a data (dd/MM/yyyy): ");
        String dataString = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = format.parse(dataString);
        } catch (ParseException e) {
            System.out.println("Data inválida. Certifique-se de usar o formato dd/MM/yyyy.");
            return;
        }
        
        if (assinante instanceof PosPago) {
            PosPago posPago = (PosPago) assinante;
            posPago.fazerChamada(data, duracao);
        } 
        else if(assinante instanceof PrePago) {
            PrePago prePago = (PrePago) assinante;
            prePago.fazerChamada(data, duracao);
        }
        else {
            System.out.println("Não foi possível realizar a chamada. Assinante não encontrado");
        }
      }

    public void fazerRecarga(Assinante assinante)
    {
        if (assinante instanceof PrePago) {
        	Scanner scanner = new Scanner(System.in);
        	System.out.print("Digite o valor da recarga: ");
            float valorRecarga = scanner.nextFloat();
            scanner.nextLine(); //limpar buffer
            
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String dataString = scanner.nextLine();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date data = null;
            try {
                data = format.parse(dataString);
            } catch (ParseException e) {
                System.out.println("Data inválida. Certifique-se de usar o formato dd/MM/yyyy.");
                return;
            }
            PrePago prePago = (PrePago) assinante;
            prePago.recarregar(data, valorRecarga);
        } else {
            System.out.println("Não foi possível realizar a recarga. Assinante não é pré-pago.");
        }
    }
    public void imprimirFaturas(){
    	
    	Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o mês (ex: 1): ");
        int mes = scanner.nextInt();
        
        System.out.print("Digite o ano (ex: 2000): ");
        int ano = scanner.nextInt();
        
        if(numAssinantes == 0) {
        	System.out.print("sem faturas de assinates");
        }
        	
        System.out.println("Faturas dos Assinantes Pré-Pagos:");
        
        for (int i = 0; i < assinantes.length; i++) {
            if (assinantes[i] instanceof PrePago) {
            	PrePago prePago = (PrePago) assinantes[i];
                prePago.imprimirFatura(mes, ano);
            }
        }
        System.out.println("Faturas dos Assinantes Pos-Pagos:");
        for (int i = 0; i < assinantes.length; i++) {
            if (assinantes[i] instanceof PosPago) {
            	PosPago posPago = (PosPago) assinantes[i];
                posPago.imprimirFatura(mes, ano);
            }
        }
        
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        System.out.print("Iniciando sistema, digite qual o numero máximo de Assinantes:");
        int assinantes = scanner.nextInt();

        Telefonia telefonia = new Telefonia(assinantes);

        do {
            System.out.println("\n------ MENU ------\n");
            System.out.println("1 - Cadastrar assinante");
            System.out.println("2 - Listar assinantes");
            System.out.println("3 - Fazer chamada");
            System.out.println("4 - Fazer recarga");
            System.out.println("5 - Imprimir faturas");
            System.out.println("6 - Sair do programa");
            System.out.print("\nEscolha uma op:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o tipo do assinante (1 - Pre-pago / 2 - Pos-pago): ");
                    int tipo = scanner.nextInt();
                    
                    System.out.print("Digite o CPF do assinante: ");
                    long Cpf = scanner.nextLong();
                    scanner.nextLine(); //limpar o buffer

                    System.out.print("Digite o nome do assinante: ");
                    String Nome = scanner.nextLine();

                    System.out.print("Digite o nmero do telefone do assinante: ");
                    int Numero = scanner.nextInt();
                    
                    if(tipo == 1) {
                    	Assinante assinante = new PrePago(Cpf, Nome, Numero);
                    	telefonia.cadastrarAssinante(assinante);
                    }
                    else if(tipo == 2) {
                    	System.out.print("Digite o valor da fatura do assinante: ");
                        float Valor = scanner.nextFloat();
                        Assinante assinante = new PosPago(Valor, Cpf, Nome, Numero);
                        telefonia.cadastrarAssinante(assinante);
                    }
                    else {
                    	System.out.print("Tipo inválido");
                    }
                    break;
                case 2:
                    telefonia.listarAssinantes();
                    break;
                case 3:
                    System.out.print("Digite o CPF do assinante: ");
                    Cpf = scanner.nextLong();
                    scanner.nextLine(); //limpar o buffer
                    
                    Assinante assinante;
                    for(int i = 0; i < telefonia.numAssinantes; i++)
                	{
                		if(telefonia.assinantes[i].getCpf() == Cpf)
                		{
                			telefonia.fazerChamada(telefonia.assinantes[i]);
                			break; 
                		}
                	}
                    break;
                case 4:
                	System.out.print("Digite o CPF do assinante: ");
                    long cpf = scanner.nextLong();
                	for(int i = 0; i < telefonia.numAssinantes; i++)
                	{
                		if(telefonia.assinantes[i].getCpf() == cpf)
                		{
                			telefonia.fazerRecarga(telefonia.assinantes[i]);
                			break; 
                		}
                	}
                    break;
                case 5:
                    telefonia.imprimirFaturas();
                    break;
                case 6:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Op invalida! Tente novamente.");
                    break;
            }
        } while (opcao != 6);
    }
}
