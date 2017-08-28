package estrutura;

public class Nodo extends VerticeAbstrato {

	private int grauEntrada;
	private int grauSaida;
	
	public Nodo(Integer numero) {
		super(numero);
		grauEntrada = 0;
		grauSaida = 0;
	}

	public int getGrauEntrada() {
		return grauEntrada;
	}

	public void setGrauEntrada(int grauEntrada) {
		this.grauEntrada = grauEntrada;
	}

	public int getGrauSaida() {
		return grauSaida;
	}

	public void setGrauSaida(int grauSaida) {
		this.grauSaida = grauSaida;
	}
	
	public void incrementarGrauEntrada() {
		this.grauEntrada++;
	}
	
	public void decrementarGrauEntrada() {
		this.grauEntrada--;
	}
	
	public void incrementarGrauSaida() {
		this.grauSaida++;
	}
	
	public void decrementarGrauSaida() {
		this.grauSaida--;
	}
}
