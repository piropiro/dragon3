package mine.util;


public class Bounder {

	private double y[];
	
    public Bounder(double y0, double g, double e, int max){

    	y = new double[max];
    	y[0] = y0;
 
    	calc(g, e);
    }
	
	public void calc(double g, double e) {

		double bound_time =  - Math.sqrt(2*y[0]/g);
		double bound_v = - g * bound_time;

		for (int i=1; i<y.length; i++) {
			double t = i - bound_time;
			y[i] = (bound_v * t) - (g * t * t) / 2;
			if (y[i] < 0) {
				y[i] = 0;
				bound_time += 2 * bound_v / g;
				bound_v *= e;
				
				if ( bound_v > 0.001) {
					i--;
					continue;
				}
			}
		}
	}
	
	public int getY(int s){
		if (s >= y.length) {
			return 0;
		} else {
			return (int)y[s];
		}
	}
	
}
