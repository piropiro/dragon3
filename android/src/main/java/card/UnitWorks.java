package card;


import mine.paint.PaintBox;
import card.body.Card;

public interface UnitWorks {

	public void repaint();
	public void repaint(PaintBox box);
	public void addCard(Card card);
	public void removeCard(Card card);
	public void sleep(long msec);
	public int nextInt(int max);
}
