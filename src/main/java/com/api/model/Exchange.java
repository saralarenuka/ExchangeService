package com.api.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "exchangetab")
@EntityListeners(AuditingEntityListener.class)
public class Exchange {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Name is mandatory")
	@Column(name="name")
	private String name;
	
	@NotBlank(message = "CountryName is mandatory")
	@Column(name="country")
	private String country;
	
	@NotBlank(message = "Description is mandatory for every exchanged stocks")
	private String description;

	public Integer getId() { 
		return id; 
	}
	public void setId(Integer id) { 
		this.id = id; 
	}

	public String getName() { 
		return name; 
	}
	public void setName(String name) { 
		this.name = name; 
		}

	public String getCountry() { 
		return country; 
		}
	public void setCountry(String country) { 
		this.country = country;
		}

	public String getDescription() {
		return description; 
		}
	public void setDescription(String description) {
		this.description = description;
		}
	@Override
	public String toString() {
		return "Exchange [id=" + id + ", name=" + name + ", country=" + country + ", description=" + description + "]";
	}

	
}
