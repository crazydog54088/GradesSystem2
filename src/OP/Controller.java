package OP;

class Controller {
	
	private Model model;
	private View view;
	
	Controller(Model m, View v){
		this.model = m;
		this.view = v;
		
		init();
	}
	
	private void init() {
		view.menuSimulation.addActionListener(e -> {
			view.showCard("Simulation");
		});
		view.menuAutomation.addActionListener(e -> {
			view.showCard("Automation");
		});
		view.menuLogout.addActionListener(e -> {
			view.logout();
		});
		view.menuExit.addActionListener(e -> {
			view.exit();
		});
		
		view.btnSetYear.addActionListener(e -> {
			int semester = Integer.parseInt(view.taSetYear.getText());
			model.setYear(semester);
			view.showSettingSuccess("年份設定為: " + semester);
		});
		view.btnSetDBPath.addActionListener(e -> {
			String strDBPath = view.taSetDBPath.getText();
			model.setDBPath(strDBPath);
			view.showSettingSuccess("資料庫路徑設定為: " + strDBPath);
		});
	}
	
}
