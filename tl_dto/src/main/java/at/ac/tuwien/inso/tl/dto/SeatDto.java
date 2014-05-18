package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;

public class SeatDto {

	@NotNull
	private Integer sequence;
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	@Override
	public String toString() {
		return "SeatDto [sequence=" + sequence + "]";
	}
}
