package proc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Other.TEST;
import User.Manager;

public class showTestInfo {
	
	showTestInfo(int id, int semester){
		new Controller(new Model(id, semester), new View());
	}

	private class Controller {
		private Model model;
		private View view;
		
		Controller(Model m, View v){
			this.model = m;
			this.view = v;
			
			// init Controller
			view.setInfo(model.getLesson());
			view.btnShowStudentList.addActionListener(e -> {
				new showStudentList(model.getTest().id, model.getTest().semester);
			});
		}
	}
	
	private class Model {
		
		private Test test;
		
		Model(int id, int semester){
			this.test = new Test(id, semester);
		}
		
		Lesson getTest() {
			return this.test;
		}
		
	}
	
	private class View {
		private JFrame frame;
		private Container container;
		private JPanel panel;
		private JPanel paneId;
		private JLabel lbId;
		private JLabel lbInputId;
		private JPanel paneName;
		private JLabel lbName;
		private JLabel lbInputName;
		private JPanel panemanager;
		private JLabel lbmanager;
		private JLabel lbInputmanager;
		private JPanel panetime;
		private JLabel lbtime;
		private JLabel lbInputtime;
		private JPanel panedifficult;
		private JLabel lbdifficult;
		private JLabel lbInputdifficult;
		JButton btnShowStudentList;
		
		View(){
			frame = new JFrame();
			frame.setTitle("成績系統");
			container = frame.getContentPane();
			container.setBackground(SystemColor.activeCaptionBorder);
			container.setLayout(null);
			
			panel = new JPanel();
			panel.setBounds(43, 40, 399, 280);
			container.add(panel);
			panel.setLayout(null);
			
			paneId = new JPanel();
			paneId.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			paneId.setBounds(123, 41, 58, 48);
			panel.add(paneId);
			paneId.setLayout(new BorderLayout(0, 0));
			
			lbId = new JLabel("ID     ");
			lbId.setFont(new Font("新細明體", Font.BOLD, 15));
			paneId.add(lbId, BorderLayout.WEST);
			
			lbInputId = new JLabel();
			lbInputId.setFont(new Font("新細明體", Font.PLAIN, 15));
			paneId.add(lbInputId, BorderLayout.CENTER);
			
			paneName = new JPanel();
			paneName.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			paneName.setBounds(203, 41, 120, 48);
			panel.add(paneName);
			paneName.setLayout(new BorderLayout(0, 0));
			
			lbName = new JLabel("名稱    ");
			lbName.setFont(new Font("新細明體", Font.BOLD, 15));
			paneName.add(lbName, BorderLayout.WEST);
			
			lbInputName = new JLabel();
			lbInputName.setFont(new Font("新細明體", Font.PLAIN, 15));
			paneName.add(lbInputName, BorderLayout.CENTER);
			
			paneProfessor = new JPanel();
			paneProfessor.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			paneProfessor.setBounds(147, 193, 100, 48);
			panel.add(paneProfessor);
			paneProfessor.setLayout(new BorderLayout(0, 0));
			
			lbmanager = new JLabel("管理員    ");
			lbmanager.setFont(new Font("新細明體", Font.BOLD, 15));
			panemanager.add(lbProfessor, BorderLayout.WEST);
			
			lbInputmanager = new JLabel();
			lbInputmanager.setFont(new Font("新細明體", Font.PLAIN, 15));
			panemanager.add(lbInputmanager, BorderLayout.CENTER);
			
			panetime = new JPanel();
			panetime.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			panetime.setBounds(81, 120, 100, 48);
			panel.add(paneCredits);
			panetime.setLayout(new BorderLayout(0, 0));
			
			lbtime = new JLabel("學分數    ");
			lbtime.setFont(new Font("新細明體", Font.BOLD, 15));
			panetime.add(lbtime, BorderLayout.WEST);
			
			lbInputtime = new JLabel();
			lbInputtime.setFont(new Font("新細明體", Font.PLAIN, 15));
			panetime.add(lbInputtime, BorderLayout.CENTER);
			
			panedifficult = new JPanel();
			panedifficult.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			panedifficult.setBounds(203, 120, 100, 48);
			panel.add(panedifficult);
			panedifficult.setLayout(new BorderLayout(0, 0));
			
			lbdifficult= new JLabel("類別    ");
			lbdifficult.setFont(new Font("新細明體", Font.BOLD, 15));
			panedifficult.add(lbdifficult, BorderLayout.WEST);
			
			lbInputdifficult = new JLabel();
			lbInputdifficult.setFont(new Font("新細明體", Font.PLAIN, 15));
			panedifficultadd(lbInputdifficult, BorderLayout.CENTER);
			
			btnShowStudentList = new JButton("學生清單");
			btnShowStudentList.setBounds(270, 250, 120, 25);
			panel.add(btnShowStudentList);

			frame.setBounds(100, 100, 500, 400);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
		void setInfo(Test test) {
			lbInputId.setText(test.id + "");
			lbInputName.setText(test.name);
			lbInputmanager.setText(new Pmanager(test.managerId).getName());
			lbInputtime.setText(test.credits + "");
			lbInputdifficult.setText(test.type);
		}
		
	}
	
}