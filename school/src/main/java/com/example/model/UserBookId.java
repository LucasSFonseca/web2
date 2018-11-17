package com.example.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserBookId implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
    private Integer userId;
 
    @Column(name = "book_id")
    private Integer bookId;
 
    public UserBookId() {}
 
    public UserBookId(Integer userId,Integer bookId)
    {
        this.userId = userId;
        this.bookId = bookId;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass()) 
            return false;
 
        UserBookId that = (UserBookId) o;
        return Objects.equals(userId, that.userId) && 
               Objects.equals(bookId, that.bookId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
}
