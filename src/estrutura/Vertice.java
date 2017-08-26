package estrutura;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Vertice implements Cloneable{
	//marcacoes para algoritmos de percorrer o grafo
	public static final int BRANCO = 0;
	public static final int CINZA = 1;
	public static final int PRETO = 2;
	
	private Integer numero;
	private Map<Vertice,Integer> adjacentes;
	
	private Integer cor;
	private Vertice pai;
	private Integer distanciaRaiz;
	
	public Vertice(Integer numero){
		this.numero = numero;
		adjacentes = new HashMap<Vertice,Integer>();
		cor = BRANCO;
		pai = null;
		distanciaRaiz = null;
	}
	
	public Map<Vertice,Integer> getAdjacentes(){
		return adjacentes;
	}
	public Integer getNumero(){
		return this.numero;
	}
	public Integer getCor() {
		return cor;
	}
	public void setCor(Integer cor) {
		if (cor == BRANCO || cor == CINZA || cor == PRETO) {
			this.cor = cor;
		}
	}
	public void setDescricao(Integer numero) {
		this.numero = numero;
	}
	public void setAdjacentes(Map<Vertice,Integer> adjacentes) {
		this.adjacentes = adjacentes;
	}
	
	public Vertice getPai() {
		return pai;
	}

	public void setPai(Vertice pai) {
		this.pai = pai;
	}

	public Integer getDistanciaRaiz() {
		return distanciaRaiz;
	}

	public void setDistanciaRaiz(Integer distanciaRaiz) {
		this.distanciaRaiz = distanciaRaiz;
	}

	public Boolean addAdjacente(Vertice vertice, Integer peso){
		int numAdjacentesAntigo = adjacentes.size();
		adjacentes.put(vertice, peso != null? peso: 1);
		return adjacentes.size() > numAdjacentesAntigo;
	}
	
	public Boolean removerAdjacente(Vertice vertice) {
		int numAdjacentesAntigo = adjacentes.size();
		adjacentes.remove(vertice);
		return adjacentes.size() < numAdjacentesAntigo;
	}
	
	public Boolean possuiAdjacente(Vertice vertice) {
		return this.adjacentes.containsKey(vertice);
	}
}
