package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;


public class RowDto {
	
	private Integer id;
		
	@NotNull
	private Integer sequence;
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@NotNull
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
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public Integer getShowId() {
		return showId;
	}

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public void setShowId(Integer showId) {
		this.showId = showId;
	}
	
	@Override
	public String toString() {
		return "RowDto [id=" + id + ", sequence=" + sequence + ", showId=" + showId + "]";
	}
}
