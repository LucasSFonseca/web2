package com.example.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity(name = "Collection")
@Table(name = "collection")
public class Collection implements Serializable  {

	// Teste
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    private UserBookId id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    private Book book;
 
    @Column(name = "added_on")
    private Date addedOn = new Date();
 
    @SuppressWarnings("unused")
	private Collection() {}
 
    public Collection(User user, Book book) 
    {
        this.user = user;
        this.book = book;
        
        if(book != null && user != null)
        {
            this.id = new UserBookId(user.getId(), book.getId());
        }
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        Collection that = (Collection) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(book, that.book);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }

	public UserBookId getId() {
		return id;
	}

	public void setId(UserBookId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	} 
}
