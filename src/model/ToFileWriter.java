package model;

import java.io.File;
import view.MainWindow;

public class ToFileWriter {

	public ToFileWriter(File file, MainWindow mainWindow) {
		mainWindow.closeAllBrackets();
		mainWindow.writeToFile(file);
	}
}
