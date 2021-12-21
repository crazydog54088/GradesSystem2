// 測試捲軸變數是否適當設置

package proc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GS.proc.component.Table;
import User.Student;

public class OwnGrade {
	public static void main(String[] args) {
		new OwnGrade(4);
	}
	
	public OwnGrade(int id) {
		new Controller(new Model(id), new View());
	}
	
	
	private class Controller{
		private Model model;
		private View view;
		
		Controller(Model m, View v){
			this.model = m;
			this.view = v;
			
			// show all score
			view.showTable(model.getColumns(), model.getAllScore());
		}
		
	}
	
	private class Model {
		private Student student;
		private String[] columns = {"Id", "測驗名稱", "分數"};
		
		Model(int id){
			this.student = new Student(id);
		}
		
		String[] getColumns() {
			return this.columns;
		}
		
		String[][] getAllScore(){
			return student.getAllScore();
		}
		
	}
	
	private class View {
		private JFrame frame;
		private Container container;
		private JScrollPane scrollPane;
		private JPanel panel;
		private JButton btn;
		
		private int intCellNumber = 0;
		
		View(){
			frame = new JFrame();
			frame.setTitle("歷年成績");
			container = frame.getContentPane();
			container.setLayout(new BorderLayout(0, 0));
			container.setBackground(SystemColor.activeCaptionBorder);
			
			// 製作 FlowLayout + 捲軸
			panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 12));
			scrollPane = new JScrollPane(panel);
			container.add(scrollPane, BorderLayout.CENTER);
			panel.setPreferredSize(new Dimension(460, 318));
			// 使用 BorderLayout 設置 scrollPane 的大小
			//scrollPane.setPreferredSize(new Dimension(460, 400));
			
			btn = new JButton("確定");
			container.add(btn, BorderLayout.NORTH);
			btn.setPreferredSize(new Dimension(480, 40));
			btn.addActionListener(e -> frame.dispose());
			
			frame.setBounds(100, 100, 500, 400);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
		void showTable(String[] columns, String[][] data) {
			for (int index = 0; index < data.length; index++) {
				int dataNumber = Integer.parseInt(data[index][1]);
				String[][] outputData = new String[dataNumber][3];
				JPanel tablePane = new JPanel();
				tablePane.setPreferredSize(new Dimension(480, 16 * (dataNumber + 2)));
				tablePane.setLayout(new BorderLayout());
				this.panel.add(tablePane);
				
				for (int outputIndex = index + 1, putIndex = 0; outputIndex <= index + dataNumber; outputIndex++, putIndex++) {
					outputData[putIndex] = data[outputIndex];
				}
				JLabel lb = new JLabel(data[index][0]);
				tablePane.add(lb, BorderLayout.NORTH);
				Table table = new Table(columns, outputData, false);
				tablePane.add(table.getTableHeader(), BorderLayout.CENTER);
				tablePane.add(table, BorderLayout.SOUTH);
				
				index += dataNumber;
				this.intCellNumber += dataNumber + 3;
			}
			
			// 調校 捲軸長度
			panel.setPreferredSize(new Dimension(460, (int)((intCellNumber / 20.0) * 320)));
			frame.setVisible(true);
		}

	}
	
}
