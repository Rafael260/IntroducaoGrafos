package estrutura;

import java.util.HashSet;
import java.util.Set;

public class Vertice {
	//marcacoes para algoritmos de percorrer o grafo
	public static final int BRANCO = 0;
	public static final int CINZA = 1;
	public static final int PRETO = 2;
	
	private String descricao;
	private Set<Vertice> adjacentes;
	private Integer cor;
	private Vertice pai;
	private Integer distanciaRaiz;
	public Vertice(String descricao){
		this.descricao = descricao;
		adjacentes = new HashSet<Vertice>();
		cor = BRANCO;
		pai = null;
		distanciaRaiz = null;
	}
	
	public Set<Vertice> getAdjacentes(){
		return adjacentes;
	}
	public String getDescricao(){
		return descricao;
	}
	public Integer getCor() {
		return cor;
	}
	public void setCor(Integer cor) {
		if (cor == BRANCO || cor == CINZA || cor == PRETO) {
			this.cor = cor;
		}
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setAdjacentes(Set<Vertice> adjacentes) {
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

	public Boolean addAdjacente(Vertice vertice){
		int numAdjacentesAntigo = adjacentes.size();
		adjacentes.add(vertice);
		return adjacentes.size() > numAdjacentesAntigo;
	}
	
	public Boolean removerAdjacente(Vertice vertice) {
		int numAdjacentesAntigo = adjacentes.size();
		adjacentes.remove(vertice);
		return adjacentes.size() < numAdjacentesAntigo;
	}
	
	public Boolean possuiAdjacente(Vertice vertice) {
		return this.adjacentes.contains(vertice);
	}
}
