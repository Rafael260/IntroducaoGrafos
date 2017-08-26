package estrutura;

public class GrafoDirigido extends Grafo {

	public GrafoDirigido(Integer numeroVertices) {
		super(numeroVertices);
	}

	@Override
	public void adicionarAresta(Integer origem, Integer destino, Integer peso) {
		//Lista de adjacencia
		Vertice verticeOrigem = vertices.get(origem);
		Vertice verticeDestino = vertices.get(destino);
		verticeOrigem.addAdjacente(verticeDestino,peso);
		
		//Matriz de adjacencia
		matrizAdjacencia[origem][destino] = 1;
	}

	@Override
	public void removerAresta(Integer origem, Integer destino) {
		//Lista de adjacencia
		Vertice verticeOrigem = vertices.get(origem);
		Vertice verticeDestino = vertices.get(destino);
		verticeOrigem.removerAdjacente(verticeDestino);
		
		//Matriz de adjacencia
		matrizAdjacencia[origem][destino] = 0;
	}

	@Override
	public Grafo getMST() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean possuiCiclo(Vertice vertice, Vertice adjacente) {
		return adjacente.getCor() == Vertice.CINZA;
	}

	@Override
	public Grafo clone() {
		Grafo clone = new GrafoDirigido(vertices.size());
		clone.setMatrizAdjacencia(matrizAdjacencia);
		return clone;
	}

	@Override
	public int getNumeroComponentes() {
		// TODO Auto-generated method stub
		return 0;
	}

}
