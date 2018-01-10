package assign.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "dates" )

public class Date {
	
	private Long id;
    private String dateTitle;
    private String dateDescription;
    private boolean completed;
    private String date;

    public Date() {
    	// this form used by Hibernate
    }
    
    public Date(String dateTitle, String dateDescription, boolean completed, String date) {
    		this.dateTitle = dateTitle;
    		this.dateDescription= dateDescription;
    		this.completed = completed;
    		this.date = date;
    }
    
    @Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
		return id;
    }

    @SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
    }
    
    @Column(name="title")
    public String getTitle() {
		return dateTitle;
    }

    public void setTitle(String dateTitle) {
		this.dateTitle = dateTitle;
    }
    
    @Column(name="description")
    public String getDateDescription() {
		return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
		this.dateDescription = dateDescription;
    }
    
    @Column(name="completed")
    public boolean getCompleted() {
		return completed;
    }

    public void setCompleted(boolean completed) {
		this.completed = completed;
    }
    
    @Column(name="date")
    public String getDate() {
    		return date;
    }
    
    public void setDate(String date) {
    		this.date = date;
    }
}