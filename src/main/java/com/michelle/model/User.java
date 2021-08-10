package com.michelle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter 
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	@NotNull
	private String username;
	@NotNull
	private String password;
	private String role;
	private boolean enabled;

	public User(long userId, String username, String password, String role, boolean enabled, int cartTotal) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled=enabled;
	}
	
}
