import Other.IO;

class Model {
	
	private final static String configFilePath = ".\\config.txt";
	
	Model() {}
	
	void setYear(int semester) {
		IO.Output(configFilePath, "year: " + semester, "year", ": ");
		
		// 更改 Cache
		String strOutput = "";
		String databasePath = IO.InputValue(".\\config.txt", "databasePath", ": ");
		String[] fileList = IO.traverseDirectory(databasePath + "TestInfo\\");
		
		for (String file: fileList) {
			String[] InputData = IO.InputAll(databasePath + "TestInfo\\" + file);
			for (String Element: InputData) {
				String yearInfo = Element.split(",")[0];
				if (yearInfo.indexOf(semester + "") != -1) {
					System.out.println(yearInfo);
					System.out.println(file);
					strOutput += file.substring(0, file.indexOf(".")) + Element.substring(Element.indexOf(",")) + "\n";
					break;
				}
			}
		}
		
		IO.OutputAll(databasePath + "Cache_TestInfo.txt", strOutput);
	}
	
	void setDBPath(String path) {
		IO.Output(configFilePath, "databasePath: " + path, "databasePath", ": ");
	}
	
}