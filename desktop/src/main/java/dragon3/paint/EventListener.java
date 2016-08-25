package dragon3.paint;

import dragon3.common.Body;

public interface EventListener {

    public void setSelectBody( Body b );
    public void setSelectPlace( int x, int y );
    public boolean isNextPoint( int x, int y );
    public void leftPressed();
    public void mouseMoved( int x, int y );
    
    public void accept();
    public void cancel();
}

