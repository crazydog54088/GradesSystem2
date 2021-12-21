package gs;

import User.*;

public class Model {

	private User user;
	
	Model() {}
	
	void setUser(int id) {
		String identity = new User(id).getIdentity();
		switch (identity) {
			case "Student":
				this.user = new Student(id);
				break;
		
			case "Manager":
				this.user = new Manager(id);
				break;
				
			case "SuperUser":
				this.user = new User(id);
				System.out.println("進行系統設定...");
				break;
			
			default:
				System.out.println("身分不明的使用者");
		}
	}
	
	String login(String account, String password) {
		return User.login(account, password);
	}
	
	int getId() {
		return user.getId();
	}
	
	String getIdentity() {
		return user.getIdentity();
	}
	
}
