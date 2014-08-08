package hu.deadcode.renamer;

public class App {
	private static Config config = new Config();
	private static Logic logic = new Logic(config);

	public static void main(String[] args) {
		String path = UI.GetPath();
		UI.PrintOut("Finding image files in : " + path);
		int count = logic.RenameFiles();
		UI.PrintOut("The program rename " + Integer.toString(count) + " files");
	}
}
