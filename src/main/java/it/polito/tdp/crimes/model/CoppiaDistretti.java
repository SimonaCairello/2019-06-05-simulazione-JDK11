package it.polito.tdp.crimes.model;

public class CoppiaDistretti implements Comparable<CoppiaDistretti>{
	
	private Integer id;
	private double distanza;
	
	public CoppiaDistretti(Integer id, double distanza) {
		this.id = id;
		this.distanza = distanza;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distanza);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		CoppiaDistretti other = (CoppiaDistretti) obj;
		if (Double.doubleToLongBits(distanza) != Double.doubleToLongBits(other.distanza))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + " - " + distanza;
	}

	@Override
	public int compareTo(CoppiaDistretti o) {
		return (int) ((this.distanza*100) - (o.getDistanza()*100));
	}
}
