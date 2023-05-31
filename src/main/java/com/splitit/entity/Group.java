package com.splitit.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Nonnull
	@Size(max = 20, min = 5)
	@Column(name = "name")
	private String name;

	@Nonnull
	@Size(max = 50, min = 5)
	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "creator_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User creator;

}
