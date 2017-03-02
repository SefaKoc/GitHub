
import java.io.FileWriter;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class CsvFileWriter {

	// Delimiter used in CSV file

	private static final String COMMA_DELIMITER = ",";

	private static final String NEW_LINE_SEPARATOR = "\n";

	// CSV file header

	private static final String FILE_HEADER = "datum,value";

	public static void writeCsvFile(String fileName, double[] werte) {

		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		FileWriter fileWriter = null;

		try {

			fileWriter = new FileWriter(fileName);

			// Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());

			fileWriter.append(NEW_LINE_SEPARATOR);

			for (int i = 0; i < 12; i++) {
				fileWriter.append(months[i]);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(werte[i]));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}

	}

}