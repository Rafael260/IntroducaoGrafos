package estrutura;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class VerticeAbstrato implements Cloneable, Comparable<VerticeAbstrato>{
	//marcacoes para algoritmos de percorrer o grafo
	public static final int BRANCO = 0;
	public static final int CINZA = 1;
	public static final int PRETO = 2;
	
	private Integer numero;
	private Map<VerticeAbstrato,Integer> adjacentes;
	
	private Integer cor;
	private VerticeAbstrato pai;
	private Integer distanciaRaiz;
	
	private Integer tempoPosWork;
	
	public VerticeAbstrato(Integer numero){
		this.numero = numero;
		adjacentes = new HashMap<VerticeAbstrato,Integer>();
		cor = BRANCO;
		pai = null;
		distanciaRaiz = null;
		tempoPosWork = 0;
	}
	
	public Map<VerticeAbstrato,Integer> getAdjacentes(){
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
	public void setAdjacentes(Map<VerticeAbstrato,Integer> adjacentes) {
		this.adjacentes = adjacentes;
	}
	
	public VerticeAbstrato getPai() {
		return pai;
	}

	public void setPai(VerticeAbstrato pai) {
		this.pai = pai;
	}

	public Integer getDistanciaRaiz() {
		return distanciaRaiz;
	}

	public void setDistanciaRaiz(Integer distanciaRaiz) {
		this.distanciaRaiz = distanciaRaiz;
	}
	
	public Integer getTempoPosWork() {
		return tempoPosWork;
	}

	public void setTempoPosWork(Integer tempoPosWork) {
		this.tempoPosWork = tempoPosWork;
	}

	public Boolean addAdjacente(VerticeAbstrato vertice, Integer peso){
		int numAdjacentesAntigo = adjacentes.size();
		adjacentes.put(vertice, peso != null? peso: 1);
		return adjacentes.size() > numAdjacentesAntigo;
	}
	
	public Boolean removerAdjacente(VerticeAbstrato vertice) {
		int numAdjacentesAntigo = adjacentes.size();
		adjacentes.remove(vertice);
		return adjacentes.size() < numAdjacentesAntigo;
	}
	
	public Boolean possuiAdjacente(VerticeAbstrato vertice) {
		return this.adjacentes.containsKey(vertice);
	}
	
	public String toString() {
		return this.numero.toString();
	}
	
	//Usando a ordem decrescente do tempo do pos work
	public int compareTo(VerticeAbstrato other) {
		return other.getTempoPosWork().compareTo(this.tempoPosWork);
	}
}
