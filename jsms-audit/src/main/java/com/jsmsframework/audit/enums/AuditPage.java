package com.jsmsframework.audit.enums;


public enum AuditPage {

	ORDINARY_AUDIT_PAGE("普通审核页面", "ordinaryNum"),
	YZM_AUDIT_PAGE("验证码审核页面", "yzmNum"),
	MAJOR_AUDIT_PAGE("重要客户审核页面", "majorNum"),
	AUDIT_QUERY_PAGE("审核查询页面", "");
	
	
	private String name;
	private String value;

	AuditPage(String name, String value) {
    	this.name = name;
        this.value = value;
    }
	
    public static AuditPage getInstance(String value){
    	for (AuditPage type : AuditPage.values()) {
			if(type.getValue().equals(value)){
				return type;
			}
		}
    	return null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

