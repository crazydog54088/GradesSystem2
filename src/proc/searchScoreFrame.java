package proc;

import Other.Lesson;
import User.Student;
import User.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class searchScoreFrame extends JFrame {
    private Manager admin = new Manager(1);
    private JComboBox studentBox;
    private JButton confirm_btn;

    private JFrame popFrame;
    private JComboBox yearBox;
    private JButton search_btn;
    private JLabel nameLabel;
    private JScrollPane scoreTable_scrollPane;


    public searchScoreFrame(){
        studentBox = new JComboBox(getStudentList());
        confirm_btn = new JButton("查詢");
        this.setLayout(null);
        this.setTitle("查詢成績");
        this.setSize(300,200);
        this.setLocation(450,400);
        this.add(studentBox);
        this.add(confirm_btn);

        studentBox.setSize(100,40);
        studentBox.setLocation(100,20);

        confirm_btn.setSize(100,30);
        confirm_btn.setLocation(100,80);

        popFrameInit();
        addListener();
        this.setVisible(true);
    }

    private void popFrameInit(){
        popFrame = new JFrame("查詢結果");
        search_btn = new JButton("查詢");
        popFrame.setLayout(null);
        popFrame.setSize(500,500);
        popFrame.setLocation(400,300);
        popFrame.add(search_btn);
        search_btn.setSize(120,20);
        search_btn.setLocation(270,20);

    }

    private void addListener(){
        confirm_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameLabel!=null){
                    popFrame.getContentPane().remove(nameLabel);
                }
                if(scoreTable_scrollPane!=null){
                    popFrame.getContentPane().remove(scoreTable_scrollPane);
                }
                if(yearBox!=null){
                    popFrame.getContentPane().remove(yearBox);
                }
                int selectID = Integer.parseInt(studentBox.getSelectedItem().toString().split(":")[0]);
                String[] scoreList = new Student(selectID).getScoreList();
                String[] TestList = new Student(selectID).getTestList();
                String yearList ="";

                for(int i=0;i<scoreList.length;i = i+2){
                    yearList += lessonList[i]+",";
                }
                yearBox = new JComboBox(yearList.split(","));
                nameLabel = new JLabel(selectID+":"+new Student(selectID).getName());
                popFrame.add(yearBox);
                popFrame.add(nameLabel);
                nameLabel.setSize(150,30);
                nameLabel.setLocation(50,15);
                yearBox.setSize(120,20);
                yearBox.setLocation(150,20);

                popFrame.setVisible(true);
            }
        });

        search_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String studentId = nameLabel.getText().split(":")[0];
                String year = yearBox.getSelectedItem().toString();

                Student student = new Student(Integer.parseInt(studentId));
                String[] studentClassList = student.getTestList();
                String[] studentScoreList = student.getScoreList();
                String[] targetClass = null;
                String[] targetScore = null;

                for (int i = 0; i < studentClassList.length; i = i + 2) {
                    if (studentClassList[i].equals(year + "")) {
                        targetClass = studentClassList[i + 1].split("&");
                        targetScore = studentScoreList[i + 1].split("&");
                    }
                }

                String[][] scoreData = new String[targetClass.length][];
                for (int i = 0; i < targetClass.length; i++) {
                    int claasId = Integer.parseInt(targetClass[i]);
                    Test currtest = new Test(claasId);
                    scoreData[i] = (currtest.name +","+ targetScore[i]).split(",");
                }
                if(scoreTable_scrollPane!=null){
                    popFrame.getContentPane().remove(scoreTable_scrollPane);
                }
                scoreTable_scrollPane = new JScrollPane(new JTable(scoreData,new String[]{"科目","成績"}));
                popFrame.add(scoreTable_scrollPane);
                scoreTable_scrollPane.setSize(400,300);
                scoreTable_scrollPane.setLocation(10,100);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                exit();
            }
        });
    }


    private String[] getStudentList(){
        String[][] data = admin.getStudentInfo();
        String stuID = "";
        for(String[] arr : data){
            stuID += arr[0]+":"+new Student(Integer.parseInt(arr[0])).getName() + ",";
        }
        return stuID.split(",");
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

