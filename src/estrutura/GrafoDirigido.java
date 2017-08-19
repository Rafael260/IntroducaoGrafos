package estrutura;

public class GrafoDirigido extends Grafo {

	public GrafoDirigido() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) {
		origem.addAdjacente(destino);
	}

	@Override
	public void removerAresta(Vertice origem, Vertice destino) {
		origem.removerAdjacente(destino);
	}

}
