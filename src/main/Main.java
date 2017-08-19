package main;

import estrutura.Grafo;
import estrutura.GrafoDirigido;
import estrutura.GrafoNaoDirigido;
import estrutura.Vertice;
import exceptions.SemCaminhoException;

public class Main {

	public static void main(String[] args) {
		Vertice a = new Vertice("a");
		Vertice b = new Vertice("b");
		Vertice c = new Vertice("c");
		Vertice d = new Vertice("d");
		Vertice e = new Vertice("e");
		
		Grafo grafo = new GrafoDirigido();
		
		grafo.adicionarVertice(a);
		grafo.adicionarVertice(b);
		grafo.adicionarVertice(c);
		grafo.adicionarVertice(d);
		grafo.adicionarVertice(e);
		
		grafo.adicionarAresta(a, b);
		grafo.adicionarAresta(a, c);
		grafo.adicionarAresta(b, c);
		grafo.adicionarAresta(d, e);
		grafo.adicionarAresta(b, e);
		grafo.adicionarAresta(b, d);
		
		try {
			System.out.println(grafo.caminho(a, e));
		} catch (SemCaminhoException ex) {
			System.out.println("Sem caminho entre a e e");
		}
	}
}
