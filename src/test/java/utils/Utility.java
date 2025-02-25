package utils;

import java.io.PrintStream;

public class Utility {

	static PrintStream originalOut = System.out;
	static PrintStream originalErr = System.err;

	static PrintStream dummyStream = new PrintStream(new java.io.OutputStream() {
		public void write(int b) {
		}
	});

	public static void enablePrintingInConsole() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	public static void disablePrintingInConsole() {

		System.setOut(dummyStream);
		System.setErr(dummyStream);
	}

	public static String getCurrentMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
}
