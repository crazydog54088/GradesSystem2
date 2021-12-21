package proc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import User.User;

public class showUserInfo {
	public static void main(String[] args) {
		new showUserInfo(1);
	}
	
	public showUserInfo(int id){
		new Controller(new Model(id), new View());
	}

	private class Controller {
		private Model model;
		private View view;
		
		Controller(Model m, View v){
			this.model = m;
			this.view = v;
			
			// init Controller
			view.setInfo(model.getUser());
		}
	}
	
	private class Model {
		
		private User user;
		
		Model(int id){
			user = new User(id);
		}
		
		User getUser() {
			return user;
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
		private JPanel paneYear;
		private JLabel lbYear;
		private JLabel lbInputYear;
		
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
			paneId.setBounds(81, 76, 100, 48);
			panel.add(paneId);
			paneId.setLayout(new BorderLayout(0, 0));
			
			lbId = new JLabel("學號    ");
			lbId.setFont(new Font("新細明體", Font.BOLD, 15));
			paneId.add(lbId, BorderLayout.WEST);
			
			lbInputId = new JLabel();
			lbInputId.setFont(new Font("新細明體", Font.PLAIN, 15));
			paneId.add(lbInputId, BorderLayout.CENTER);
			
			paneName = new JPanel();
			paneName.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			paneName.setBounds(203, 76, 123, 48);
			panel.add(paneName);
			paneName.setLayout(new BorderLayout(0, 0));
			
			lbName = new JLabel("姓名    ");
			lbName.setFont(new Font("新細明體", Font.BOLD, 15));
			paneName.add(lbName, BorderLayout.WEST);
			
			lbInputName = new JLabel();
			lbInputName.setFont(new Font("新細明體", Font.PLAIN, 15));
			paneName.add(lbInputName, BorderLayout.CENTER);
			
			paneYear = new JPanel();
			paneYear.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			paneYear.setBounds(155, 149, 105, 48);
			panel.add(paneYear);
			paneYear.setLayout(new BorderLayout(0, 0));
			
			lbYear = new JLabel("入學年    ");
			lbYear.setFont(new Font("新細明體", Font.BOLD, 15));
			paneYear.add(lbYear, BorderLayout.WEST);
			
			lbInputYear = new JLabel();
			lbInputYear.setFont(new Font("新細明體", Font.PLAIN, 15));
			paneYear.add(lbInputYear, BorderLayout.CENTER);

			frame.setBounds(100, 100, 500, 400);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
		
		void setInfo(User user){
			lbInputId.setText(user.getId() + "");
			lbInputName.setText(user.getName());
			if (!user.getIdentity().equals("Student")) {
				lbYear.setText("創建年分    ");
			}
			lbInputYear.setText(user.getEntranceYear() + "");
		}
		
	}
	
}
