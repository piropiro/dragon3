/*
 * 作成日: 2004/03/07
 */
package mine.edit;

import javax.swing.JComponent;


/**
 * @author k-saito
 */
public interface EditListener<B> {
	public B createInstance();
	public B[] createArray();
	public void setData(B bean);
	public void getData(B bean);
	public JComponent getComponent();
}
