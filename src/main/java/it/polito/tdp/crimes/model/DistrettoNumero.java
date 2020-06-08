package it.polito.tdp.crimes.model;

public class DistrettoNumero {
	
	private Integer distretto;
	private Integer num;
	
	public DistrettoNumero(Integer distretto, Integer num) {
		this.distretto = distretto;
		this.num = num;
	}

	public Integer getDistretto() {
		return distretto;
	}

	public void setDistretto(Integer distretto) {
		this.distretto = distretto;
	}

	public Integer getNum() {
		return num;
	}

	public void setNumDim() {
		this.num = this.num--;
	}
	
	public void setNumAum() {
		this.num = this.num++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distretto == null) ? 0 : distretto.hashCode());
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
		DistrettoNumero other = (DistrettoNumero) obj;
		if (distretto == null) {
			if (other.distretto != null)
				return false;
		} else if (!distretto.equals(other.distretto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DistrettoNumero [distretto=" + distretto + ", num=" + num + "]";
	}

}
