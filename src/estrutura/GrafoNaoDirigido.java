package estrutura;

public class GrafoNaoDirigido extends Grafo{

	public GrafoNaoDirigido() {
		// TODO Auto-generated constructor stub
		super();
	}
	@Override
	public void adicionarAresta(Vertice origem, Vertice destino) {
		origem.addAdjacente(destino);
		destino.addAdjacente(origem);
	}
	@Override
	public void removerAresta(Vertice origem, Vertice destino) {
		origem.removerAdjacente(destino);
		destino.removerAdjacente(origem);
	}

}
