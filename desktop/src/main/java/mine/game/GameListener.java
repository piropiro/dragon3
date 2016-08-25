/*
 * Created on 2004/10/10
 */
package mine.game;

import mine.paint.MineGraphics;

/**
 * @author saito
 */
public interface GameListener {
	/**
	 * タイトル画面を描画する。<p>
	 * 
	 * @param g
	 */
	public void paintStart(MineGraphics g);
	
	/**
	 * ゲームオーバー画面を描画する。<p>
	 * 
	 * @param g
	 */
	public void paintEnd(MineGraphics g);
	
	/**
	 * プレイ画面を描画する。<p>
	 * 
	 * @param g
	 */
	public void paintGame(MineGraphics g);

	/**
	 * Zキーが押された場合の処理<p>
	 *
	 */
	public void z_key();

	/**
	 * Xキーが押された場合の処理<p>
	 *
	 */
	public void x_key();

	/**
	 * Cキーが押された場合の処理<p>
	 *
	 */
	public void c_key();

	/**
	 * データを初期化する。<p>
	 *
	 */
	public void init();
}
