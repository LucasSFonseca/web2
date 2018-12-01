package br.imd.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length=20)
	private String id;

	@ManyToMany(mappedBy = "roles")
    private List<User> users;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.id;
	}
}
