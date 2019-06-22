package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata, DefaultWeightedEdge> grafo ;
	private List<Fermata> fermate ;
	private Map<Integer, Fermata> fermateIdMap ;
	
	public void creaGrafo() {
		
		// Crea l'oggetto grafo
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class) ;
		
		// Aggiungi i vertici 
		MetroDAO dao = new MetroDAO() ;
		this.fermate = dao.getAllFermate() ;
		
		// crea idMap
		this.fermateIdMap = new HashMap<>() ;
		for(Fermata f: this.fermate)
			fermateIdMap.put(f.getIdFermata(), f) ;
		
		Graphs.addAllVertices(this.grafo, this.fermate) ;
		
		// Aggiungi gli archi (opzione 1)
		/*
		for( Fermata partenza : this.grafo.vertexSet() ) {
//			System.out.print(partenza.getIdFermata()+" ");
			for( Fermata arrivo: this.grafo.vertexSet() ) {
				
				if(dao.esisteConnessione(partenza, arrivo)) {
					this.grafo.addEdge(partenza, arrivo) ;
				}
				
			}
		}
		*/
		
		
		// Aggiungi gli archi (opzione 2)
		
//		for( Fermata partenza : this.grafo.vertexSet() ) {
//			List<Fermata> arrivi = dao.stazioniArrivo(partenza, fermateIdMap) ;
//			
//			for(Fermata arrivo: arrivi) 
//				this.grafo.addEdge(partenza, arrivo) ;
//		}
		
		
		// Aggiungi il peso agli archi
		List<ConnessioneVelocita> archipesati= dao.getConnessioneVelocita();
		for(ConnessioneVelocita sv: archipesati) {
			Fermata partenza= this.fermateIdMap.get(sv.getStazP());
			Fermata arrivo= this.fermateIdMap.get(sv.getStazA());
			double distanza= LatLngTool.distance(partenza.getCoords(), arrivo.getCoords(), LengthUnit.KILOMETER);
			double peso= distanza/sv.getVelocita() * 3600; //in secondi 
			//grafo.setEdgeWeight(partenza, arrivo , peso);
			//grafo.setEdgeWeight(partenza, arrivo, peso);
			Graphs.addEdgeWithVertices(grafo, partenza, arrivo, peso);
		}


		
		
	}

	public Graph<Fermata, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<Fermata> getFermate() {
		return fermate;
	}
	
	
	
	public Map<Integer, Fermata> getFermateIdMap() {
		return fermateIdMap;
	}

	public List<Fermata> trovaCamminomMinimo(Fermata partenza, Fermata arrivo){
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> sp= new DijkstraShortestPath<>(this.grafo);
		GraphPath<Fermata, DefaultWeightedEdge> path= sp.getPath(partenza, arrivo);
		return path.getVertexList();
	}
}
