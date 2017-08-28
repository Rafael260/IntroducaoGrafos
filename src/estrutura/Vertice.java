package estrutura;

public class Vertice extends VerticeAbstrato {

	private int grau;
	
	public Vertice(Integer numero) {
		super(numero);
		grau = 0;
	}

	public int getGrau() {
		return grau;
	}

	public void setGrau(int grau) {
		this.grau = grau;
	}
}
