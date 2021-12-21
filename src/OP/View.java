package OP;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class View {
	private JFrame frame;
	private Container container;
	private JMenuBar menuBar;
	JButton menuSimulation;
	JButton menuAutomation;
	JButton menuLogout;
	JButton menuExit;
	
	
	private CardLayout card;
	private JPanel panel;
	
	private JPanel paneSimulation;
	private JLabel lbSetYear;
	JTextField taSetYear;
	JButton btnSetYear;
	private JLabel lbSetDBPath;
	JTextField taSetDBPath;
	JButton btnSetDBPath;
	
	private JPanel paneAutomation;
	private JLabel lbBackup;
	JTextField taBackup;
	JButton btnBackup;
	
	View(){
		frame = new JFrame();
		frame.setTitle("系統設置");
		container = frame.getContentPane();
		container.setLayout(new BorderLayout(0, 0));
		container.setBackground(SystemColor.activeCaptionBorder);
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuSimulation = new JButton("模擬");
		menuBar.add(menuSimulation);
		menuAutomation = new JButton("自動化");
		menuBar.add(menuAutomation);
		menuLogout = new JButton("登出");
		menuBar.add(menuLogout);
		menuExit = new JButton("離開");
		menuBar.add(menuExit);
		
		card = new CardLayout();
		panel = new JPanel();
		panel.setLayout(card);
		initSimulation();
		initAutomation();
		panel.add(paneSimulation, "Simulation");
		panel.add(paneAutomation, "Automation");
		container.add(panel, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
	
	void showCard(String name) {
		card.show(panel, name);
	}
	
	void initSimulation() {
		paneSimulation = new JPanel();
		paneSimulation.setBackground(SystemColor.activeCaptionBorder);
		paneSimulation.setLayout(null);
		
		lbSetYear = new JLabel("設置年分");
		lbSetYear.setBounds(10, 20, 50, 25);
		paneSimulation.add(lbSetYear);
		taSetYear = new JTextField();
		taSetYear.setBounds(70, 20, 100, 25);
		paneSimulation.add(taSetYear);
		btnSetYear = new JButton("確定");
		btnSetYear.setBounds(190, 20, 60, 25);
		paneSimulation.add(btnSetYear);
		
		lbSetDBPath = new JLabel("設置資料庫路徑");
		lbSetDBPath.setBounds(10, 70, 90, 25);
		paneSimulation.add(lbSetDBPath);
		taSetDBPath = new JTextField();
		taSetDBPath.setBounds(110, 70, 300, 25);
		paneSimulation.add(taSetDBPath);
		btnSetDBPath = new JButton("確定");
		btnSetDBPath.setBounds(415, 70, 60, 25);
		paneSimulation.add(btnSetDBPath);
	}
	
	void initAutomation() {
		paneAutomation = new JPanel();
		paneAutomation.setBackground(SystemColor.activeCaptionBorder);
		paneAutomation.setLayout(null);
		
		lbBackup = new JLabel("備份");
		lbBackup.setBounds(10, 20, 50, 25);
		paneAutomation.add(lbBackup);
		taBackup = new JTextField();
		taBackup.setBounds(70, 20, 100, 25);
		paneAutomation.add(taBackup);
		btnBackup = new JButton("確定");
		btnBackup.setBounds(190, 20, 60, 25);
		paneAutomation.add(btnBackup);
	}
	
	void showSettingSuccess(String Message) {
		JOptionPane.showMessageDialog(this.frame, Message, "設定", JOptionPane.INFORMATION_MESSAGE);
	}
	
	void logout() {
		gs.Main.openGSFrame();
		frame.dispose();
	}
	
	void exit() {
		frame.dispose();
	}
	
}
