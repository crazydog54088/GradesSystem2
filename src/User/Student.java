package User;

import Other.Lesson;

public class Student extends User{
	
	private int TestNumber;
	
	public Student(int Id) {
		super(Id);
	}
	
	public String[] getTestList() {
		String strStudentInfo = Other.IO.InputById(filePath + "CourseRegistration.txt", this.id);
		return strStudentInfo.split(",")[1].split("#");
	}
	
	public String[] getScoreList() {
		String[] strList = Other.IO.InputById(filePath + "CourseRegistration.txt", this.id).split(",")[1].split("#");
		String[] ScoreList = new String[strList.length];
		
		
		int intDataNumber = 0;
		for (int index = 0; index < strList.length; index += 2) {
			String[] TestList = strList[index + 1].split("&");
			String strScore = "";
			intDataNumber +=TestList.length + 1;
			for (String TestId: TestList) {
				String InputData = Other.IO.InputById(filePath + "Score\\" + TestId + ".txt", Integer.parseInt(strList[index]));
				String[] InputId = InputData.split(",")[1].split("&");
				String[] InputScore = InputData.split(",")[2].split("&");
				for (int i = 0; i < InputId.length; i++) {
					if (Integer.parseInt(InputId[i]) == this.id) strScore += InputScore[i] + "&";
				}
			}
			strScore = strScore.substring(0, strScore.length() - 1);
			ScoreList[index] = strList[index];
			ScoreList[index + 1] = strScore;
		}
		this.TestNumber = intDataNumber;
		
		return ScoreList;
	}
	
	public String[][] getAllScore(){
		String[] TestList = this.geTestList();
		String[] ScoreList = this.getScoreList();
		
		String[][] outputData = new String[this.TestNumber][3];
		
		for (int index = 0, i = 0; i < TestList.length; i += 2) {
			String[] TestInSemester = TestList[i + 1].split("&");
			String[] ScoreInSemester =TestList[i + 1].split("&");
			outputData[index++] = new String[] {LessonList[i], TestnInSemester.length + "", "dataInfo"};
			
			for (int j = 0; j < TestInSemester.length; j++) {
				outputData[index++] = new String[] {TestInSemester[j], new Lesson(Integer.parseInt(LessonInSemester[j])).name, ScoreInSemester[j]};
			}
		}
		
		return outputData;
	}
	
}
