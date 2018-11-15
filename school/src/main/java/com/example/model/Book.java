package com.example.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@OneToOne(mappedBy = "book")
	private Collection usuarioTem; 

	@OneToOne(mappedBy = "book")
	private WishList usuarioQuer; 
	
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
		ISBN = ISBN;
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

//	public Integer getAuthorId() {
//		return authorId;
//	}
//
//	public void setAuthorId(Integer authorId) {
//		this.authorId = authorId;
//	}
	
	
	
}

