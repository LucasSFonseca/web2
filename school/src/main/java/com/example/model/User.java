package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;


@Entity
@Table(name = "user")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="email")
	private String email;
	
	@NaturalId
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Collection> usuarioTem = new ArrayList<Collection>(); 

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WishList> usuarioQuer = new ArrayList<WishList>(); 
	
	//Getters and setters omitted for brevity
	 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
    
    public User()
    {
    }
    
    public User(String nome, String email, String login, String password) 
    {
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.password = password;
	}
    
    public void addHaveBook(Book book) 
    {
        Collection collection = new Collection(this, book);
        usuarioTem.add(collection);
        book.getUsuarioTem().add(collection);
    }
 
    public void removeHaveBook(Book book)
    {
        for (Iterator<Collection> iterator = usuarioTem.iterator(); iterator.hasNext(); )
        {
            Collection collection = iterator.next();
 
            if (collection.getUser().equals(this) && collection.getBook().equals(book)) 
            {
                iterator.remove();
                collection.getBook().getUsuarioTem().remove(collection);
                collection.setUser(null);
                collection.setBook(null);
            }
        }
    }
    
    /*public void addWishBook(Book book) 
    {
    	WishList wishList = new WishList(this, book);
        usuarioQuer.add(wishList);
        book.getUsuarioQuer().add(wishList);
    }
 
    public void removeWishBook(Book book)
    {
        for (Iterator<WishList> iterator = usuarioQuer.iterator(); iterator.hasNext(); )
        {
        	WishList wishList = iterator.next();
 
            if (wishList.getUser().equals(this) && wishList.getBook().equals(book)) 
            {
                iterator.remove();
                wishList.getBook().getUsuarioQuer().remove(wishList);
                wishList.setUser(null);
                wishList.setBook(null);
            }
        }
    }*/

//	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
//	private List<Book> usuarioTem;
	
//	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
//	private List<Book> usuarioQuer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Collection> getUsuarioTem() {
		return usuarioTem;
	}

	public void setUsuarioTem(List<Collection> usuarioTem) {
		this.usuarioTem = usuarioTem;
	}

	public List<WishList> getUsuarioQuer() {
		return usuarioQuer;
	}

	public void setUsuarioQuer(List<WishList> usuarioQuer) {
		this.usuarioQuer = usuarioQuer;
	}

}
