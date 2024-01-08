package com.pxnch.demo.models;


import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Files")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class File {

	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private String type;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;




}
