package com.toy.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class TestReadLine {

	public static void main(String[] args) {

		File test = new File("a.txt");
		LineNumberReader rf = null;
		try {
			rf = new LineNumberReader(new FileReader(test));
			rf.skip(test.length());
			System.out.println(rf.getLineNumber());
			rf.close();
		} catch (IOException e) {
			if (rf != null) {
				try {
					rf.close();
				} catch (IOException ee) {
				}
			}
		}

	}

}
