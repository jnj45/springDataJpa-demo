package net.ecbank.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

//@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {
	
//	@CreatedDate
	@Column(name = "INS_DT", updatable = false, length = 14)
	private String insDt;
	
//	@CreatedBy
	@Column(name = "INS_ID", updatable = false, length = 20)
	private String insId;
	
//	@LastModifiedDate
	@Column(name = "UPD_DT", length = 14)
	private String updDt;
	
//	@LastModifiedBy
	@Column(name = "UPD_ID", length = 20)
	private String updId;
	
	@PrePersist
    public void prePersist() {
        this.insDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        this.updDt = this.insDt;
        
        this.insId = getCurrentId();
        this.updId = this.insId;
    }
	
	@PreUpdate
	public void preUpdate() {
		this.updDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}
	
	/**
	 * 현재 사용자 ID 조회, 없을 경우 "SYSTEM"
	 * @return
	 */
	private String getCurrentId() {
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		if (securityContext.getAuthentication() != null && securityContext.getAuthentication().getPrincipal() instanceof UserDetails) {
//            return ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
//        }else {
//        	return "SYS";
//        }
		return "test";
	}
}
