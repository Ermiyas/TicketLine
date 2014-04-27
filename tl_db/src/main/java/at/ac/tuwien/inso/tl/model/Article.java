package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Article implements Serializable{
	private static final long serialVersionUID = 6246337708372686917L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=100)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	@Column(nullable=false)
	private Integer price;
	
	private Integer available;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="article")
	private List<Entry> entries;
	
	@ManyToOne
	@JoinColumn(name="perf_id", nullable=false)
	private Performance performance;

	public Article(){
	}
	
	public Article(String name, String description, Integer price, Integer available, Performance performance){
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.performance = performance;
	}
	
	public Article(Integer id, String name, String description, Integer price, Integer available, Performance performance){
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.performance = performance;
	}
	
	public Article(Integer id, String name, String description, Integer price, Integer available, Performance performance,
			List<Entry> entries){
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.performance = performance;
		this.entries = entries;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}
}
