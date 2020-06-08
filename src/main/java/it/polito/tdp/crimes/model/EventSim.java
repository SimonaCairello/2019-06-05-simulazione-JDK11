package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class EventSim implements Comparable<EventSim>{
	
	public enum EventType {
		CRIMINE, AGENTE_LIBERO
	}
	
	private EventType tipo;
	private LocalDateTime time;
	private String offenseCatId;
	private Integer idDistretto;

	public EventSim(EventType tipo, LocalDateTime time, String offenseCatId, Integer idDistretto) {
		this.tipo = tipo;
		this.time = time;
		this.offenseCatId = offenseCatId;
		this.idDistretto = idDistretto;
	}
	
	public EventSim(EventType tipo, LocalDateTime time, Integer idDistretto) {
		this.tipo = tipo;
		this.time = time;
		this.idDistretto = idDistretto;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getOffenseCatId() {
		return offenseCatId;
	}

	public void setOffenseCatId(String offenseCatId) {
		this.offenseCatId = offenseCatId;
	}

	public Integer getIdDistretto() {
		return idDistretto;
	}

	public void setIdDistretto(Integer idDistretto) {
		this.idDistretto = idDistretto;
	}

	@Override
	public int compareTo(EventSim o) {
		return 0;
	}

}
