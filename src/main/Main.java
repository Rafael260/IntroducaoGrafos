package main;

import estrutura.Grafo;
import estrutura.GrafoDirigido;
import estrutura.GrafoNaoDirigido;
import estrutura.Vertice;
import exceptions.SemCaminhoException;

public class Main {

	public static void main(String[] args) {
		Grafo grafo = new GrafoDirigido(3);
		
		grafo.adicionarAresta(0, 1,null);
		grafo.adicionarAresta(1, 2,null);
		grafo.adicionarAresta(0, 2,null);
		
		Grafo inverso = grafo.getGrafoInverso();
		
		System.out.println("Grafo original " + (grafo.ehCiclico() ? "tem ciclo." : "nao tem ciclo."));
		System.out.println("Grafo inverso " + (inverso.ehCiclico() ? "tem ciclo." : "nao tem ciclo."));
	}
}
