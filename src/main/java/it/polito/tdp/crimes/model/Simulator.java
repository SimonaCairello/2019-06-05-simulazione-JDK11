package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

import it.polito.tdp.crimes.model.EventSim.EventType;

public class Simulator {
	
	private Model model;
	
	private Integer numAgenti;
	private Integer distrettoCentrale;
	
	private PriorityQueue<EventSim> queue;
	
	private Map<Integer, DistrettoNumero> agentiLiberi;
	
	private Integer malGestiti;

	public void init(Integer numAgenti, List<Event> events, Integer distrettoCentrale, Model model) {
		this.distrettoCentrale = distrettoCentrale;
		this.numAgenti = numAgenti;
		this.model = model;
		this.malGestiti = 0;
		List<Event> eventi = events;
		
		this.agentiLiberi = new TreeMap<>();
		agentiLiberi.put(distrettoCentrale, new DistrettoNumero(distrettoCentrale, numAgenti));
		
		this.queue = new PriorityQueue<>();
		for(Event e : eventi) {
			this.queue.add(new EventSim(EventType.CRIMINE, e.getReported_date(), e.getOffense_category_id(), e.getDistrict_id()));
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			EventSim e = this.queue.poll();
			processEvent(e);			
		}
	}
	
	public void processEvent(EventSim e) {
		List<CoppiaDistretti> coppie = this.model.getAdiacenti(e.getIdDistretto());
		
		switch(e.getTipo()) {
		
		case CRIMINE:
			
			for(CoppiaDistretti c : coppie) {
				if(c.getId1()==e.getIdDistretto()) {
					for(DistrettoNumero d : agentiLiberi.values()) {
						if(c.getId2()==d.getDistretto() && d.getNum()>0) {
							d.setNumDim();
							Long tempoSpostamento = this.getSecondiViaggio(c.getDistanza());
							Integer tempoCrimine = this.getOreCrimine(e.getOffenseCatId());
							
							LocalDateTime arrivo = e.getTime().plusSeconds(tempoSpostamento);
							if(arrivo.isAfter(e.getTime().plusMinutes(15))) {
								this.malGestiti++;
							}
							
							arrivo.plusHours(tempoCrimine);
							
							EventSim ev = new EventSim(EventType.AGENTE_LIBERO, arrivo, e.getIdDistretto());
							queue.add(ev);
							break;
						}
					}
				}
			}
			this.malGestiti++;
			break;
		
		case AGENTE_LIBERO:
			boolean aumentato = false;
			for(DistrettoNumero d : this.agentiLiberi.values()) {
				if(d.getDistretto()==e.getIdDistretto()) {
					d.setNumAum();
					aumentato = true;
				}
			}
			
			if(aumentato==false) {
				this.agentiLiberi.put(e.getIdDistretto(), new DistrettoNumero(e.getIdDistretto(), 1));
			}
			break;
		}
	}
	
	public Long getSecondiViaggio(Double distanza) {
		Long seconds = (long) ((distanza * 1000)/(60/3.6));
		return seconds;
	}
	
	public Integer getOreCrimine(String offenseCat) {
		Integer ore = 0;
		
		if(offenseCat.equals("all_other_crimes")) {
			Random random = new Random();
			Integer p = random.nextInt(100)+1;
			
			if(p<=50)
				ore = 1;
			else ore = 2;
		}
		else ore = 2;
		
		return ore;
	}
	
	public Integer getNumMalGestiti() {
		return this.malGestiti;
	}

}
