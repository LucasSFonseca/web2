package com.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "book")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Collection> usuarioTem = new ArrayList<Collection>();
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WishList> usuarioQuer = new ArrayList<WishList>();

//	@NaturalId
	@Column(name="ISBN")
	private Integer ISBN;
	
	@Column(name="numero_paginas")
	private Integer numeroPaginas;
	
	@Column(name="titulo")
	private String titulo;
	
	@Column(name="autor")
	private String autor;
	
	@Column(name="data_publicacao")
	private String dataP;
	
	@Column(name="thumbnail")
	private String thumbnail;
	
	@Column(name="idioma")
	private String idioma;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="media")
	private float media;
	
//	@ManyToOne
//	@JoinColumn(name="authorId")
//	private Integer authorId;	
	
//	@ManyToOne
//	@JoinColumn(name="id")
//	private User user; 
	
	public Book()
	{
	}
	
    public Book(Integer iSBN, Integer numeroPaginas, String titulo, String autor, String dataP, String thumbnail, String idioma, String descricao, float media)
    {
		ISBN = iSBN;
		this.numeroPaginas = numeroPaginas;
		this.titulo = titulo;
		this.autor = autor;
		this.dataP = dataP;
		this.thumbnail = thumbnail;
		this.idioma = idioma;
		this.descricao = descricao;
		this.media = media;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(ISBN, book.ISBN);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getISBN() {
		return ISBN;
	}

	public void setISBN(Integer ISBN) {
		this.ISBN = ISBN;
	}

	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDataP() {
		return dataP;
	}

	public void setDataP(String dataP) {
		this.dataP = dataP;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getMedia() {
		return media;
	}

	public void setMedia(float media) {
		this.media = media;
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
	
//	public Integer getAuthorId() {
//		return authorId;
//	}
//
//	public void setAuthorId(Integer authorId) {
//		this.authorId = authorId;
//	}
	
	
	
}

