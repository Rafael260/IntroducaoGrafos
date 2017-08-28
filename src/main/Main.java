package main;

import java.util.Collection;

import estrutura.Grafo;
import estrutura.GrafoDirigido;
import estrutura.GrafoNaoDirigido;
import estrutura.VerticeAbstrato;
import exceptions.SemCaminhoException;

public class Main {

	public static void main(String[] args) {
		GrafoDirigido grafo = new GrafoDirigido(5);
		
		grafo.adicionarAresta(0, 1,null);
		grafo.adicionarAresta(0, 2,null);
		grafo.adicionarAresta(3, 4, null);
		grafo.adicionarAresta(4, 2, null);
		Collection<VerticeAbstrato> ordenacao = grafo.getOrdenacaoTopologica();
		for (VerticeAbstrato v: ordenacao) {
			System.out.print(v + " ");
		}
	}
}
