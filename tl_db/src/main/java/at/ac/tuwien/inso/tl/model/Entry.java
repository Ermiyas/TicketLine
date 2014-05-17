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
	private Integer amount;		

	@Column(nullable=false)
	private Boolean buyWithPoints;	
	
	@Column(nullable=false)
	private Boolean sold;		
	
	@ManyToOne(optional=true)
	@JoinColumn(name="article_id", nullable=true)
	private Article article;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="basket_id", nullable=false)
	private Basket basket;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="receipt_id", nullable=true)
	private Receipt receipt;
	
	@OneToOne(optional=true)
	@JoinColumn(nullable=true)
	private Ticket ticket;

	public Entry() {
	
	}


	public Entry(Integer id, Integer amount, Boolean buyWithPoints,
			Boolean sold, Article article, Basket basket, Receipt receipt,
			Ticket ticket) {
		this.id = id;
		this.amount = amount;
		this.buyWithPoints = buyWithPoints;
		this.sold = sold;
		this.article = article;
		this.basket = basket;
		this.receipt = receipt;
		this.ticket = ticket;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}


	public Boolean getBuyWithPoints() {
		return buyWithPoints;
	}


	public void setBuyWithPoints(Boolean buyWithPoints) {
		this.buyWithPoints = buyWithPoints;
	}


	public Boolean getSold() {
		return sold;
	}


	public void setSold(Boolean sold) {
		this.sold = sold;
	}


	public Article getArticle() {
		return article;
	}


	public void setArticle(Article article) {
		this.article = article;
	}


	public Basket getBasket() {
		return basket;
	}


	public void setBasket(Basket basket) {
		this.basket = basket;
	}


	public Receipt getReceipt() {
		return receipt;
	}


	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}


	public Ticket getTicket() {
		return ticket;
	}


	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}	
}
