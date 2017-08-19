package estrutura;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import exceptions.SemCaminhoException;

public abstract class Grafo {
	protected List<Vertice> vertices;

	public Grafo() {
		this.vertices = new ArrayList<Vertice>();
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
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

	abstract public void adicionarAresta(Vertice origem, Vertice destino);

	abstract public void removerAresta(Vertice origem, Vertice destino);
	
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
	public void buscarEmProfundidade(Vertice raiz) {
		inicializarVertices();
		//Precisamos começar de um vertice, que tal escolher um aleatorio?
		Random random = new Random();
		Vertice raizSelecionada;
		if (raiz == null) {
			int indiceRaiz = random.nextInt(vertices.size());
			raizSelecionada = vertices.get(indiceRaiz);
		}
		else {
			raizSelecionada = raiz;
		}
		//Raiz ja foi visitada, logo fica com a cor cinza
		raizSelecionada.setCor(Vertice.CINZA);
		raizSelecionada.setDistanciaRaiz(0);
		System.out.println("Raiz dessa busca é: "+ raizSelecionada.getDescricao());
		buscarEm(raizSelecionada);
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
		buscarEmProfundidade(null);
		for (Vertice vertice: vertices) {
			if (vertice.getCor() == Vertice.BRANCO) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean ehAtingivel(Vertice u, Vertice v) {
		//Começar o DFS pelo u
		inicializarVertices();
		return ehAtingivelVisit(u,v);
	}
	
	public Boolean ehAtingivelVisit(Vertice u, Vertice v) {
		System.out.println("Vertice visitado (DFS): "+ u.getDescricao());
		if (u == v) {
			return true;
		}
		u.setCor(Vertice.CINZA);
		for (Vertice adj: u.getAdjacentes()) {
			if (adj.getCor() == Vertice.BRANCO) {
				adj.setPai(u);
				if(ehAtingivelVisit(adj,v)) {
					return true;
				}
			}
		}
		v.setCor(Vertice.PRETO);
		return false;
	}
	
	public String caminho(Vertice u, Vertice v) throws SemCaminhoException {
		inicializarVertices();
		Stack<Vertice> verticesCaminho = new Stack<Vertice>();
		String caminho = "";
		if(caminhoVisit(u,v,verticesCaminho)) {
			verticesCaminho.push(u);
			while(!verticesCaminho.isEmpty()) {
				caminho += verticesCaminho.pop().getDescricao() + " ";
			}
		}
		if (caminho.isEmpty()) {
			throw new SemCaminhoException("Não possui caminho de "+ u.getDescricao() + " ate " + v.getDescricao());
		}
		return caminho;
	}
	
	public Boolean caminhoVisit(Vertice u, Vertice v, Stack<Vertice> verticesCaminho) {
		System.out.println("Vertice visitado (DFS): "+ u.getDescricao());
		if (u == v) {
			return true;
		}
		u.setCor(Vertice.CINZA);
		for (Vertice adj: u.getAdjacentes()) {
			if (adj.getCor() == Vertice.BRANCO) {
				adj.setPai(u);
				if(caminhoVisit(adj,v,verticesCaminho)) {
					verticesCaminho.push(adj);
					return true;
				}
			}
		}
		v.setCor(Vertice.PRETO);
		return false;
	}
	
	public Boolean ehCiclico() {
		inicializarVertices();
		for (Vertice v: this.vertices)
			if(v.getCor() == Vertice.BRANCO) {
				if (ehCiclicoVisit(v)) {
					return true;
				}
			}
		return false;
	}
	
	public Boolean ehCiclicoVisit(Vertice vertice) {
		System.out.println("Vertice visitado (DFS): "+ vertice.getDescricao());
		vertice.setCor(Vertice.CINZA);

		for (Vertice adj: vertice.getAdjacentes()) {
			if (adj.getCor() == Vertice.CINZA && adj != vertice.getPai()) {
				return true;
			}
			if (adj.getCor() == Vertice.BRANCO) {
				adj.setPai(vertice);
				if (ehCiclicoVisit(adj)) {
					return true;
				}
			}
		}
		vertice.setCor(Vertice.PRETO);
		return false;
	}
	
	public String  toString() {
		String retorno = "";
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
