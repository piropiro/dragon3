package mine.paint;

public class PaintBox {
	private int max_x;
	private int min_x;
	private int max_y;
	private int min_y;
	
	public PaintBox(){
		max_x = Integer.MIN_VALUE;
		min_x = Integer.MAX_VALUE;
		max_y = Integer.MIN_VALUE;
		min_y = Integer.MAX_VALUE;
	}
	
	public PaintBox(int x, int y, int w, int h){
		max_x = x + w;
		min_x = x;
		max_y = y + h;
		min_y = y;
	}

	public void add(int x, int y){
		if ( x > max_x ) max_x = x;
		if ( x < min_x ) min_x = x;
		if ( y > max_y ) max_y = y;
		if ( y < min_y ) min_y = y;
	}
	
	public void add( int x, int y, int w, int h){
		if ( x + w > max_x ) max_x = x + w;
		if ( x < min_x ) min_x = x;
		if ( y + h > max_y ) max_y = y + h;
		if ( y < min_y ) min_y = y;
	}
	
	public int getX(){
		return min_x;
	}
	
	public int getY(){
		return min_y;
	}
	
	public int getW(){
		return max_x - min_x;
	}
	public int getH(){
		return max_y - min_y;
	}
}
