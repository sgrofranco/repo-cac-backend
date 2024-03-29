package ar.com.codoacodo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Representa la tabla de USUARIOS

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "role",length = 50,unique = true)
	private String rol;
	
}
