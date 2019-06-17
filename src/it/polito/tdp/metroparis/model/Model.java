package it.polito.tdp.metroparis.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	//deafultedge perché il grafo è non pesato
	private Graph<Fermata, DefaultEdge> grafo;
	private MetroDAO md= new MetroDAO();
	private List<Fermata> fermate;
	
	
	
	public Graph<Fermata, DefaultEdge> getGrafo() {
		return grafo;
	}



	public List<Fermata> getFermate() {
		return fermate;
	}



	public void creaGrafo() {
		this.grafo= new SimpleDirectedGraph<>(DefaultEdge.class);
		//aggiungo i vertici
		fermate= md.getAllFermate();
		Graphs.addAllVertices(grafo, fermate);
		//aggiungo gli archi
		for(Fermata partenza: this.grafo.vertexSet()) {
			for(Fermata arrivo: this.grafo.vertexSet()) {
				//tutte le possibili combinazioni 
				if(md.esisteConnessione(partenza, arrivo)) {
					this.grafo.addEdge(partenza, arrivo);
				}
			}
			
		}
//		for(Fermata partenza: this.grafo.vertexSet()) {
//			List<Fermata> arrivi =md.stazioniArrivo(partenza);
//			
//			for(Fermata arrivo: arrivi) {
//				for(Fermata f: fermate) {
//				if(f.equals(arrivo)) {
//					nuova= f;
//				}
//				this.grafo.addEdge(partenza,nuova);
//				}
//			}
//			
//		}
		
	}
}
