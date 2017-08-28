package estrutura;

public class Aresta {
	private VerticeAbstrato origem;
	private VerticeAbstrato destino;
	private Integer peso;
	
	public Aresta(VerticeAbstrato origem, VerticeAbstrato destino, Integer peso) {
		this.origem = origem;
		this.destino = destino;
		this.peso = peso;
	}

	public VerticeAbstrato getOrigem() {
		return origem;
	}

	public void setOrigem(VerticeAbstrato origem) {
		this.origem = origem;
	}

	public VerticeAbstrato getDestino() {
		return destino;
	}

	public void setDestino(VerticeAbstrato destino) {
		this.destino = destino;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
}
