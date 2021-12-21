package proc;

import User.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestManagementFrame extends JFrame {
    private Manager admin = new Manager(1);
    private JTable TestTable;
    private JScrollPane TestTable_scrollPane;
    private JButton addTestButton;
    private JButton modifyTestButton;
    private JButton deleteTestButton;

    private JFrame popFrame;
    private JLabel classIdLabel;
    private JTextField classIdField;
    private JLabel classNameLabel;
    private JTextField classNameField;
    private JLabel managerIdLabel;//改用box
    private JComboBox managerIdBox;
    private JLabel testtimeLabel;
    private JTextField testtimeField;
    private JComboBox difficultBox;
    private JButton confirm_btn;
    private JLabel remindLabel1;
    private JLabel remindLabel2;

    public lessonManagementFrame(){
    	TestTable = new JTable(admin.getLessonInfo(),new Object[]{"測驗ID","測驗名稱","管理者ID","測驗時間","難易度"});
    	TestTable_scrollPane = new JScrollPane(TestTable);
        addTestButton =new JButton("新增");
        modifyTestButton = new JButton("送出修改");
        deleteTestButton =new JButton("刪除");
        remindLabel1 = new JLabel("修改資料後");
        remindLabel2 = new JLabel("請按\"送出修改\"");

        this.setLayout(null);
        this.setTitle("測驗管理");
        this.setSize(520,350);
        this.setLocation(400,200);
        this.add(TestTable_scrollPane);
        this.add(addTestButton);
        this.add(modifyTestButton);
        this.add(deleteTestButton);
        this.add(remindLabel1);
        this.add(remindLabel2);

        TestTable_scrollPane.setSize(350,300);
        TestTable_scrollPane.setLocation(10,10);

        addTestButton.setSize(100,20);
        addTestButton.setLocation(400,20);
        modifyTestButton.setSize(100,20);
        modifyTestButton.setLocation(400,40);
        deleteTestButton.setSize(100,20);
        deleteTestButton.setLocation(400,60);
        remindLabel1.setSize(100,30);
        remindLabel1.setLocation(400,80);
        remindLabel2.setSize(100,30);
        remindLabel2.setLocation(400,100);

        setPopFrame();
        addListener();
        this.setVisible(true);
    }

    private void setPopFrame(){
        popFrame = new JFrame("新增測驗");
        classIdLabel = new JLabel("請輸入新增測驗的ID");
        classIdField = new JTextField(20);
        classNameLabel = new JLabel("請輸入測驗名稱");
        classNameField = new JTextField(20);
        managerIdLabel = new JLabel("請輸入該測驗管理者ID");//box
        managerIdBox = new JComboBox();
        testtimeLabel = new JLabel("測驗時間");
        testtimeField = new JTextField(20);
        difficultBox = new JComboBox(new Object[]{"基礎","進階"});
        confirm_btn = new JButton("送出");

        for(String[] data : admin.getAccountInfo()){
            if("professor".equals(data[4].toLowerCase())){
                professorIdBox.addItem(data[0]);
            }
        }

        popFrame.setLayout(null);
        popFrame.setSize(400,250);
        popFrame.setLocation(400,200);
        popFrame.add(classIdLabel);
        popFrame.add(classIdField);
        popFrame.add(classNameLabel);
        popFrame.add(classNameField);
        popFrame.add(managerIdLabel);
        popFrame.add(managerIdBox);
        popFrame.add(testtimeLabel);
        popFrame.add(testtimeField);
        popFrame.add(difficultBox);
        popFrame.add(confirm_btn);

        classIdLabel.setSize(120,20);
        classIdLabel.setLocation(100,50);
        classIdField.setSize(120,20);
        classIdField.setLocation(220,50);
        classNameLabel.setSize(120,20);
        classNameLabel.setLocation(100,70);
        classNameField.setSize(120,20);
        classNameField.setLocation(220,70);
        managerIdLabel.setSize(120,20);
        managerIdLabel.setLocation(100,90);
        managerIdBox.setSize(120,20);
        managerIdBox.setLocation(220,90);
        testtimeLabel.setSize(120,20);
        testtimeLabel.setLocation(100,110);
        testtimeField.setSize(120,20);
        testtimeField.setLocation(220,110);
        difficultBox.setSize(120,20);
        difficultBox.setLocation(220,130);
        confirm_btn.setSize(120,20);
        confirm_btn.setLocation(220,150);
    }

    private void addListener(){
        addtestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popFrame.setVisible(true);
            }
        });

        modifytestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String newData = "";
                    for(int i = 0; i< testTable.getRowCount(); i++){
                        for(int j = 0; j< testTable.getColumnCount(); j++){
                            newData += testTable.getValueAt(i,j)+",";
                        }
                        newData = newData.substring(0,newData.length()-1);
                        newData += "\n";
                    }
                    admin.modifytestInfo(newData);
                    refreshTable();
                    JOptionPane.showMessageDialog(null,"修改成功!");

                }
                catch (NumberFormatException E){
                    JOptionPane.showMessageDialog(null,"ID和測驗時間只能輸入數字!");
                    return;
                }
            }

        });

        deleteLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int testId = Integer.parseInt(testTable.getValueAt(testTable.getSelectedRow(), 0).toString());
                    int yesOrNo =JOptionPane.showConfirmDialog(null,"確定修改?","確認",JOptionPane.YES_NO_OPTION);
                    if(yesOrNo==1){
                        return;
                    }
                    admin.deleteLessonInfo(testId);
                    refreshTable();
                }
                catch (ArrayIndexOutOfBoundsException E){
                    JOptionPane.showMessageDialog(null,"請選擇要刪除的測驗!");
                    return;
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        confirm_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int classID = Integer.parseInt(testtime.getText());
                    for(String[] data :admin.getTestInfo()){
                        if(data.equals(classID+"")){
                            JOptionPane.showMessageDialog(null,"測驗ID已存在!");
                            return;
                        }
                    }
                    String className = testtimeField.getText();
                    int managerID = Integer.parseInt(managerIdBox.getSelectedItem().toString());
                    int testtime = Integer.parseInt(testtimeField.getText());
                    String  difficult =difficultBox.getSelectedItem().toString();
                    admin.createNewtestInfo(classID, className, managerID + "", testtime, difficult);
                    refreshTable();
                    popFrame.setVisible(false);
                    JOptionPane.showMessageDialog(null,"新增成功");
                }
                catch (NumberFormatException E){
                    JOptionPane.showMessageDialog(null,"ID 測驗時間只能輸入數字");
                }
            }
        });
    }

    private void refreshTable(){
        this.getContentPane().remove(TestTable_scrollPane);
        TestTable = new JTable(admin.getTestInfo(),new Object[]{"測驗ID","測驗名稱","管理者ID","測驗時間","基礎/進階"});
        TestTable_scrollPane = new JScrollPane(TestTable);
        this.getContentPane().add(TestTable_scrollPane);
        TestTable_scrollPane.setSize(350,300);
        TestTable_scrollPane.setLocation(10,10);
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