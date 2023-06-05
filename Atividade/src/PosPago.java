import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PosPago extends Assinante{
    private float assinatura;
    private int numChamadas;

    public PosPago(float assinatura, long cpf, String nome, int numero) {
    	super(cpf, nome, numero);
        this.assinatura = assinatura;
        this.numChamadas = 0;
    }
    @Override
    public void fazerChamada(Date data, int duracao) {
        if (numChamadas < 10) {
            Chamada chamada = new Chamada(data, duracao);
            chamadas[numChamadas] = chamada;
            numChamadas++;
        } else {
            System.out.println("No h  espao para mais chamadas.");
        }
    }
    @Override
    public void imprimirFatura(int mes, int ano) {
    	System.out.println("=== Fatura PosPago ===");
    	System.out.println(toString());

        double totalChamadas = 0.0;
        
        for (int i = 0; i < numChamadas; i++)  {
        	Chamada chamada = chamadas[i];
            if (chamada.getData().getMonth() == mes - 1 && chamada.getData().getYear() == ano) {
            	System.out.println(chamada.toString());
                double custoChamada = chamada.getDuracao() * 1.04;
                System.out.println("Custo da chamada: R$" + custoChamada);
                totalChamadas += custoChamada;
            }
        }

        System.out.println("Valor da assinatura: R$" + assinatura);
        System.out.println("Valor total da fatura: R$" + (assinatura + totalChamadas));
    }
}
