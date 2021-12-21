package proc;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Other.Encryptor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import User.User;

public class changePassword {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnNewButton;

	public changePassward(int id) {
		User uu = new User(id);
		String originalPassword = uu.getPassword();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("\u8ACB\u8F38\u5165\u820A\u5BC6\u78BC");
		lblNewLabel.setBounds(44, 33, 112, 19);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(200, 30, 188, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u8ACB\u8F38\u5165\u65B0\u5BC6\u78BC");
		lblNewLabel_1.setBounds(44, 88, 112, 19);
		frame.getContentPane().add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setBounds(200, 85, 188, 25);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("\u8ACB\u518D\u6B21\u8F38\u5165\u65B0\u5BC6\u78BC");
		lblNewLabel_2.setBounds(44, 146, 127, 19);
		frame.getContentPane().add(lblNewLabel_2);

		textField_2 = new JTextField();
		textField_2.setBounds(200, 140, 188, 25);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		btnNewButton = new JButton("\u78BA\u8A8D");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				String text01 = textField_1.getText();
				String text02 = textField_2.getText();
				if (text01.equals("") && text02.equals("")) {
					showDialog("新密碼不可為空");
				}
				else if (!text01.equals(text02)) {
					showDialog("新密碼不相同");
				}
				else if (text.equals(text01)) {
					showDialog("新密碼與原密碼相符");
				}
				else if (!originalPassword.equals(new Encryptor(text).getResult())) {
					showDialog("密碼輸入錯誤");
				}
				else {
					String newPassword = textField_1.getText();
					uu.changePassword(newPassword);
					JOptionPane.showMessageDialog(frame, "更改成功", "更改密碼", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}
			}
		});
		btnNewButton.setBounds(166, 195, 99, 27);
		frame.getContentPane().add(btnNewButton);

		frame.setVisible(true);
	}
	
	private void showDialog(String Message) {
		JOptionPane.showMessageDialog(this.frame, Message, "登入失敗", JOptionPane.ERROR_MESSAGE);
	}

}
