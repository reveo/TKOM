package model;

import view.MainWindow;

public class ErrorHandler {
	private static volatile ErrorHandler instance;
	private MainWindow mainWindow;

	private ErrorHandler() {

	}

	public static ErrorHandler getInstance() {
		if (instance == null) {
			synchronized (ErrorHandler.class) {
				if (instance == null) {
					instance = new ErrorHandler();
				}
			}
		}
		return instance;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void setError(String text) {
		mainWindow.setErrorText(text + "\n");
	}

	public void clearError() {
		mainWindow.clearErrorText();
	}
}
