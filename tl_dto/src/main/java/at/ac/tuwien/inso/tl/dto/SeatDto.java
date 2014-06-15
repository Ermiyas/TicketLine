package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;

public class SeatDto {
	
	private Integer id;

	@NotNull
	private Integer sequence;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	@Override
	public String toString() {
		return "SeatDto [id=" + id + ", sequence=" + sequence + "]";
	}
}
