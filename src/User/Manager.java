package User;

import Other.IO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Manager extends User {

    public Manager(int Id) {
        super(Id);
    }

    public  void updateAccount(String newData){
        Other.IO.OutputAll(filePath+"Account.txt",newData);
    }
    
    public String[] getLessonList() {
		String[] LessonList = IO.InputAll(filePath + "Cache_LessonInfo.txt");
		String strOutput = "";
		for (String Element: LessonList) {
			if (Integer.parseInt(Element.split(",")[2]) == this.id) strOutput += Element.split(",")[0] + "&";
		}
		
		if (strOutput == "") return null;
		else return strOutput.substring(0, strOutput.length() - 1).split("&");
	}
	


    public  String[][] getAccountInfo(){
        String[] data = Other.IO.InputAll(filePath+"Account.txt");
        String[][] newData = new String[data.length][];
        for(int i=0;i<data.length;i++){
            newData[i] = data[i].split(",");
        }
        return newData;
    }

    public  String[][] getLessonInfo(){
        String[] data = Other.IO.InputAll(filePath+"Cache_LessonInfo.txt");
        String[][] newData = new String[data.length][];
        for(int i=0;i<data.length;i++){
            newData[i] = data[i].split(",");
        }
        return newData;
    }
    
    public void verifyAccount(int id){
        String[] userInfo = Other.IO.InputById(filePath + "Account.txt",id).split(",");
        userInfo[userInfo.length-3] = "1";
        String newUserInfo ="";
        for(String Element : userInfo){
            newUserInfo += Element + ",";
        }
        newUserInfo = newUserInfo.substring(0,newUserInfo.length()-1);
        modifyUserAccount(id,newUserInfo);

    }
    public void createNewAccount(int id, String userName, String password, String stateOfVerification,String identity ){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String strDate = Integer.parseInt(sdFormat.format(date))-1911+"";
        String[] userInfo = new String[1];
        userInfo[0] = (id + "," + userName + "," + password + "," + stateOfVerification + "," + identity+","+strDate);
        if(identity.toLowerCase().equals("student")){
            Other.IO.OutputAtLast(filePath+"CourseRegistration.txt",new String[]{id+","});
        }
        Other.IO.OutputAtLast(filePath + "Account.txt", userInfo);
    }

    public void modifyUserAccount(int id,String newUserInfo){
        Other.IO.OutputById(filePath + "Account.txt", newUserInfo, id);
    }

    public void deleteUserAccount(int id){
        Other.IO.OutputById(filePath + "Account.txt", null, id);
        Other.IO.OutputById(filePath + "CourseRegistration.txt", null, id);
    }

    public void createNewLessonInfo(int id, String LessonName, String professor, int creditOfLesson, String LessonType){
        String[] LessonInfo = new String[1];
        LessonInfo[0] = (id + ","+ LessonName + "," + professor + "," + creditOfLesson + "," + LessonType);
        Other.IO.OutputAtLast(filePath + "Cache_LessonInfo.txt",LessonInfo);
        Other.IO.createTxt(filePath+"\\LessonInfo",id);
    }

    public void modifyLessonInfo(String newLessonInfo){
        Other.IO.OutputAll(filePath+"Cache_LessonInfo.txt",newLessonInfo);
    }

    public void deleteLessonInfo(int id){
        Other.IO.OutputById(filePath + "Cache_LessonInfo.txt", null, id);
    }
    //更改
    public void addLessonForStudentInSemester(int studentID, int LessonID,int whichSemester){
        try {
            String[] LessonList = (new Student(studentID).getLessonList());
            for(int index = 0; index < LessonList.length; index += 2){
                if(LessonList[index].equals(whichSemester + "")){
                    LessonList[index + 1] = LessonList[index + 1] + "&" + LessonID;
                    break;
                }
            }
            String outputData = studentID + ",";
            for(int index = 0; index < (LessonList.length - 1); index++){
                outputData += LessonList[index] + "#";
            }
            outputData += LessonList[LessonList.length - 1];
            Other.IO.OutputById(filePath + "CourseRegistration.txt", outputData, studentID);
        }
        catch (ArrayIndexOutOfBoundsException E){
            Other.IO.OutputById(filePath + "CourseRegistration.txt", studentID+","+whichSemester+"#"+LessonID, studentID);
        }

    }

    public void setDefaultScoreForStudentInClass(int studentId,int lessonId, int whichSemester){
        String scoreList[] = Other.IO.InputById(filePath+ "Score\\" + lessonId+".txt",whichSemester).split(",");
        if(scoreList.length==1){
            String[] temp = scoreList.clone();
            scoreList = new String[3];
            scoreList[0] = temp[0];
            scoreList[1] = studentId+"";
            scoreList[2] = "-1";

        }
        else {
            scoreList[1] += "&" + studentId;
            scoreList[2] += "&" + "-1";
        }
        String newData = scoreList[0]+","+scoreList[1]+","+scoreList[2];
        System.out.println(newData);
        Other.IO.OutputById(filePath+ "Score\\" + lessonId+".txt",newData,whichSemester);
    }
    public void deleteScoreForStudentInClass(int studentId,int lessonId, int whichSemester){
        String scoreList[] = Other.IO.InputById(filePath+ "Score\\" + lessonId+".txt",whichSemester).split(",");
        String[] stuArr = scoreList[1].split("&");
        String[] scoreArr = scoreList[2].split("&");
        scoreList[1] = "";
        scoreList[2] = "";
        for(int i=0;i<stuArr.length;i++){
            if((studentId+"").equals(stuArr[i])){
                continue;
            }
            else {
                scoreList[1] += stuArr[i] +"&";
                scoreList[2] += scoreArr[i]+"&";
            }
        }
        if(scoreList[1].length()>0&&"&".equals(scoreList[1].charAt(scoreList[1].length()-1)+"")){
            scoreList[1] = scoreList[1].substring(0,scoreList[1].length()-1);
            scoreList[2] = scoreList[2].substring(0,scoreList[2].length()-1);
        }

        String newData = scoreList[0]+","+scoreList[1]+","+scoreList[2];
        Other.IO.OutputById(filePath+ "Score\\" + lessonId+".txt",newData,whichSemester);
    }


    public String[][] getStudentInfo(){
        String[] stuInfo = Other.IO.InputAll(filePath+"CourseRegistration.txt");
        String[][] stuList = new String[stuInfo.length][1];
        for(int i=0;i<stuInfo.length;i++){
            stuList[i][0] = stuInfo[i].split(",")[0];
        }
        return stuList;
    }


    public void modifyStudentLessonList(int studentId,int classId,int whichSemester ){
        String oldData = Other.IO.InputById(filePath+"CourseRegistration.txt",studentId);
        String[] oldDataList = oldData.split(",")[1].split("#");
        for(int i=0;i<oldDataList.length;i=i+2){
            if(oldDataList[i].equals(whichSemester+"")){
                oldDataList[i+1] += "&" + classId;
            }
            break;
        }
        String newData = studentId+"," ;
        for(int i=0;i<oldDataList.length;i++){
            if(i%2==0){
                newData += oldDataList[i] + "#";
            }
            else {
                newData += oldDataList[i];
            }
        }
        Other.IO.OutputById(filePath+"CourseRegistration.txt",newData,studentId);
    }
    public void deleteStudentLessonList(int studentId,int classId,int whichSemester ){
        String oldData = Other.IO.InputById(filePath+"CourseRegistration.txt",studentId);
        String[] oldDataList = oldData.split(",")[1].split("#");
        for(int i=0;i<oldDataList.length;i=i+2){
            if(oldDataList[i].equals(whichSemester+"")){
                String[] temp = oldDataList[i+1].split("&");
                for(int k=0;k<temp.length;k++){
                    if(temp[k].equals(classId+"")){
                        temp[k] = "";
                    }
                }
                oldDataList[i+1] ="";
                //System.out.println(temp.length);
                for(int k=0;k<temp.length;k++){
                    if(!temp[k].equals("")){
                        oldDataList[i+1] += temp[k] +"&";
                    }
                }
                if(oldDataList[i+1].length()!=0){
                    oldDataList[i+1] = oldDataList[i+1].substring(0,oldDataList[i+1].length()-1);
                }
                break;
            }

        }
        String newData = studentId+"," ;

        if(oldDataList.length>2) {
            for (int i = 0; i < oldDataList.length; i++) {
                if (i % 2 == 0) {
                    newData += oldDataList[i] + "#";
                } else {
                    newData += oldDataList[i];
                }
            }
        }
        else
        //System.out.println(newData);
        Other.IO.OutputById(filePath+"CourseRegistration.txt",newData,studentId);
    }

    private boolean checkIdUnique(int id,String fileName){
        String[] data = Other.IO.InputAll(filePath+fileName);
        for(String info : data){
            if((id+"").equals(info.split(",")[0])){
                return false;
            }
        }
        return true;
    }

    public boolean checkUserId(int id){
        return checkIdUnique(id,"Account.txt");
    }

    public boolean checkClassId(int id){
        return checkIdUnique(id,"Cache_LessonInfo.txt");
    }

    public boolean checkIsStudent(int id){
        if(checkUserId(id))
            return false;
        String userInfo[] = Other.IO.InputById(filePath+"Account.txt",id).split(",");
        if(userInfo[userInfo.length-2].toLowerCase().equals("student")){
            return true;
        }
        return false;
    }
    public boolean checkIsProfessor(int id){
        if(checkUserId(id))
            return false;
        String userInfo[] = IO.InputById(filePath+"Account.txt",id).split(",");
        if(userInfo[userInfo.length-2].toLowerCase().equals("professor")){
            return true;
        }
        return false;
    }
}
