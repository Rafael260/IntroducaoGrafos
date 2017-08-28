package estrutura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GrafoDirigido extends Grafo {

	public GrafoDirigido(Integer numeroVertices) {
		super(numeroVertices);
		int i = 0;
		while (i < numeroVertices) {
			adicionarVertice(new Nodo(i++));
		}
	}

	@Override
	public void adicionarAresta(Integer origem, Integer destino, Integer peso) {
		//Lista de adjacencia
		VerticeAbstrato verticeOrigem = vertices.get(origem);
		VerticeAbstrato verticeDestino = vertices.get(destino);
		verticeOrigem.addAdjacente(verticeDestino,peso);
		
		//Matriz de adjacencia
		matrizAdjacencia[origem][destino] = 1;
	}

	@Override
	public void removerAresta(Integer origem, Integer destino) {
		//Lista de adjacencia
		VerticeAbstrato verticeOrigem = vertices.get(origem);
		VerticeAbstrato verticeDestino = vertices.get(destino);
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
	public Boolean possuiCiclo(VerticeAbstrato vertice, VerticeAbstrato adjacente) {
		return adjacente.getCor() == VerticeAbstrato.CINZA;
	}

	@Override
	public Grafo clone() {
		Grafo clone = new GrafoDirigido(vertices.size());
		clone.setMatrizAdjacencia(matrizAdjacencia);
		return clone;
	}

	@Override
	public int getNumeroComponentes() {
		
		return 0;
	}
	
	private static List<Nodo> convertToNodoList(Collection<VerticeAbstrato> list){
		List<Nodo> nodos = new ArrayList<Nodo>();
		for (VerticeAbstrato v: list) {
			nodos.add((Nodo) v);
		}
		return nodos;
	}
	
	public void inicializarGraus() {
		List<Nodo> nodos = convertToNodoList(vertices);
		List<Nodo> adjacentes = new ArrayList<Nodo>();
		for (Nodo n: nodos) {
			adjacentes = convertToNodoList(n.getAdjacentes().keySet());
			for (Nodo adj: adjacentes) {
				n.incrementarGrauSaida();
				adj.incrementarGrauEntrada();
			}
		}
	}
	
	public Queue<VerticeAbstrato> getOrdenacaoTopologica(){
		inicializarGraus();
		Queue<VerticeAbstrato> filaGrauZero = new LinkedList<VerticeAbstrato>();
		Queue<VerticeAbstrato> filaSaida = new LinkedList<VerticeAbstrato>();
		List<Nodo> nodos = convertToNodoList(vertices);
		//Para cada nodo, se tiver grau zero, eh pq ja pode ser colocado na fila
		for(Nodo n: nodos) {
			if (n.getGrauEntrada() == 0) {
				filaGrauZero.add(n);
			}
		}
		//Laço principal do algoritmo
		while (!filaGrauZero.isEmpty()) {
			Nodo n = (Nodo) filaGrauZero.remove();
			filaSaida.add(n);
			for (Nodo adj: convertToNodoList(n.getAdjacentes().keySet())) {
				adj.decrementarGrauEntrada();
				if (adj.getGrauEntrada() == 0) {
					filaGrauZero.add(adj);
				}
			}
		}
		return filaSaida;
	}

}
