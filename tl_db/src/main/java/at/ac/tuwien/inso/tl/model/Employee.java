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

@Entity
public class Employee implements Serializable{
	private static final long serialVersionUID = 1021211581003682919L;		
	
	@Id
	@Column(nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, length=50)
	private String firstname;		
	
	@Column(nullable=false)
	private Boolean isadmin;	
	
	@Column(nullable=false, length=50)
	private String lastname;
	
	@Column(nullable=false, length=512)
	private String passwordHash;
	
	@Column(nullable=false, length=50, unique=true)
	private String username;
	
	@Column(nullable=false)
	private Integer wrongPasswordCounter;
		
	@ManyToMany
	@JoinTable(name="newsread", joinColumns={
			@JoinColumn(name="employee_id", nullable=false)},
			inverseJoinColumns = { @JoinColumn(name="news_id", nullable=false)})	
	private List<News> readNews;

	public Employee() {
	}
	
	public Employee(String firstname, String lastname, String username, String passwordHash, Boolean isadmin, Integer wrongPasswordCounter) {	
		this.firstname = firstname;		
		this.lastname = lastname;
		this.passwordHash = passwordHash;
		this.username = username;	
		this.isadmin = isadmin;
		this.wrongPasswordCounter = wrongPasswordCounter;
	}

	public Employee(Integer id, String firstname, Boolean isadmin,
			String lastname, String passwordHash, String username,
			Integer wrongPasswordCounter, List<News> readNews) {
		this.id = id;
		this.firstname = firstname;
		this.isadmin = isadmin;
		this.lastname = lastname;
		this.passwordHash = passwordHash;
		this.username = username;
		this.wrongPasswordCounter = wrongPasswordCounter;
		this.readNews = readNews;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Boolean getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(Boolean isadmin) {
		this.isadmin = isadmin;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getWrongPasswordCounter() {
		return wrongPasswordCounter;
	}

	public void setWrongPasswordCounter(Integer wrongPasswordCounter) {
		this.wrongPasswordCounter = wrongPasswordCounter;
	}

	public List<News> getReadNews() {
		return readNews;
	}

	public void setReadNews(List<News> readNews) {
		this.readNews = readNews;
	}	
}
