package estrutura;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import exceptions.SemCaminhoException;

public abstract class Grafo implements Cloneable{
	protected List<Vertice> vertices;
	protected int[][] matrizAdjacencia;
	
	public Grafo(Integer numeroVertices) {
		this.vertices = new ArrayList<Vertice>();
		int i = 0;
		while (i < numeroVertices) {
			adicionarVertice(new Vertice(i++));
		}
		matrizAdjacencia = new int[vertices.size()][vertices.size()];
		inicializarMatrizAdjacencia();
	}

	public void inicializarMatrizAdjacencia() {
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				matrizAdjacencia[i][j] = 0;
			}
		}
	}
	
	public int[][] getMatrizAdjacencia() {
		return matrizAdjacencia;
	}

	public void setMatrizAdjacencia(int[][] novaMatrizAdjacencia) {
		this.matrizAdjacencia = novaMatrizAdjacencia.clone();
		for (Vertice vertice: vertices) {
			vertice.getAdjacentes().clear();
			for (int j = 0; j < vertices.size(); j++) {
				if(matrizAdjacencia[vertice.getNumero()][j] != 0) {
					adicionarAresta(vertice.getNumero(), j, matrizAdjacencia[vertice.getNumero()][j]);
				}
			}
		}
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

	private void adicionarVertice(Vertice vertice) {
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
		
		//Para matriz de adjacencias
		for (int i = 0; i < vertices.size(); i++) {
			matrizAdjacencia[i][vertice.getNumero()] = 0;
			matrizAdjacencia[vertice.getNumero()][i] = 0;
		}
	}

	abstract public void adicionarAresta(Integer origem, Integer destino, Integer peso);

	abstract public void removerAresta(Integer origem, Integer destino);
	
	public void inicializarVertices() {
		for (Vertice vertice: vertices) {
			vertice.setPai(null);
			vertice.setCor(Vertice.BRANCO);
			vertice.setDistanciaRaiz(null);
		}
	}
	
	//BFS
	public void buscarEmLargura() {
		inicializarVertices();
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
		System.out.println("Raiz dessa busca é: "+ raiz.getNumero());
		filaVertices.add(raiz);
		Vertice verticeAtual;
		Map<Vertice,Integer> adjacentes;
		while(!filaVertices.isEmpty()) {
			verticeAtual = filaVertices.remove();
			
			System.out.println("Vertice visitado: "+ verticeAtual.getNumero());
			adjacentes = verticeAtual.getAdjacentes();
			for (Vertice adj: adjacentes.keySet()) {
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
//		raizSelecionada.setDistanciaRaiz(0);
		System.out.println("Raiz dessa busca é: "+ raizSelecionada.getNumero());
		buscarEm(raizSelecionada);
	}
	
	//DFS_visit
	public void buscarEm(Vertice vertice) {
		System.out.println("Vertice visitado (DFS): "+ vertice.getNumero());
		vertice.setCor(Vertice.CINZA);
		Map<Vertice,Integer> adjacentes = vertice.getAdjacentes();
		for (Vertice adj: adjacentes.keySet()) {
			if (adj.getCor() == Vertice.BRANCO) {
				adj.setPai(vertice);
				buscarEm(adj);
			}
		}
		vertice.setCor(Vertice.PRETO);
	}
	
	public Boolean ehConexo() {
		return getNumeroComponentes() == 1;
	}
	
	public Boolean ehAtingivel(Vertice u, Vertice v) {
		//Começar o DFS pelo u
		inicializarVertices();
		return ehAtingivelVisit(u,v);
	}
	
	public Boolean ehAtingivelVisit(Vertice u, Vertice v) {
		System.out.println("Vertice visitado (DFS): "+ u.getNumero());
		if (u == v) {
			return true;
		}
		u.setCor(Vertice.CINZA);
		Map<Vertice,Integer> adjacentes = u.getAdjacentes();
		for (Vertice adj: adjacentes.keySet()) {
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
				caminho += verticesCaminho.pop().getNumero() + " ";
			}
		}
		if (caminho.isEmpty()) {
			throw new SemCaminhoException("Não possui caminho de "+ u.getNumero() + " ate " + v.getNumero());
		}
		return caminho;
	}
	
	public Boolean caminhoVisit(Vertice u, Vertice v, Stack<Vertice> verticesCaminho) {
		System.out.println("Vertice visitado (DFS): "+ u.getNumero());
		if (u == v) {
			return true;
		}
		u.setCor(Vertice.CINZA);
		Map<Vertice,Integer> adjacentes = u.getAdjacentes();
		for (Vertice adj: adjacentes.keySet()) {
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
	
	abstract public Boolean possuiCiclo(Vertice vertice, Vertice adjacente);
	
	public Boolean ehCiclicoVisit(Vertice vertice) {
		System.out.println("Vertice visitado (DFS): "+ vertice.getNumero());
		vertice.setCor(Vertice.CINZA);
		Map<Vertice, Integer> adjacentes = vertice.getAdjacentes();
		for (Vertice adj: adjacentes.keySet()) {
			if (possuiCiclo(vertice,adj)) {
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
	
	abstract public int getNumeroComponentes();
	
	abstract public Grafo getMST();
	
	public String  toString() {
		String retorno = "";
		Map<Vertice,Integer> adjacentes;
		for (Vertice v: this.vertices) {
			retorno += "Vertice " + v.getNumero() + " com adjacentes: ";
			adjacentes = v.getAdjacentes();
			for (Vertice adj: adjacentes.keySet()) {
				retorno += adj.getNumero() + " ";
			}
			retorno += "\n";
		}
		return retorno;
	}
	
	public abstract Grafo clone();
	
	public Grafo getGrafoInverso() {
		inicializarVertices();
		//Crio uma cópia do grafo atual
		Grafo grafoInverso = clone();
		//Pego a matriz de adjacencia do grafo
		int[][] matrizAtual = grafoInverso.getMatrizAdjacencia();
		//Crio a matriz do grafo inverso
		int[][] novaMatriz = new int[vertices.size()][vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				//E coloco os valores do grafo inverso
				novaMatriz[i][j] = matrizAtual[j][i];
			}
		}
		//Por fim, seto a matriz de adjacencia no objeto grafo, que aplicará as alterações na lista de adjacencia
		grafoInverso.setMatrizAdjacencia(novaMatriz);
		return grafoInverso;
	}
}
