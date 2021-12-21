package proc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import GS.proc.component.Table;
import Other.Lesson;
import User.Student;

public class showStudentList {
	
	public static void main(String[] args) {
		new showStudentList(1, 109);
	}
	
	showStudentList(int id, int semester){
		new Controller(new Model(id, semester), new View());
	}

	private class Controller {
		private Model model;
		private View view;
		
		Controller(Model m, View v){
			this.model = m;
			this.view = v;
			
			// init Controller
			view.setTable(model.getStudentList());
			view.btn.addActionListener(e -> {
				view.frame.dispose();
			});
		}
	}
	
	private class Model {
		private Test test;
		
		Model(int TestId, int Semester){
			test = new Test(TestId, Semester);
		}
		
		String[][] getStudentList(){
			String[] studentList = test.getStudentList();
			String[][] outputData = new String[studentList.length][2];
			
			for (int index = 0; index < studentList.length; index++) {
				outputData[index] = new String[] {studentList[index], new Student(Integer.parseInt(studentList[index])).getName()};
			}
			return outputData;
		}
		
	}
	
	private class View {
		private JFrame frame;
		private Container container;
		private JScrollPane scrollPane;
		private JButton btn;
		private Table table;
		
		View(){
			frame = new JFrame();
			frame.setTitle("學生清單");
			container = frame.getContentPane();
			container.setLayout(new BorderLayout(0, 0));
			container.setBackground(SystemColor.activeCaptionBorder);
			frame.setBounds(100, 100, 500, 400);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			btn = new JButton("確定");
			container.add(btn, BorderLayout.NORTH);
			
			scrollPane = new JScrollPane();
			container.add(scrollPane, BorderLayout.CENTER);
			
			frame.setVisible(true);
		}
		
		void setTable(String[][] data){
			String[] columns = {"Id", "學生姓名"};
			this.table = new Table(columns, data, true);
			scrollPane.setViewportView(this.table);
		}
		
	}
	
}

