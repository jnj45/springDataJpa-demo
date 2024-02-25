package net.ecbank.entity;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "EF_SERVICE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Service extends BaseEntity implements Persistable<String> {
	
	@Id
	@Column(name = "SERVICE_URL", length = 200)
	private String serviceUrl;
	
	@Column(name = "SERVICE_NM", nullable = false, length = 200)
	private String serviceNm;
	
	@Column(name = "RMK", length = 200)
	private String rmk;
	
	public Service(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public Service(String serviceUrl, String serviceNm, String rmk) {
		this.serviceUrl = serviceUrl;
		this.serviceNm = serviceNm;
		this.rmk = rmk;
	}
	
	public void update(String serviceNm, String rmk) {
		this.serviceNm = serviceNm;
		this.rmk = rmk;
	}

	@Override
	public String getId() {
		return this.serviceUrl;
	}

	@Override
	public boolean isNew() {
		return getInsDt() == null;
	}
	
}
