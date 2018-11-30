package br.imd.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ForeignKey;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
//@Table(name = "swaps")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Swap implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey( name="IdUserTo" ))
    User userTo;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey( name="IdUserFrom"))
    User userFrom;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey( name="IdBookTo"))
    Book bookTo;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey( name="IdBookFrom"))
    Book bookFrom;
    
    @Column(name = "dateOfSwap")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfSwap = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User userTo) {
		this.userTo = userTo;
	}

	public User getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(User userFrom) {
		this.userFrom = userFrom;
	}

	public Book getBookTo() {
		return bookTo;
	}

	public void setBookTo(Book bookTo) {
		this.bookTo = bookTo;
	}

	public Book getBookFrom() {
		return bookFrom;
	}

	public void setBookFrom(Book bookFrom) {
		this.bookFrom = bookFrom;
	}

	public Date getDateOfSwap() {
		return dateOfSwap;
	}

	public void setDateOfSwap(Date dateOfSwap) {
		this.dateOfSwap = dateOfSwap;
	}
}
