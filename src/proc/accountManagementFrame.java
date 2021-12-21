package proc;

import javax.swing.*;

import Other.Encryptor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import User.Manager;

public class accountManagementFrame extends JFrame {
    private Manager admin = new Manager(1);
    private JFrame popFrame;
    private JLabel idLabel;
    private JTextField idField;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel pwLabel;
    private JTextField pwField;
    private JComboBox typeBox;
    private JButton confirm_btn;

    private JTable accountTable;
    private JScrollPane accountTable_scrollPane;
    private JButton addAccount_Btn;
    private JButton modifyAccount_Btn;
    private JButton deleteAccount_Btn;
    private JButton verifyAccount_Btn;

    public accountManagementFrame(){
        idLabel = new JLabel("請輸入ID");
        idField = new JTextField(20);
        nameLabel = new JLabel("請輸入姓名");
        nameField = new JTextField(20);
        pwLabel = new JLabel("請輸入密碼");
        pwField = new JTextField(20);
        typeBox = new JComboBox(new Object[]{"Student","Manager"});
        confirm_btn = new JButton("新增");

        accountTable = new JTable(admin.getAccountInfo(),new String[]{"ID","姓名","密碼","認證狀況","身分","創立帳號時間"}) {
        	@Override
    		public boolean isCellEditable(int row, int column) {
    			if ((column == 2) || (column == 3)) return false;
    			return true;
    		}
        };
        accountTable_scrollPane = new JScrollPane(accountTable);
        addAccount_Btn = new JButton("新增帳戶");
        modifyAccount_Btn = new JButton("送出修改");
        deleteAccount_Btn = new JButton("刪除所選");
        verifyAccount_Btn = new JButton("認證帳戶");
        popFrame = new JFrame("新增帳戶");
        popFrame.setLayout(null);
        popFrame.setSize(300,200);
        popFrame.setLocation(400,200);

        this.setTitle("帳戶管理");
        this.setLayout(null);
        this.setSize(500,300);
        this.setLocation(450,150);
        popFrame.add(idLabel);
        popFrame.add(idField);
        popFrame.add(nameLabel);
        popFrame.add(nameField);
        popFrame.add(pwLabel);
        popFrame.add(pwField);
        popFrame.add(typeBox);
        popFrame.add(confirm_btn);
        this.add(accountTable_scrollPane);
        this.add(addAccount_Btn);
        this.add(modifyAccount_Btn);
        this.add(deleteAccount_Btn);
        this.add(verifyAccount_Btn);

        idLabel.setSize(100,20);
        idLabel.setLocation(40,48);
        idField.setSize(100,20);
        idField.setLocation(105,50);

        nameLabel.setSize(100,20);
        nameLabel.setLocation(40,68);
        nameField.setSize(100,20);
        nameField.setLocation(105,70);

        pwLabel.setSize(100,20);
        pwLabel.setLocation(40,88);
        pwField.setSize(100,20);
        pwField.setLocation(105,90);

        typeBox.setSize(100,20);
        typeBox.setLocation(105,110);

        confirm_btn.setSize(100,20);
        confirm_btn.setLocation(105,130);


        accountTable_scrollPane.setSize(400,300);

        addAccount_Btn.setSize(100,20);
        addAccount_Btn.setLocation(400,20);
        modifyAccount_Btn.setSize(100,20);
        modifyAccount_Btn.setLocation(400,40);
        deleteAccount_Btn.setSize(100,20);
        deleteAccount_Btn.setLocation(400,60);
        verifyAccount_Btn.setSize(100,20);
        verifyAccount_Btn.setLocation(400,80);

        addListener();
        this.setVisible(true);

    }

    private void addListener(){
        addAccount_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popFrame.setVisible(true);
            }
        });

        modifyAccount_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int yesOrNo =JOptionPane.showConfirmDialog(null,"確定修改?","確認",JOptionPane.YES_NO_OPTION);
                if(yesOrNo==1){
                    return;
                }
                String newData = "";
                for(int i = 0; i< accountTable.getRowCount(); i++){
                    for(int j = 0; j< accountTable.getColumnCount(); j++){
                        newData += accountTable.getValueAt(i,j)+",";
                    }
                    newData = newData.substring(0,newData.length()-1);
                    newData += "\n";
                }
                newData = newData.substring(0, newData.length() - 1);
                String[] newDataArr = newData.split("\n");
                ArrayList<Integer> idList = new ArrayList<Integer>();
                for(String info : newDataArr){
                    int currId = Integer.parseInt(info.split(",")[0]);
                    if(idList.contains(currId)){
                        JOptionPane.showMessageDialog(null,currId+" :已重複,請檢查!");
                        return;
                    }
                    else {
                        idList.add(currId);
                    }
                }
                admin.updateAccount(newData);
                refreshTable();
            }
        });

        deleteAccount_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int yesOrNo =JOptionPane.showConfirmDialog(null,"確定刪除?","確認",JOptionPane.YES_NO_OPTION);
                    if(yesOrNo==1){
                        return;
                    }
                    int id = Integer.parseInt(accountTable.getValueAt(accountTable.getSelectedRow(), 0).toString());
                    admin.deleteUserAccount(id);
                    refreshTable();
                }
                catch (ArrayIndexOutOfBoundsException E){
                    JOptionPane.showMessageDialog(null,"請選擇要刪除的用戶!");
                }
            }
        });

        verifyAccount_Btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(accountTable.getValueAt(accountTable.getSelectedRow(), 0).toString());
                    if(accountTable.getValueAt(accountTable.getSelectedRow(), 3).toString().equals("1")){
                        JOptionPane.showMessageDialog(null,"此用戶已經認證!");
                        return;
                    }
                    admin.verifyAccount(id);
                    JOptionPane.showMessageDialog(null,"認證成功!");
                    refreshTable();
                }
                catch (Exception E){
                    JOptionPane.showMessageDialog(null,"請選擇欲認證用戶!");
                }
            }
        });

        confirm_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    int id = Integer.parseInt(idField.getText());
                    if(!admin.checkUserId(id)){
                        JOptionPane.showMessageDialog(null,"此ID已經存在!");
                        return;
                    }
                    String name  = nameField.getText();
                    String pw = new Encryptor(pwField.getText()).getResult();
                    String type = typeBox.getSelectedItem().toString();
                    if(name.trim().equals("")||pw.trim().equals("")){
                        JOptionPane.showMessageDialog(null,"有欄位尚未填入!");
                        return;
                    }
                    admin.createNewAccount(id,name,pw,"0",type);
                    refreshTable();
                    popFrame.setVisible(false);
                }
                catch (NumberFormatException E){
                    JOptionPane.showMessageDialog(null,"ID只能輸入數字!");
                    return;
                }
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

    public void refreshTable(){
        this.remove(accountTable_scrollPane);
        accountTable = new JTable(admin.getAccountInfo(),new String[]{"ID","姓名","密碼","認證狀況","身分","創立帳號時間"});
        accountTable_scrollPane = new JScrollPane(accountTable);
        accountTable_scrollPane.setSize(400,300);
        this.add(accountTable_scrollPane);
        this.setVisible(false);
        this.setVisible(true);
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

