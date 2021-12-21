package proc;
import Other.Test;
import User.Student;
import User.Manager;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addTestForStudentFrame extends JFrame {
    private Manager admin = new Manager(1);
    private JTable studentTable;
    private JScrollPane studentTable_scrollPane;

    private JButton chooseBtn;
    private JFrame popFrame;
    private JButton addTestBtn;
    private JButton deleteTestBtn;
    private JComboBox newClassBox;
    private JTable classTable;
    private JScrollPane classTable__scrollPane;

    private String yearNow;



    public TestForStudentFrame(){
        studentTable = new JTable(admin.getStudentInfo(),new String[]{"學生ID"});
        studentTable_scrollPane = new JScrollPane(studentTable);
        chooseBtn = new JButton("選取");
        setPopFrame();

        this.setLayout(null);
        this.setSize(500,400);
        this.setLocation(400,350);

        this.add(studentTable_scrollPane);
        this.add(chooseBtn);

        studentTable_scrollPane.setSize(50,300);
        studentTable_scrollPane.setLocation(100,0);
        chooseBtn.setSize(90,90);
        chooseBtn.setLocation(220,80);

        yearNow = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()))-1911+"";
        addListener();
        this.setVisible(true);
    }

    private void setPopFrame(){
        addTestBtn = new JButton("增加測驗");
        deleteTestBtn = new JButton("刪除測驗");
        newClassBox = new JComboBox();
        popFrame = new JFrame("測驗選單");
        classTable = new JTable(new String[][]{{"id","name"}},new String[]{"測驗ID","測驗名稱"});
        classTable__scrollPane = new JScrollPane(classTable);

        popFrame.setLayout(null);
        popFrame.setTitle("測驗系統");
        popFrame.setSize(400,400);
        popFrame.setLocation(400,200);
        popFrame.add(addTestBtn);
        popFrame.add(deleteTestBtn);
        popFrame.add(classTable__scrollPane);
        popFrame.add(newClassBox);

        addTestBtn.setSize(120,20);
        addTestBtn.setLocation(240,100);
        deleteTestBtn.setSize(120,20);
        deleteTestBtn.setLocation(240,120);
    }

    private void addListener(){
        chooseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(studentTable.getSelectedRow()==-1){
                    JOptionPane.showMessageDialog(null,"請選擇學生!");
                    return;
                }
                int stuID = Integer.parseInt(studentTable.getValueAt(studentTable.getSelectedRow(),0).toString());
                try {
                    String[] studentClass = new Student(stuID).getTestList();
                    String[][] TestList = admin.getTestInfo();
                    String yearNowClass = "";
                    popFrame.getContentPane().remove(newClassBox);
                    newClassBox = new JComboBox();
                    boolean flag = false;
                    for(int i=0;i<studentClass.length;i=i+2){
                        if(studentClass[i].equals(yearNow)){
                            yearNowClass = studentClass[i+1];
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        for(String[] line : TestList){
                            boolean check = true;
                            for(String stuLesson : yearNowClass.split("&")){
                                if(line[0].equals(stuLesson)){
                                    check = false;
                                }
                            }
                            if(check){
                            	Test currTest  = new Test(Integer.parseInt(line[0]));
                                newClassBox.addItem(line[0]+":"+currTest.name);
                            }
                        }
                    }
                    popFrame.add(newClassBox);
                    newClassBox.setSize(120,30);
                    newClassBox.setLocation(200,50);
                    refreshTable();
                    popFrame.setVisible(true);
                }
                catch (ArrayIndexOutOfBoundsException E){
                    //E.printStackTrace();
                    classTable__scrollPane.setLocation(20,50);
                    classTable__scrollPane.setSize(150,200);
                    String[][] lessonList = admin.getTestInfo();
                    popFrame.getContentPane().remove(newClassBox);
                    newClassBox = new JComboBox();
                    for(String[] line : lessonList){
                       Test currTest  = new Test(Integer.parseInt(line[0]));
                        newClassBox.addItem(line[0]+":"+currTest.name);
                    }
                    popFrame.add(newClassBox);
                    newClassBox.setSize(120,30);
                    newClassBox.setLocation(200,50);
                    refreshTable();
                    popFrame.setVisible(true);
                }

            }
        });

        addLessonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int classID = Integer.parseInt(newClassBox.getSelectedItem().toString().split(":")[0]);
                    int stuID = Integer.parseInt(studentTable.getValueAt(studentTable.getSelectedRow(), 0).toString());
                    admin.addLessonForStudentInSemester(stuID, classID, Integer.parseInt(yearNow));
                    admin.setDefaultScoreForStudentInClass(stuID,classID,Integer.parseInt(yearNow));
                    JOptionPane.showMessageDialog(null,"新增成功");
                    refreshTable();
                    popFrame.setVisible(false);
                }
                catch (Exception E){
                    E.printStackTrace();
                    JOptionPane.showMessageDialog(null,"新增失敗!");
                }
            }
        });

        deleteLessonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(classTable.getSelectedRow()==-1){
                        JOptionPane.showMessageDialog(null,"請選擇測驗");
                    }
                    int classID = Integer.parseInt(classTable.getValueAt(classTable.getSelectedRow(),0).toString());
                    int stuID = Integer.parseInt(studentTable.getValueAt(studentTable.getSelectedRow(), 0).toString());
                    admin.deleteScoreForStudentInClass(stuID,classID,Integer.parseInt(yearNow));
                    admin.deleteStudentTestList(stuID,classID,Integer.parseInt(yearNow));
                    JOptionPane.showMessageDialog(null,"刪除成功");
                    refreshTable();
                    popFrame.setVisible(false);
                }
                catch (Exception E){
                    //E.printStackTrace();
                    JOptionPane.showMessageDialog(null,"刪除失敗!");
                }
            }
        });

        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }
    private void refreshTable(){
        popFrame.getContentPane().remove(classTable__scrollPane);
        int stuID = Integer.parseInt(studentTable.getValueAt(studentTable.getSelectedRow(), 0).toString());
        try {
            String[] TestList = new Student(stuID).getTestList();
            String yearNowTest = "";
            boolean flag = false;
            for(int i=0;i<TestList.length;i=i+2){
                if(yearNow.equals(TestListt[i])){
                    flag = true;
                    yearNowTest = TestList[i+1];
                    break;
                }
            }
            if(flag){
                String[][] tableData = new String[yearNowTest.split("&").length][];
                for(int i=0;i<yearNowTest.split("&").length;i++){
                	Test test = new Test(Integer.parseInt(yearNowTest.split("&")[i]));
                    tableData[i] = (yearNowTest.split("&")[i] +"," + Test.name).split(",");
                }
                classTable = new JTable(tableData,new String[]{"測驗ID","測驗名稱"});
            }
            else {
                classTable = new JTable(new String[][]{{"id","name"}},new String[]{"測驗ID","測驗名稱"});
            }
        }
        catch (ArrayIndexOutOfBoundsException E){
            //E.printStackTrace();
            classTable = new JTable(new String[][]{{"id","name"}},new String[]{"測驗ID","測驗名稱"});
        }

        finally {
            classTable__scrollPane = new JScrollPane(classTable);
            popFrame.add(classTable__scrollPane);
            classTable__scrollPane.setLocation(20,50);
            classTable__scrollPane.setSize(150,200);
        }


    }

    public void exit(){
        int yesOrNo =JOptionPane.showConfirmDialog(null,"確定退出?","確認",JOptionPane.YES_NO_OPTION);
        if(yesOrNo==1){
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
        else {
            this.setVisible(false);
        }
    }

}
