package it.polito.tdp.metroparis.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model() ;
		
		m.creaGrafo();
		
		System.out.println(m.getGrafo()) ;
		System.out.println(m.trovaCamminomMinimo(m.getFermateIdMap().get(3), m.getFermateIdMap().get(444) ));

	}

}
