package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;

public class CentroDistretto {
	
	private Integer id;
	private LatLng centro;
	
	public CentroDistretto(Integer id, LatLng centro) {
		this.id = id;
		this.centro = centro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LatLng getCentro() {
		return centro;
	}

	public void setCentro(LatLng centro) {
		this.centro = centro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CentroDistretto other = (CentroDistretto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CentroDistretto [id=" + id + ", centro=" + centro + "]";
	}
}
