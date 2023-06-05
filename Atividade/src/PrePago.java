
import java.util.Date;
import java.util.GregorianCalendar;

public class PrePago extends Assinante {
    private Recarga[] recargas;
    private int numRecargas;
    private float creditos;

    public PrePago(long cpf, String nome, int numero) {
        super(cpf, nome, numero);
        recargas = new Recarga[100];
        numRecargas = 0;
        creditos = 0;
    }
    @Override
    public void fazerChamada(Date data, int duracao) {
        if (numChamadas < 100 && creditos >= 1.45 * duracao) {
            Chamada chamada = new Chamada(data, duracao);
            chamadas[numChamadas] = chamada;
            numChamadas++;
            creditos -= 1.45 * duracao;
        } else {
            System.out.println("Não foi possvel fazer a chamada. Verifique se há espaço para armazenar a chamada e se há creditos suficientes.");
        }
    }

    public void recarregar(Date data, float valor) {
        if (getNumRecargas() < 100) {
            Recarga recarga = new Recarga(data, valor);
            recargas[getNumRecargas()] = recarga;
            numRecargas = getNumRecargas() + 1;
            creditos += valor;
            System.out.println("Recarga realizada");
        } else {
            System.out.println("Não é possvel fazer mais recargas. O limite maximo de recargas foi atingido.");
        }
    }
    @Override
    public void imprimirFatura(int mes, int ano) {
        System.out.println("=== Fatura PrePago ===");
        System.out.println(toString());

        float totalChamadas = 0;
        float totalRecargas = 0;
        for (int i = 0; i < numChamadas; i++) {
            Chamada chamada = chamadas[i];
            if (chamada.getData().getMonth() == mes - 1 && chamada.getData().getYear() == ano){
                totalChamadas += 1.45 * chamada.getDuracao();
                System.out.println(chamada.toString() + ", valor: " + 1.45 * chamada.getDuracao());
            }
        }
        for (int i = 0; i < getNumRecargas(); i++) {
            Recarga recarga = recargas[i];
            if (recarga.getData().getMonth() == mes - 1) {
                totalRecargas += recarga.getValor();
                System.out.println(recarga.toString());
            }
        }

        System.out.println("Total de chamadas: " + totalChamadas);
        System.out.println("Total de recargas: " + totalRecargas);
        System.out.println("Creditos: " + creditos);
        if((totalChamadas - totalRecargas) > 0) {
        	System.out.println("Total a pagar: " + (totalChamadas - totalRecargas));
        }
        else {
        	System.out.println("Total a pagar: 0");
        }
    }
	public int getNumRecargas() {
		return numRecargas;
	}
}