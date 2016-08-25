package mine.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class MatrixReader extends BufferedReader {
	
	public MatrixReader( InputStream in ){
		super( new InputStreamReader(in) );
	}

	public MatrixReader( Reader in ){
		super(in);
	}

	public int[][] readMatrix() throws IOException {
		
		ArrayList<int[]> matrix = new ArrayList<int[]>();
		
		String line = null;
		while ( (line = super.readLine()) != null ) {
			StringTokenizer st = new StringTokenizer(line, ", ");
			
			int row[] = new int[st.countTokens()];
			
			for (int i=0; st.hasMoreTokens(); i++) {
				row[i] = Integer.parseInt(st.nextToken());
			}
			matrix.add(row);
		}
		
		return matrix.toArray( new int[0][] );		
	}
}