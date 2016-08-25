package mine.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class MatrixWriter extends BufferedWriter {

	public MatrixWriter( OutputStream out ){
		super( new OutputStreamWriter(out) );
	}
	
	public MatrixWriter( Writer out ){
		super(out);
	}

	public void writeMatrix( int[][] matrix ) throws IOException {
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				super.write(matrix[i][j] + " ");
			}
			super.newLine();
		}
	}
	

}