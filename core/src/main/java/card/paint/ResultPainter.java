package card.paint;

import javax.inject.Inject;
import javax.inject.Singleton;

import mine.paint.MineGraphics;


@Singleton
public class ResultPainter {

	public int result;
	
	public static final int NONE = 0;
	public static final int WIN = 1;
	public static final int LOSE = 2;
	public static final int DRAW = 3;
	
	@Inject
	public ResultPainter(){
		result = NONE;
	}
	
	public void setResult(int result){
		this.result = result;
	}
	
	public void paint(MineGraphics g){
		switch (result) {
			case WIN:
				paintText("Win", g);
				break;
			case LOSE:
				paintText("Lose", g);
				break;
			case DRAW:
				paintText("Draw", g);
				break;
		}
	}
	
	private void paintText(String text, MineGraphics g){
		g.setAntialias(true);
		g.setFont("serif", 32);
		g.drawString(text, 0, 32*6, 32*11);
	}
}
