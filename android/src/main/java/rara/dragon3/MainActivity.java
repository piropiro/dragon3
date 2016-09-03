package rara.dragon3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import dragon3.controller.CommandListener;
import dragon3.controller.DragonController;
import dragon3.view.FrameWorks;
import dragon3.view.MenuSet;
import mine.android.MineCanvasAND;
import mine.event.MineCanvas;
import mine.event.MouseAllListener;

public class MainActivity extends AppCompatActivity implements FrameWorks {

    private MineCanvasAND c;

    private Toolbar toolbar;

    private CommandListener commandListener;


    @Inject
    MineCanvas mc;

    @Inject
    DragonController dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplicationComponent().inject(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                c.getMouseAllListener().cancel();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                c.getMouseAllListener().accept();
            }
        });

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });


        c = (MineCanvasAND) findViewById(R.id.dragon_view);
        c.setBufferSize(640, 480);
        c.setPaintListener(mc);


        this.setCommandListener(dc);
        dc.setup(this);
        dc.title();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (mc.isUpdated()) {
                        c.repaint();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_stage) {
            commandListener.executeMenuCommand("stage");
        }
        if (id == R.id.action_start) {
            commandListener.executeMenuCommand("start");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setMenu(MenuSet n) {

    }

    @Override
    public void setMouseListener(MouseAllListener mal) {
        c.setMouseAllListener(mal);
        Log.d("SetMouseListener", "MouseListener:" + mal.getClass());
    }


    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    private AppComponent getApplicationComponent() {
        return ((AppApplication) getApplication()).getApplicationComponent();
    }

}

