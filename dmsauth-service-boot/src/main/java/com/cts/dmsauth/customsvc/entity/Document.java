package com.cts.dmsauth.customsvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DOCUMENT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

	@Id
	@Column(name = "DOC_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DOC_NAME")
	private String name;

	@Column(name = "DOC_TYPE")
	private String type;

	@Lob
	@Column(name = "DOC_CONTENT")
	private byte[] content;

	@Column(name = "Created_By")
	private String createdBy;

}
