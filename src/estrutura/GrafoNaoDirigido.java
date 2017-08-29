package estrutura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class GrafoNaoDirigido extends Grafo{

	public GrafoNaoDirigido(Integer numeroVertices) {
		super(numeroVertices);
		int i = 0;
		while (i < numeroVertices) {
			adicionarVertice(new Vertice(i++));
		}
	}
	
	@Override
	public void adicionarAresta(Integer origem, Integer destino, Integer peso) {
		int pesoAresta = peso != null ? peso : 1;
		
		//Lista de adjacencia
		VerticeAbstrato verticeOrigem = vertices.get(origem);
		VerticeAbstrato verticeDestino = vertices.get(destino);
		verticeOrigem.addAdjacente(verticeDestino,pesoAresta);
		verticeDestino.addAdjacente(verticeOrigem,pesoAresta);
		
		//Matriz de adjacencia
		matrizAdjacencia[origem][destino] = pesoAresta;
		matrizAdjacencia[destino][origem] = pesoAresta;
		
	}
	@Override
	public void removerAresta(Integer origem, Integer destino) {
		//Lista de adjacencia
		VerticeAbstrato verticeOrigem = vertices.get(origem);
		VerticeAbstrato verticeDestino = vertices.get(destino);
		verticeOrigem.removerAdjacente(verticeDestino);
		verticeDestino.removerAdjacente(verticeOrigem);
		
		//Matriz de adjacencia
		matrizAdjacencia[origem][destino] = 0;
		matrizAdjacencia[destino][origem] = 0;
		
	}
	
	@Override
	public Grafo getMST() {
		return null;
	}
	@Override
	public Boolean possuiCiclo(VerticeAbstrato vertice, VerticeAbstrato adjacente) {
		return adjacente.getCor() == VerticeAbstrato.CINZA && adjacente != vertice.getPai();
	}

	@Override
	public Grafo clone() {
		inicializarVertices();
		Grafo clone = new GrafoNaoDirigido(vertices.size());
		clone.setMatrizAdjacencia(matrizAdjacencia);
		return clone;
	}

	@Override
	public Collection<Collection<VerticeAbstrato>> getComponentes() {
		inicializarVertices();
		Collection<Collection<VerticeAbstrato>> componentes = new ArrayList<Collection<VerticeAbstrato>>();
		for (VerticeAbstrato vertice: vertices) {
			if (vertice.getCor() == VerticeAbstrato.BRANCO) {
				componentes.add(getComponente(vertice));
			}
		}
		return componentes;
	}
	
	@Override
	public Boolean ehConexo() {
		buscarEmLargura(null);
		for (VerticeAbstrato vertice: vertices) {
			if (vertice.getCor() == VerticeAbstrato.BRANCO) {
				return false;
			}
		}
		return true;
	}
	
}
