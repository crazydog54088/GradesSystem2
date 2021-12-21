// 測試捲軸變數是否適當設置

package proc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Other.IO;
import Other.Lesson;
import User.User;
import User.Professor;
import User.Manager;

public class editList {

	public editList(int id, String openFrame) {
		// execute editScore
		new Controller(new Model(id), new View(openFrame));
	}
	
	public editList(String openFrame) {
		// execute showLessonInfo
		new Controller(new Model(), new View(openFrame));
	}
	
	
	private class Controller {
		private Model model;
		private View view;
		
		Controller(Model m, View v){
			this.model = m;
			this.view = v;
			
			switch (model.getIdentity()) {
				case "SuperUser":
					view.hideComboBox();
					view.showList(model.getTestList());
					break;
				
				case "Manager":
					view.cbSetYear.addActionListener(new ActionListener() {
				        @Override
				        public void actionPerformed(ActionEvent e) {
				        	int semester = (int)view.cbSetYear.getSelectedItem();
				        	view.showList(model.getTestList(semester), semester);
				        }
				    });
					break;
			}
		}
		
	}
	
	private class Model {
		private User user;
		private String identity;
		
		Model(int id){
			this.identity = new User(id).getIdentity();
			switch (identity) {
			case "SuperUser":
				this.user = new SuperUser(id);
				break;
				
			case "Manager":
				this.user = new Manager(id);
				break;
			}
		}
		
		Model(){
			this.identity = "Manager";
		}
		
		String getIdentity() {
			return this.identity;
		}
		
		String[] getLessonList(){
			return ((SuperUser) user).getTestList();
		}
		
		String[] getLessonList(int semester) {
			return Lesson.getTestListInSemester(semester);
		}
		
	}

	private class View {
		private JFrame frame;
		private Container container;
		private JPanel paneSetYear;
		private JLabel lbSetYear;
		JComboBox<Integer> cbSetYear;
		private JScrollPane scrollPane;
		private JPanel panel;
		
		private String openFrame;// (dynamic created) button add the function opening frame
		
		View(String openFrame){
			this.openFrame = openFrame;
			
			frame = new JFrame();
			frame.setTitle("成績系統");
			container = frame.getContentPane();
			container.setBackground(SystemColor.activeCaptionBorder);
			frame.setBounds(100, 100, 500, 400);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			container.setLayout(new BorderLayout(0, 0));
			
			paneSetYear = new JPanel();
			paneSetYear.setLayout(new BorderLayout(0, 0));
			container.add(paneSetYear, BorderLayout.NORTH);
			lbSetYear = new JLabel("   請選擇年分:");
			lbSetYear.setPreferredSize(new Dimension(80, 0));
			paneSetYear.add(lbSetYear, BorderLayout.WEST);
			cbSetYear = new JComboBox<Integer>();
			addComboBoxItem();
			paneSetYear.add(cbSetYear, BorderLayout.CENTER);
			
			// 製作 FlowLayout + 捲軸
			scrollPane = new JScrollPane();
			container.add(scrollPane, BorderLayout.CENTER);
			clearList();
			// 使用 BorderLayout 設置 scrollPane 的大小
			//scrollPane.setPreferredSize(new Dimension(460, 400));
			
			frame.setVisible(true);
		}
		
		void hideComboBox() {
			paneSetYear.setVisible(false);
		}
		
		void addComboBoxItem() {
			int sinceYear = Integer.parseInt( IO.InputValue(".\\config.txt", "since", ": ") );
			int systemYear = Integer.parseInt( IO.InputValue(".\\config.txt", "year", ": ") );
			
			for (int i = sinceYear; i <= systemYear; i++) {
				cbSetYear.addItem(i);
			}
		}
		
		void showList(String[] data) {
			showList(data, -1);
		}
		
		void showList(String[] data, int semester) {
			clearList();
			
			if (data == null) {
				JLabel lb = new JLabel("暫無課程清單");
				lb.setFont(new Font("Dialog", 1, 20));
				lb.setForeground(Color.WHITE);
				panel.add(lb);
				frame.setVisible(true);
				return;
			}
			
			// 調校 捲軸長度
			panel.setPreferredSize(new Dimension(460, (data.length * 50)));
						
			for (int index = 0; index < data.length; index++) {
				int LessonId = Integer.parseInt(data[index]);
				if (semester == -1) addRow(new Test(TestId));
				else addRow(new Test(TestId, semester));
			}
			
			frame.setVisible(true);
		}
		
		private void addRow(Test Test) {
			JPanel rowPanel = new JPanel();
			rowPanel.setPreferredSize(new Dimension(480, 40));
			rowPanel.setLayout(new BorderLayout(20, 0));
			
			JLabel lb01 = new JLabel(Test.id + "");
			rowPanel.add(lb01, BorderLayout.WEST);
			JLabel lb02 = new JLabel(Test.name);
			rowPanel.add(lb02, BorderLayout.CENTER);
			
			JButton btn = new JButton();
			if (this.openFrame.equals("editScore")) {
				btn.setText("批改成績");
				btn.addActionListener(e -> {
					// 開啟編輯畫面
					new editScore(Test.id, Test.semester);
				});
			}
			else if (this.openFrame.equals("showTestInfo")) {
				btn.setText("測驗資訊");
				btn.addActionListener(e -> {
					// 開啟編輯畫面
					new showTestInfo(Test.id, Test.semester);
				});
			}
			rowPanel.add(btn, BorderLayout.EAST);
			btn.setSize(100, 100);
			
			
			panel.add(rowPanel);
		}
		
		void clearList() {
			panel = new JPanel();
			panel.setBackground(SystemColor.activeCaptionBorder);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 12));
			scrollPane.setViewportView(panel);
			panel.setPreferredSize(new Dimension(0, 0));
		}
		
	}
	
}
