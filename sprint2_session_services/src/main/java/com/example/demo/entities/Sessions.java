package com.example.demo.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sessions {
	@Id
	private String username;
	private Timestamp logintimestamp;
	private Timestamp expiredtimestamp; 
}
