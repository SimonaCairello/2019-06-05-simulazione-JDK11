package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<Integer, DefaultWeightedEdge> graph;
	private List<CoppiaDistretti> coppieDistretti;
	
	private Simulator sim;

	public Model() {
		this.dao = new EventsDao();
		this.coppieDistretti = new ArrayList<>();
		this.sim = new Simulator();
	}
	
	public List<Integer> listYears() {
		List<Integer> years = this.dao.listYears();
		Collections.sort(years);
		return years;
	}
	
	public void generateGraph(Integer year) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.dao.listDistrict());
		
		Map<Integer, CentroDistretto> centriDistretti = this.dao.getCentroDistrict(year);
		
		for(Integer id : this.graph.vertexSet()) {
			for(Integer id1 : this.graph.vertexSet()) {
				CoppiaDistretti c1 = new CoppiaDistretti(id, id1, 0);
				CoppiaDistretti c2 = new CoppiaDistretti(id1, id, 0);
				
				if(!coppieDistretti.contains(c1) && !coppieDistretti.contains(c2) && id!=id1) {
					Double distanza = LatLngTool.distance(centriDistretti.get(id).getCentro(), centriDistretti.get(id1).getCentro(), LengthUnit.KILOMETER);
					coppieDistretti.add(new CoppiaDistretti(id, id1, distanza));
				}
			}
		}
		
		for(CoppiaDistretti c : coppieDistretti) {
			Graphs.addEdge(this.graph, c.getId1(), c.getId2(), c.getDistanza());
		}
	}
	
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public List<CoppiaDistretti> getDistrettiAdiacenti() {
		List<CoppiaDistretti> list = this.coppieDistretti;
		Collections.sort(list);
		return list;
	}
	
	public List<CoppiaDistretti> getAdiacenti(Integer id){
		List<CoppiaDistretti> list = new ArrayList<>();
		for(CoppiaDistretti c : this.coppieDistretti) {
			if(c.getId1()==id) {
				list.add(c);
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public Integer getDistrettoCentrale(Integer year) {
		Map<Integer, DistrettoNumero> map = this.dao.getDistrettoCentrale(year);
		Integer minimo = map.get(1).getNum();
		Integer minId = 1;
		
		for(DistrettoNumero i : map.values()) {
			if(i.getNum()<minimo) {
				minimo = i.getNum();
				minId = i.getDistretto();
			}
		}
		return minId;
	}
	
	public List<Integer> getMesi() {
		List<Integer> mesi = new ArrayList<>();
		
		for(int i=1; i<13; i++)
			mesi.add(i);
		
		return mesi;
	}
	
	public List<Integer> getGiorni() {
		List<Integer> giorni = new ArrayList<>();
		
		for(int i=1; i<32; i++)
			giorni.add(i);
		
		return giorni;
	}
	
	public List<Event> getEventiDelGiorno(Integer year, Integer month, Integer day) {
		return this.dao.getEventiDelGiorno(year, month, day);
	}
	
	public void simula(Integer year, Integer month, Integer day, Integer numAgenti) {
		List<Event> events = this.dao.getEventiDelGiorno(year, month, day);
		Collections.sort(events);
		this.sim.init(numAgenti, events, this.getDistrettoCentrale(year), this);
		this.sim.run();
	}
	
	public Integer getNumMalGestiti() {
		return this.sim.getNumMalGestiti();
	}
	
}
