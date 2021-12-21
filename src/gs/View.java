package gs;

import java.awt.*;
import javax.swing.*;

public class View {
	
	View() {
		calculateCreatePosition();
		initLogin();
		initHome();
	}
	
	private int createLeft;
	private int createTop;
	private void calculateCreatePosition() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.createLeft = (int)(screen.width / 3.5);
		this.createTop = (int)(screen.height / 3.5);
	}
	
	
	
	/* Login */
	private JFrame frameLogin;
	private Container paneLogin;
	private JLabel lbLogin;
	private JTextField taAccount;
	private JTextField taPassword;
	private JButton btnLogin;
	
	private void initLogin() {
		frameLogin = new JFrame("登入");
		frameLogin.setBounds(this.createLeft, this.createTop, 500, 200);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.getContentPane().setLayout(null);
		paneLogin = frameLogin.getContentPane();
		
		lbLogin = new JLabel("請輸入帳密");
		lbLogin.setBounds(190, 20, 100, 20);
		lbLogin.setFont(new Font("Dialog", 1, 20));
		paneLogin.add(lbLogin);
		
		taAccount = new JTextField(5);
		taAccount.setBounds(140, 60, 200, 20);
		paneLogin.add(taAccount);
		taPassword = new JTextField(5);
		taPassword.setBounds(140, 90, 200, 20);
		paneLogin.add(taPassword);
		
		btnLogin = new JButton("確定");
		btnLogin.setBounds(140, 120, 200, 20);
		paneLogin.add(btnLogin);
		
		frameLogin.setVisible(true);
	}
	
	JTextField getTaAccount() {
		return taAccount;
	}
	JTextField getTaPassword() {
		return taPassword;
	}
	void clearTaPassword() {
		taPassword.setText("");
	}
	JButton getBtnLogin() {
		return btnLogin;
	}
	void showLoginFailure(String Message) {
		JOptionPane.showMessageDialog(this.frameLogin, Message, "登入失敗", JOptionPane.ERROR_MESSAGE);
	}
	
	void loginSuccess(String identity) {
		frameLogin.dispose();
		frame.setVisible(true);
		
		// set Conponent By Identity
		switch (identity) {
			case "Student":
				menuItemSetScore.setVisible(false);
				menuManage.setVisible(false);
				break;
				
			case "Manager":
				menuItemOwnGrade.setVisible(false);
				break;
				
			case "SuperUser":
				OP.Main.openOPFrame();
				frame.dispose();
				break;
				
			default:
				JOptionPane.showMessageDialog(this.frameLogin, "身分不明的使用者", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/* Home */
	private JFrame frame;
	private Container panel;
	private JLabel lb01;
	private JMenuBar menuBar;
	private JMenu menuAccount;
	JMenuItem menuItemOwnInfo;
	JMenuItem menuItemChangePassword;
	JMenuItem menuItemLogout;
	JMenuItem menuItemExit;
	private JMenu menuGrade;
	JMenuItem menuItemOwnGrade;
	JMenuItem menuItemSetScore;
	private JMenu menuTest;
	JMenuItem menuItemSearchTestgroup;
	private JMenu menuManage;
	JMenuItem menuItemManageAccount;
	JMenuItem menuItemManageTest;
	JMenuItem menuItemSetTestForStudent;
	JMenuItem menuItemPrintTranscript;
	
	private void initHome() {
		frame = new JFrame();
		frame.setTitle("成績系統");
		panel = frame.getContentPane();
		panel.setBackground(SystemColor.activeCaptionBorder);
		
		lb01 = new JLabel("高燕大成績管理系統");
		lb01.setHorizontalAlignment(SwingConstants.CENTER);
		lb01.setFont(new Font("Dialog", 1, 20));
		lb01.setForeground(Color.WHITE);
		panel.add(lb01, BorderLayout.CENTER);
		frame.setBounds(this.createLeft, this.createTop, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		menuAccount = new JMenu("帳戶");
		menuBar.add(menuAccount);
		
		menuItemOwnInfo = new JMenuItem("個人資料");
		menuAccount.add(menuItemOwnInfo);
		
		menuItemChangePassword = new JMenuItem("更改密碼");
		menuAccount.add(menuItemChangePassword);
		
		menuItemLogout = new JMenuItem("登出");
		menuAccount.add(menuItemLogout);
		
		menuItemExit = new JMenuItem("離開");
		menuAccount.add(menuItemExit);
		
		menuGrade = new JMenu("成績");
		menuBar.add(menuGrade);
		
		menuItemOwnGrade = new JMenuItem("檢視個人成績");
		menuGrade.add(menuItemOwnGrade);
		
		menuItemSetScore = new JMenuItem("成績批閱");
		menuGrade.add(menuItemSetScore);
		
		menuLesson = new JMenu("測驗");
		menuBar.add(menuTest);
		
		menuItemSearchLesson = new JMenuItem("測驗群組");
		menuLesson.add(menuItemSearchTestgroup);
		
		menuManage = new JMenu("管理");
		menuBar.add(menuManage);
		
		menuItemManageAccount = new JMenuItem("帳戶管理");
		menuManage.add(menuItemManageAccount);
		
		menuItemManageLesson = new JMenuItem("測驗管理");
		menuManage.add(menuItemManageTest);
		
		menuItemSetTestForStudent = new JMenuItem("更動學生測驗");
		menuManage.add(menuItemSetTestForStudent);
		
		menuItemPrintTranscript = new JMenuItem("學生總成績單");
		menuManage.add(menuItemPrintTranscript);
	}
	
	void relogin() {
		frame.dispose();
		initLogin();
		initHome();
	}
	
	void closeHomeFrame() {
		frame.dispose();
	}
	
}
