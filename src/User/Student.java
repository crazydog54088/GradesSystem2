package User;

import Other.Lesson;

public class Student extends User{
	
	private int lessonNumber;
	
	public Student(int Id) {
		super(Id);
	}
	
	public String[] getLessonList() {
		String strStudentInfo = Other.IO.InputById(filePath + "CourseRegistration.txt", this.id);
		return strStudentInfo.split(",")[1].split("#");
	}
	
	public String[] getScoreList() {
		String[] strList = Other.IO.InputById(filePath + "CourseRegistration.txt", this.id).split(",")[1].split("#");
		String[] ScoreList = new String[strList.length];
		
		// 資料數 = 年份 + 課程
		int intDataNumber = 0;
		for (int index = 0; index < strList.length; index += 2) {
			String[] LessonList = strList[index + 1].split("&");
			String strScore = "";
			intDataNumber += LessonList.length + 1;
			for (String LessonId: LessonList) {
				String InputData = Other.IO.InputById(filePath + "Score\\" + LessonId + ".txt", Integer.parseInt(strList[index]));
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
		this.lessonNumber = intDataNumber;
		
		return ScoreList;
	}
	
	public String[][] getAllScore(){
		String[] LessonList = this.getLessonList();
		String[] ScoreList = this.getScoreList();
		
		String[][] outputData = new String[this.lessonNumber][3];
		
		for (int index = 0, i = 0; i < LessonList.length; i += 2) {
			String[] LessonInSemester = LessonList[i + 1].split("&");
			String[] ScoreInSemester = ScoreList[i + 1].split("&");
			outputData[index++] = new String[] {LessonList[i], LessonInSemester.length + "", "dataInfo"};
			
			for (int j = 0; j < LessonInSemester.length; j++) {
				outputData[index++] = new String[] {LessonInSemester[j], new Lesson(Integer.parseInt(LessonInSemester[j])).name, ScoreInSemester[j]};
			}
		}
		
		return outputData;
	}
	
}
