package estrutura;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.Collection;

import exceptions.SemCaminhoException;

public abstract class Grafo implements Cloneable{
	
	public static int SEARCH_TIMER = 0;
	
	protected List<VerticeAbstrato> vertices;
	protected int[][] matrizAdjacencia;
	
	public Grafo(Integer numeroVertices) {
		this.vertices = new ArrayList<VerticeAbstrato>();
		matrizAdjacencia = new int[numeroVertices][numeroVertices];
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
		for (VerticeAbstrato vertice: vertices) {
			vertice.getAdjacentes().clear();
			for (int j = 0; j < vertices.size(); j++) {
				if(matrizAdjacencia[vertice.getNumero()][j] != 0) {
					adicionarAresta(vertice.getNumero(), j, matrizAdjacencia[vertice.getNumero()][j]);
				}
			}
		}
	}

	public List<VerticeAbstrato> getVertices() {
		return vertices;
	}

	public void setVertices(List<VerticeAbstrato> vertices) {
		this.vertices = vertices;
	}

	protected void adicionarVertice(VerticeAbstrato vertice) {
		this.vertices.add(vertice);
	}

	public void removerVertice(VerticeAbstrato vertice) {
		//Remove o vertice do grafo
		this.vertices.remove(vertice);
		//E agora devemos olhar todos os demais
		for (VerticeAbstrato v: vertices) {
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
		for (VerticeAbstrato vertice: vertices) {
			vertice.setPai(null);
			vertice.setCor(VerticeAbstrato.BRANCO);
			vertice.setDistanciaRaiz(null);
		}
	}
	
	//BFS
	public void buscarEmLargura(VerticeAbstrato vertice) {
		inicializarVertices();
		//Lista de vertices visitados
		Queue<VerticeAbstrato> filaVertices = new LinkedList<VerticeAbstrato>();
		inicializarVertices();
		VerticeAbstrato raiz;
		if (vertice == null) {
			Random random = new Random();
			int indiceRaiz = random.nextInt(vertices.size());
			raiz = vertices.get(indiceRaiz);
		}
		else {
			raiz = vertice;
		}
		//Raiz ja foi visitada, logo fica com a cor cinza
		raiz.setCor(VerticeAbstrato.CINZA);
		raiz.setDistanciaRaiz(0);
		System.out.println("Raiz dessa busca é: "+ raiz.getNumero());
		filaVertices.add(raiz);
		VerticeAbstrato verticeAtual;
		Map<VerticeAbstrato,Integer> adjacentes;
		while(!filaVertices.isEmpty()) {
			verticeAtual = filaVertices.remove();
			
			System.out.println("Vertice visitado: "+ verticeAtual.getNumero());
			adjacentes = verticeAtual.getAdjacentes();
			for (VerticeAbstrato adj: adjacentes.keySet()) {
				if (adj.getCor() == VerticeAbstrato.BRANCO) {
					adj.setCor(VerticeAbstrato.CINZA);
					adj.setDistanciaRaiz(verticeAtual.getDistanciaRaiz()+1);
					adj.setPai(verticeAtual);
					filaVertices.add(adj);
				}
			}
			verticeAtual.setCor(VerticeAbstrato.PRETO);
		}
	}
	
	//DFS
	public void buscarEmProfundidade(VerticeAbstrato raiz) {
		inicializarVertices();
		SEARCH_TIMER = 0;
		//Precisamos começar de um vertice, que tal escolher um aleatorio?
		Random random = new Random();
		VerticeAbstrato raizSelecionada;
		if (raiz == null) {
			int indiceRaiz = random.nextInt(vertices.size());
			raizSelecionada = vertices.get(indiceRaiz);
		}
		else {
			raizSelecionada = raiz;
		}
		//Raiz ja foi visitada, logo fica com a cor cinza
		raizSelecionada.setCor(VerticeAbstrato.CINZA);
//		raizSelecionada.setDistanciaRaiz(0);
		System.out.println("Raiz dessa busca é: "+ raizSelecionada.getNumero());
		buscarEm(raizSelecionada);
	}
	
	//DFS_visit
	public void buscarEm(VerticeAbstrato vertice) {
		System.out.println("Vertice visitado (DFS): "+ vertice.getNumero());
		vertice.setCor(VerticeAbstrato.CINZA);
		Map<VerticeAbstrato,Integer> adjacentes = vertice.getAdjacentes();
		for (VerticeAbstrato adj: adjacentes.keySet()) {
			if (adj.getCor() == VerticeAbstrato.BRANCO) {
				adj.setPai(vertice);
				buscarEm(adj);
			}
		}
		vertice.setTempoPosWork(SEARCH_TIMER++);
		vertice.setCor(VerticeAbstrato.PRETO);
	}
	
	abstract public Boolean ehConexo();
	
	public Boolean ehAtingivelByProlo(VerticeAbstrato u, VerticeAbstrato v) {
		inicializarVertices();
		buscarEmLargura(u);
		return v.getCor() != VerticeAbstrato.BRANCO;
		
	}
	
	public Boolean ehAtingivel(VerticeAbstrato u, VerticeAbstrato v) {
		//Começar o DFS pelo u
		inicializarVertices();
		return ehAtingivelVisit(u,v);
	}
	
	public Boolean ehAtingivelVisit(VerticeAbstrato u, VerticeAbstrato v) {
		System.out.println("Vertice visitado (DFS): "+ u.getNumero());
		if (u == v) {
			return true;
		}
		u.setCor(VerticeAbstrato.CINZA);
		Map<VerticeAbstrato,Integer> adjacentes = u.getAdjacentes();
		for (VerticeAbstrato adj: adjacentes.keySet()) {
			if (adj.getCor() == VerticeAbstrato.BRANCO) {
				adj.setPai(u);
				if(ehAtingivelVisit(adj,v)) {
					return true;
				}
			}
		}
		v.setCor(VerticeAbstrato.PRETO);
		return false;
	}
	
	public String caminho(VerticeAbstrato u, VerticeAbstrato v) throws SemCaminhoException {
		inicializarVertices();
		Stack<VerticeAbstrato> verticesCaminho = new Stack<VerticeAbstrato>();
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
	
	public Boolean caminhoVisit(VerticeAbstrato u, VerticeAbstrato v, Stack<VerticeAbstrato> verticesCaminho) {
		System.out.println("Vertice visitado (DFS): "+ u.getNumero());
		if (u == v) {
			return true;
		}
		u.setCor(VerticeAbstrato.CINZA);
		Map<VerticeAbstrato,Integer> adjacentes = u.getAdjacentes();
		for (VerticeAbstrato adj: adjacentes.keySet()) {
			if (adj.getCor() == VerticeAbstrato.BRANCO) {
				adj.setPai(u);
				if(caminhoVisit(adj,v,verticesCaminho)) {
					verticesCaminho.push(adj);
					return true;
				}
			}
		}
		v.setCor(VerticeAbstrato.PRETO);
		return false;
	}
	
	public Boolean ehCiclico() {
		inicializarVertices();
		for (VerticeAbstrato v: this.vertices)
			if(v.getCor() == VerticeAbstrato.BRANCO) {
				if (ehCiclicoVisit(v)) {
					return true;
				}
			}
		return false;
	}
	
	abstract public Boolean possuiCiclo(VerticeAbstrato vertice, VerticeAbstrato adjacente);
	
	public Boolean ehCiclicoVisit(VerticeAbstrato vertice) {
		System.out.println("Vertice visitado (DFS): "+ vertice.getNumero());
		vertice.setCor(VerticeAbstrato.CINZA);
		Map<VerticeAbstrato, Integer> adjacentes = vertice.getAdjacentes();
		for (VerticeAbstrato adj: adjacentes.keySet()) {
			if (possuiCiclo(vertice,adj)) {
				return true;
			}
			if (adj.getCor() == VerticeAbstrato.BRANCO) {
				adj.setPai(vertice);
				if (ehCiclicoVisit(adj)) {
					return true;
				}
			}
		}
		vertice.setCor(VerticeAbstrato.PRETO);
		return false;
	}
	
	abstract public Collection<Collection<VerticeAbstrato>> getComponentes();
	
	protected Collection<VerticeAbstrato> getComponente(VerticeAbstrato vertice){
		Collection<VerticeAbstrato> componente = new ArrayList<VerticeAbstrato>();
		coletarVerticesComponente(vertice, componente);
		return componente;
	}
	
	protected void coletarVerticesComponente(VerticeAbstrato vertice, Collection<VerticeAbstrato> componente) {
		System.out.println("Vertice visitado (DFS): "+ vertice.getNumero());
		vertice.setCor(VerticeAbstrato.CINZA);
		componente.add(vertice);
		Map<VerticeAbstrato,Integer> adjacentes = vertice.getAdjacentes();
		for (VerticeAbstrato adj: adjacentes.keySet()) {
			if (adj.getCor() == VerticeAbstrato.BRANCO) {
				adj.setPai(vertice);
				coletarVerticesComponente(adj,componente);
			}
		}
		vertice.setCor(VerticeAbstrato.PRETO);
	}
	
	abstract public Grafo getMST();
	
	public String  toString() {
		String retorno = "";
		Map<VerticeAbstrato,Integer> adjacentes;
		for (VerticeAbstrato v: this.vertices) {
			retorno += "Vertice " + v.getNumero() + " com adjacentes: ";
			adjacentes = v.getAdjacentes();
			for (VerticeAbstrato adj: adjacentes.keySet()) {
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
	
	public void imprimirComponentes() {
		Collection<Collection<VerticeAbstrato>> componentes = getComponentes();
		for (Collection<VerticeAbstrato> componente: componentes) {
			System.out.println("Componente:");
			for(VerticeAbstrato vertice: componente) {
				System.out.print(vertice + " ");
			}
			System.out.println();
			System.out.println();
		}
	}
}
