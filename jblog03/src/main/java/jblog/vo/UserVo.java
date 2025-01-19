package jblog.vo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserVo {
	@NotEmpty
    @Size(min = 1, max = 50)
	private String id;
	
	@NotEmpty
	@Size(min = 1, max = 45)
	private String name;
	
	@NotEmpty
	@Size(min = 1, max = 16)
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", password=" + password + "]";
	}
}