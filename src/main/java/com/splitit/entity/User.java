package com.splitit.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Nonnull
	@Column(name = "user_name", unique = true)
	private String userName;

	@Nonnull
	@Size(max = 50)
	@Column(name = "first_name")
	private String firstName;

	@Nonnull
	@Size(max = 50)
	@Column(name = "last_name")
	private String lastName;

	@Nonnull
	@Size(max = 120)
	private String password;

	@Nonnull
	@Email
	@Size(max = 50)
	private String email;

	@Nonnull
	@Size(min = 10, max = 10)
	@Column(name = "phn_number")
	private Integer phoneNumber;
}