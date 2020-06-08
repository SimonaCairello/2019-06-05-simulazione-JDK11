package it.polito.tdp.crimes.model;

public class CoppiaDistretti implements Comparable<CoppiaDistretti>{
	
	private Integer id1;
	private Integer id2;
	private double distanza;
	
	public CoppiaDistretti(Integer id1, Integer id2, double distanza) {
		this.id1 = id1;
		this.id2 = id2;
		this.distanza = distanza;
	}

	public Integer getId1() {
		return id1;
	}

	public void setId1(Integer id1) {
		this.id1 = id1;
	}

	public Integer getId2() {
		return id2;
	}

	public void setId2(Integer id2) {
		this.id2 = id2;
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
		result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
		result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
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
		if (id1 == null) {
			if (other.id1 != null)
				return false;
		} else if (!id1.equals(other.id1))
			return false;
		if (id2 == null) {
			if (other.id2 != null)
				return false;
		} else if (!id2.equals(other.id2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id1 + " - " + id2 + ", distanza: " + distanza + "\n";
	}

	@Override
	public int compareTo(CoppiaDistretti o) {
		if(this.id1==o.getId1())
			return (int) ((this.distanza*100) - (o.getDistanza()*100));
		return this.id1-o.getId1();
	}
}
