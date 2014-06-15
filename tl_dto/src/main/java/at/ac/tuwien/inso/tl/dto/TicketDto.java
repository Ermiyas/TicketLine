package at.ac.tuwien.inso.tl.dto;

public class TicketDto {
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "TicketDto [id=" + id + "]";
	}
}
