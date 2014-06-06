package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;


public class RowDto {
	
	private Integer id;
		
	@NotNull
	private Integer sequence;
	
	private Integer showId;

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
	
	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}
	
	@Override
	public String toString() {
		return "RowDto [id=" + id + ", sequence=" + sequence + ", showId=" + showId + "]";
	}
}
