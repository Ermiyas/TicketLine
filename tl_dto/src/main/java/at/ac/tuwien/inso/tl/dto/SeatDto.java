package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;

public class SeatDto {
	
	private Integer id;

	@NotNull
	private Integer sequence;

	private Integer rowId;
	
	private Integer ticketId;
	
	// --- Getter & Setter
	
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
	
	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		return "SeatDto [id=" + id + ", sequence=" + sequence + ", rowId=" + rowId + ", ticketId=" + ticketId + "]";
	}
}
