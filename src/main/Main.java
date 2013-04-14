package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import controller.Controller;

import view.MainWindow;

public class Main {
	public static void main(String[] args) {
		System.out.println(new JFrame().getContentPane().getLayout());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Controller controller = new Controller();
				MainWindow mainWindow = new MainWindow(controller);
				controller.setMainWindow(mainWindow);
			}
		});
	}
}
