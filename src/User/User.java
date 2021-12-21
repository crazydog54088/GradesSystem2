package User;

import Other.IO;
import Other.Encryptor;

//僅有 is-a 的關係難以實作，可以加入 has-a
public class User {
	
	protected String filePath;
	protected int id;// -1 代表  用戶不存在
	protected String name;
	private String password;
	protected boolean verify;
	protected String identity;
	protected int entranceYear;
	
	public User(int Id) {
		this.filePath = IO.InputValue(".\\config.txt", "databasePath", ": ");
	
		String InputData = IO.InputById(filePath + "Account.txt", Id);
		if (InputData == null) {
			this.id = -1;
			return;
		}
		String[] userInfo = InputData.split(",");
		this.id = Id;
		this.name = userInfo[1];
		this.password = userInfo[2];
		this.verify = Boolean.parseBoolean(userInfo[3]);
		this.identity = userInfo[4];
		this.entranceYear = Integer.parseInt(userInfo[5]);
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean getVerify() {
		return this.verify;
	}
	
	public String getIdentity() {
		return this.identity;
	}
	
	public int getEntranceYear() {
		return this.entranceYear;
	}
	
	public void changePassword(String newPassword) {
		this.password = new Encryptor(newPassword).getResult();
		String changeData = IO.InputById(filePath + "Account.txt", this.id);
		int index = changeData.indexOf(",", changeData.indexOf(",") + 1);
		int passwordLength = changeData.indexOf(",", index + 1) - index;
		changeData = changeData.substring(0, index + 1) + this.password + changeData.substring(index + passwordLength);
		IO.OutputById(filePath + "Account.txt", changeData, this.id);
	}
	
	private static boolean IsNumeric(String str) {
		return str.chars().allMatch( Character::isDigit );
	}
	
	public static String login(String id, String password) {
		String encryptedPassword = new Encryptor(password).getResult();
		if (id.equals("")) {
			return "請輸入帳號";
		}
		else if (!IsNumeric(id)) {
			return "帳號不可以有數字以外的字元";
		}
		User loginUser = new User(Integer.parseInt(id));
		if (loginUser.id == -1) {
			return "帳戶名稱錯誤";
		}
		else if (!encryptedPassword.equals(loginUser.password)) {
			return "密碼輸入錯誤";
		}
		else if (loginUser.verify) {
			return "此帳戶尚未認證";
		}
		else {
			return "Success";
		}
	}
	
}
