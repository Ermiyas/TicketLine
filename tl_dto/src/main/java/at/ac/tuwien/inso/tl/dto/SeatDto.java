package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;

public class SeatDto {
	
	private Integer id;

	@NotNull
	private Integer sequence;

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@NotNull
	private Integer rowId;
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
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
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public Integer getRowId() {
		return rowId;
	}

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public Integer getTicketId() {
		return ticketId;
	}

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		return "SeatDto [id=" + id + ", sequence=" + sequence + ", rowId=" + rowId + ", ticketId=" + ticketId + "]";
	}
}
