package estrutura;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Grafo {
	private List<Vertice> vertices;
	private boolean dirigido;

	public Grafo(boolean dirigido) {
		this.vertices = new ArrayList<Vertice>();
		this.dirigido = dirigido;
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

	public boolean isDirigido() {
		return dirigido;
	}

	public void setDirigido(boolean ehDirigido) {
		this.dirigido = ehDirigido;
	}

	public void adicionarVertice(Vertice vertice) {
		this.vertices.add(vertice);
	}

	public void removerVertice(Vertice vertice) {
		//Remove o vertice do grafo
		this.vertices.remove(vertice);
		//E agora devemos olhar todos os demais
		for (Vertice v: vertices) {
			//Se caso o vertice estiver ligado com o removido, devemos tirar a aresta
			if (v.possuiAdjacente(vertice)) {
				v.removerAdjacente(vertice);
			}
		}
	}

	public void adicionarAresta(Vertice origem, Vertice destino) {
		origem.addAdjacente(destino);
		// Se nao for dirigido, tanto a ida como a volta tem q valer
		if (!dirigido) {
			destino.addAdjacente(origem);
		}
	}

	public void removerAresta(Vertice origem, Vertice destino) {
		origem.removerAdjacente(destino);
		if (!dirigido) {
			destino.removerAdjacente(origem);
		}
	}
	
	public void inicializarVertices() {
		for (Vertice vertice: vertices) {
			vertice.setPai(null);
			vertice.setCor(Vertice.BRANCO);
			vertice.setDistanciaRaiz(null);
		}
	}
	
	//BFS
	public void buscarEmLargura() {
		//Lista de vertices visitados
		Queue<Vertice> filaVertices = new LinkedList<Vertice>();
		inicializarVertices();
		//Precisamos começar de um vertice, que tal escolher um aleatorio?
		Random random = new Random();
		int indiceRaiz = random.nextInt(vertices.size());
		Vertice raiz = vertices.get(indiceRaiz);
		//Raiz ja foi visitada, logo fica com a cor cinza
		raiz.setCor(Vertice.CINZA);
		raiz.setDistanciaRaiz(0);
		System.out.println("Raiz dessa busca é: "+ raiz.getDescricao());
		filaVertices.add(raiz);
		Vertice verticeAtual;
		while(!filaVertices.isEmpty()) {
			verticeAtual = filaVertices.remove();
			
			System.out.println("Vertice visitado: "+ verticeAtual.getDescricao());
			for (Vertice adj: verticeAtual.getAdjacentes()) {
				if (adj.getCor() == Vertice.BRANCO) {
					adj.setCor(Vertice.CINZA);
					adj.setDistanciaRaiz(verticeAtual.getDistanciaRaiz()+1);
					adj.setPai(verticeAtual);
					filaVertices.add(adj);
				}
			}
			verticeAtual.setCor(Vertice.PRETO);
		}
	}
	
	//DFS
	public void buscarEmProfundidade() {
		inicializarVertices();
		//Precisamos começar de um vertice, que tal escolher um aleatorio?
		Random random = new Random();
		int indiceRaiz = random.nextInt(vertices.size());
		Vertice raiz = vertices.get(indiceRaiz);
		//Raiz ja foi visitada, logo fica com a cor cinza
		raiz.setCor(Vertice.CINZA);
		raiz.setDistanciaRaiz(0);
		System.out.println("Raiz dessa busca é: "+ raiz.getDescricao());
		buscarEm(raiz);
	}
	
	//DFS_visit
	public void buscarEm(Vertice vertice) {
		System.out.println("Vertice visitado (DFS): "+ vertice.getDescricao());
		vertice.setCor(Vertice.CINZA);
		for (Vertice adj: vertice.getAdjacentes()) {
			if (adj.getCor() == Vertice.BRANCO) {
				adj.setPai(vertice);
				buscarEm(adj);
			}
		}
		vertice.setCor(Vertice.PRETO);
	}
	
	//Para grafos dirigidos, eh verdadeiro se for FORTEMENTE conexo
	public Boolean ehConexo() {
		buscarEmProfundidade();
		for (Vertice vertice: vertices) {
			if (vertice.getCor() == Vertice.BRANCO) {
				return false;
			}
		}
		return true;
	}
	
	public String  toString() {
		String retorno = dirigido ? "Grafo dirigido\n\n" : "Grafo não dirigido\n\n";
		for (Vertice v: this.vertices) {
			retorno += "Vertice " + v.getDescricao() + " com adjacentes: ";
			for (Vertice adj: v.getAdjacentes()) {
				retorno += adj.getDescricao() + " ";
			}
			retorno += "\n";
		}
		return retorno;
	}
}
