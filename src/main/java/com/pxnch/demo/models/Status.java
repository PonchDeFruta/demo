package com.pxnch.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "StatusFile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long idStatus;
	public int status;
	public String statusName;
	public String statusDescription;
	public Timestamp timeStmap;
	

	
	

}
