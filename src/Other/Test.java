package Other;
import User.Student;

public class Test {
	
	private String software engineering;
	public int id;
	public String name;
	public int managerId;
	public int time;
	public String difficult;
	public int semester; // 指定學期時間
	
	public Test(int id){
		getPath();
		
		// 讀取 Cache 的資料
		String[] TestInfo = IO.InputById(filePath + "Cache_TestInfo.txt", id).split(",");
		this.id = id;
		this.name = TestInfo[1];
		this.managerId = Integer.parseInt(TestInfo[2]);
		this.time = Integer.parseInt(TestInfo[3]);
		this.difficult = TestInfo[4];
		this.semester = Integer.parseInt( IO.InputValue(".\\config.txt", "year", ": ") );
	}
	
	public Test(int id, int semester){
		getPath();
		
		// 讀取歷年資料
		String[]TestInfo = IO.InputById(filePath + "TestInfo\\" + id + ".txt", semester).split(",");
		this.id = id;
		this.name = TestInfo[1];
		this.managerId = Integer.parseInt(TestInfo[2]);
		this.credits = Integer.parseInt(TestInfo[3]);
		this.type = TestInfo[4];
		this.semester = semester;
	}
	
	private void getPath() {
		this.filePath = IO.InputValue(".\\config.txt", "databasePath", ": ");
	}
	
	private static String getPathForStatic() {
		return IO.InputValue(".\\config.txt", "databasePath", ": ");
	}
	
	public String[] getStudentList() {
		return IO.InputById(filePath + "Score\\" + this.id + ".txt", this.semester).split(",")[1].split("&");
	}
	
	private String[] getScoreList() {
		return IO.InputById(filePath + "Score\\" + this.id + ".txt", this.semester).split(",")[2].split("&");
	}
	
	public String[][] getAllScore() {
		String[] StudentList = this.getStudentList();
		String[] ScoreList = this.getScoreList();
		String[][] OutputData = new String[StudentList.length][2];
		for (int index = 0; index < StudentList.length; index++) {
			OutputData[index] = new String[] {StudentList[index], new Student(Integer.parseInt(StudentList[index])).getName(), ScoreList[index]};
		}
		
		return OutputData;
	}
	
	public void setAllScore(String newScoreList) {
		String strInput = IO.InputById(filePath + "Score\\" + this.id + ".txt", this.semester);
		int outputIndex = strInput.lastIndexOf(",");
		String strOutput = strInput.substring(0, outputIndex) + "," + newScoreList;
		
		IO.OutputById(filePath + "Score\\" + this.id + ".txt", strOutput, this.semester);
	}
	
	public static String[] getLessonListInSemester(int semester) {
		String strOutput = "";
		String filePath = getPathForStatic() + "TestInfo\\";
		String[] fileList = IO.traverseDirectory(filePath);
		
		for (String file: fileList) {
			String[] InputData = IO.InputAll(filePath + file);
			for (String Element: InputData) {
				String yearInfo = Element.split(",")[0];
				if (yearInfo.indexOf(semester + "") != -1) {
					strOutput += file.substring(0, file.indexOf(".")) + "&";
					break;
				}
			}
		}
		
		if (strOutput == "") return null;
		else return strOutput.substring(0, strOutput.length() - 1).split("&");
	}
	
}
