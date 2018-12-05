package br.imd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.imd.model.Role;
import br.imd.model.User;
import br.imd.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findOne(Integer id) {
		return userRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public User save(User entity) {		
		entity.setPassword( new BCryptPasswordEncoder().encode( entity.getPassword() ) );
		return userRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(User entity) {
		userRepository.delete(entity);
	}
	
	public boolean validateLoginName(User entity) {
		try {
			String myDriver = "com.mysql.jdbc.Driver";
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root");
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM user WHERE user.login=" + entity.getLogin();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				System.out.println("Query não repetida");
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	public boolean validateMail(User entity) {
		try {
			String myDriver = "com.mysql.jdbc.Driver";
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root");
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM user WHERE user.email=" + entity.getEmail();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}

}
	
