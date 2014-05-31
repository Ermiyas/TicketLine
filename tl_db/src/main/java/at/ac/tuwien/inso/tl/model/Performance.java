package at.ac.tuwien.inso.tl.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Performance implements Serializable{
	private static final long serialVersionUID = -3795343062479959019L;

	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=1024)
	private String content;
	
	@Column(nullable=false, length=50)
	private String description;
	
	@Column
	private Integer durationInMinutes;

	@Column(length=50)
	private String performancetype;	
	
	@ManyToMany
	@JoinTable(name="articleforperformance", joinColumns={
			@JoinColumn(name="performance_id", nullable=false)},
			inverseJoinColumns = { @JoinColumn(name="article_id", nullable=false)})	
	private List<Article> articles;	
	
	@ManyToMany
	@JoinTable(name="participation", joinColumns={
			@JoinColumn(name="performance_id", nullable=false)},
			inverseJoinColumns = { @JoinColumn(name="artist_id", nullable=false)})	
	private List<Artist> artists;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="performance")
	private List<Show> shows;

	public Performance() {
	}

	public Performance(Integer id, String content, String description,
			Integer durationInMinutes, String performancetype,
			List<Article> articles, List<Artist> artists, List<Show> shows) {
		this.id = id;
		this.content = content;
		this.description = description;
		this.durationInMinutes = durationInMinutes;
		this.performancetype = performancetype;
		this.articles = articles;
		this.artists = artists;
		this.shows = shows;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(Integer durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public String getPerformancetype() {
		return performancetype;
	}

	public void setPerformancetype(String performancetype) {
		this.performancetype = performancetype;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}			
}

