package com.example.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="email")
	private String email;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
	private String password;

	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Collection> usuarioTem; 

	@OneToMany
	@JoinColumn(name = "user_id")
	private List<WishList> usuarioQuer; 
	
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

	/*public List<Book> getUsuarioTem() {
		return usuarioTem;
	}

	public void setUsuarioTem(List<Book> usuarioTem) {
		this.usuarioTem = usuarioTem;
	}

	public List<Book> getUsuarioQuer() {
		return usuarioQuer;
	}

	public void setUsuarioQuer(List<Book> usuarioQuer) {
		this.usuarioQuer = usuarioQuer;
	}*/
	
	//@Column(name="")

}
