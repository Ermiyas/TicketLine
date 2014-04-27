package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Entry implements Serializable{
	private static final long serialVersionUID = 2151550295868898930L;
	
	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false)
	private Integer position;
	
	@Column(nullable=false)
	private Integer amount;
	
	@Column(nullable=false)
	private Integer unitPrice;
	
	@ManyToOne
	@JoinColumn(name="rec_id", nullable=false)
	private Receipt receipt;
	
	@ManyToOne
	@JoinColumn(name="article_id", nullable=true)
	private Article article;
	
	@OneToOne
	@JoinColumn(name="ticket_id", nullable=true)
	private TicketIdentifier ticketIdentifier;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public TicketIdentifier getTicketIdentifier() {
		return ticketIdentifier;
	}

	public void setTicketIdentifier(TicketIdentifier ticketIdentifier) {
		this.ticketIdentifier = ticketIdentifier;
	}
}
