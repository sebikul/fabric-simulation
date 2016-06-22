package fabric;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DlmWriter {
	private BufferedWriter writer;

	public DlmWriter(String fileName) throws IOException {
		writer = new BufferedWriter(new FileWriter(fileName));
	}

	public void write(double[][] matrix, int rows, int cols) throws IOException {
		

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				double d=matrix[i][j];
				//String str=new DecimalFormat("#.0###").format(d);
				writer.append(matrix[i][j]+ "");
				//writer.write(str);
				if(j+1 < cols) {
					writer.append(",");
				}
				
			}
			writer.write("\n");
		}
		//writer.flush();
	}

	public void closeWriter() throws IOException {
		writer.close();
	}

}
