package at.ac.tuwien.inso.tl.dto;

public class TicketDto {
	
	private Integer id;

	private Integer showId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}
	
	@Override
	public String toString() {
		return "TicketDto [id=" + id + ", showId=" + showId + "]";
	}
}
