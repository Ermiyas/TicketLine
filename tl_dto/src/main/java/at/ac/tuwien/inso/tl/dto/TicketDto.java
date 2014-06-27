package at.ac.tuwien.inso.tl.dto;

public class TicketDto {
	
	private Integer id;

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	private Integer showId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return "TicketDto [id=" + id + ", showId=" + showId + "]";
	}
}
