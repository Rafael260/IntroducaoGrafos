package main;

import java.util.Collection;

import estrutura.Grafo;
import estrutura.GrafoDirigido;
import estrutura.GrafoNaoDirigido;
import estrutura.VerticeAbstrato;
import exceptions.SemCaminhoException;

public class Main {

	public static void main(String[] args) {
		Grafo grafo = new GrafoNaoDirigido(8);
		
		//https://upload.wikimedia.org/wikipedia/commons/5/5c/Scc.png
		grafo.adicionarAresta(0, 1,null);
		grafo.adicionarAresta(1, 2,null);
		grafo.adicionarAresta(2, 3, null);
		grafo.adicionarAresta(3, 2, null);
		grafo.adicionarAresta(3, 7, null);
		grafo.adicionarAresta(7, 3, null);
		grafo.adicionarAresta(1, 4, null);
		grafo.adicionarAresta(1, 5, null);
		grafo.adicionarAresta(4, 0, null);
		grafo.adicionarAresta(4, 5, null);
		grafo.adicionarAresta(5, 6, null);
		grafo.adicionarAresta(6, 5, null);
		grafo.adicionarAresta(7, 6, null);
		grafo.adicionarAresta(2, 6, null);
		
		grafo.imprimirComponentes();
	}
}
