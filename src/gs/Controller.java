package gs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import GS.proc.OwnGrade;
import GS.proc.accountManagementFrame;
import GS.proc.addtestFrame;
import GS.proc.changePassword;
import GS.proc.editList;
import GS.proc.testManagementFrame;
import GS.proc.searchScoreFrame;
import GS.proc.showUserInfo;
import GS.proc.testgroupManagementFrame;

public class Controller {
	
	private Model model;
	private View view;
	
	Controller(Model model, View view) {
		this.model = model;
		this.view = view;

		// Initialize Controller
		initLogin();
		initHome();
	}
	
	private void initLogin() {
		view.getBtnLogin().addActionListener(e -> login());
		view.getTaAccount().addKeyListener(new EnterListener());
		view.getTaPassword().addKeyListener(new EnterListener());
	}
	
	private void initHome() {
		view.menuItemOwnInfo.addActionListener(e -> {
			new showUserInfo(model.getId());
		});
		view.menuItemChangePassword.addActionListener(e -> {
			new changePassward(model.getId());
		});
		view.menuItemLogout.addActionListener(e -> {
			view.relogin();
			initLogin();
			initHome();
		});
		view.menuItemExit.addActionListener(e -> {
			view.closeHomeFrame();
		});
		view.menuItemOwnGrade.addActionListener(e -> {
			new OwnGrade(model.getId());
		});
		view.menuItemSetScore.addActionListener(e -> {
			new editList(model.getId(), "editScore");
		});
		view.menuItemSearchLesson.addActionListener(e -> {
			new editList("showtestInfo");
		});
		view.menuItemManageAccount.addActionListener(e -> {
			new accountManagementFrame();
		});
		view.menuItemManageLesson.addActionListener(e -> {
			new testManagementFrame();
		});
		view.menuItemSetLessonForStudent.addActionListener(e -> {
			new addtestFrame();
		});
		view.menuItemPrintTranscript.addActionListener(e -> {
			new searchScoreFrame();
		});
		view.menuItemManageLesson.addActionListener(e -> {
			new testManagementFrame();
		});
	}
	
	class EnterListener extends KeyAdapter{
		// implements KeyListener 比 extends KeyAdapter 完整

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10) {
				login();
			}
		}
		
	}
	
	private void login() {
		String loginState = model.login(view.getTaAccount().getText() , view.getTaPassword().getText());
		if (loginState.equals("Success")) {
			model.setUser(Integer.parseInt(view.getTaAccount().getText()));
			view.loginSuccess(model.getIdentity());
		}
		else {
			view.showLoginFailure(loginState);
			view.clearTaPassword();
		}
	}
	
}
