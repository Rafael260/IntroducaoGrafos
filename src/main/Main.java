package main;

import estrutura.Grafo;
import estrutura.Vertice;

public class Main {

	public static void main(String[] args) {
		Vertice no1 = new Vertice("no1");
		Vertice no2 = new Vertice("no2");
		Vertice no3 = new Vertice("no3");
		Vertice no4 = new Vertice("no4");
		Vertice no5 = new Vertice("no5");
		
		Grafo grafo = new Grafo(false);
		
		grafo.adicionarVertice(no1);
		grafo.adicionarVertice(no2);
		grafo.adicionarVertice(no3);
		grafo.adicionarVertice(no4);
		grafo.adicionarVertice(no5);
		
		grafo.adicionarAresta(no1, no3);
		grafo.adicionarAresta(no2, no3);
		grafo.adicionarAresta(no2, no4);
		grafo.adicionarAresta(no3, no4);
		grafo.adicionarAresta(no3, no5);
		grafo.adicionarAresta(no4, no5);
		
		System.out.println(grafo);
		System.out.println();
//		grafo.buscarEmLargura();
		System.out.println(grafo.ehConexo() ? "Conexo": "Não conexo");
	}

}
