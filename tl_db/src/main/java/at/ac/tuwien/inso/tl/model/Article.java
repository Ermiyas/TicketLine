package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Article implements Serializable{
	private static final long serialVersionUID = 6246337708372686917L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=1024)
	private String description;
	
	@Column(nullable=false, length=100)
	private String name;		
	
	@Column
	private Integer priceInCent;
	
	@Column
	private Integer priceInPoints;		
	
	@OneToMany(mappedBy="article")
	private List<Entry> entries;		
	
	@ManyToMany
	@JoinTable(name="articleforperformance", joinColumns={
			@JoinColumn(name="article_id", nullable=false)},
			inverseJoinColumns = { @JoinColumn(name="performance_id", nullable=false)})	
	private List<Performance> performances;	
	
	public Article() {		
	}

	public Article(Integer id, String description, String name,
			Integer priceInCent, Integer priceInPoints, List<Entry> entries,
			List<Performance> performances) {		
		this.id = id;
		this.description = description;
		this.name = name;
		this.priceInCent = priceInCent;
		this.priceInPoints = priceInPoints;
		this.entries = entries;
		this.performances = performances;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriceInCent() {
		return priceInCent;
	}

	public void getPriceInCent(Integer priceInCent) {
		this.priceInCent = priceInCent;
	}

	public Integer getPriceInPoints() {
		return priceInPoints;
	}

	public void getPriceInPoints(Integer priceInPoints) {
		this.priceInPoints = priceInPoints;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public List<Performance> getPerformances() {
		return performances;
	}

	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}
}
