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

import card.CardCanvas;
import dragon3.controller.CommandListener;
import dragon3.controller.DragonController;
import dragon3.panel.DataPanel;
import dragon3.panel.HPanel;
import dragon3.panel.HelpPanel;
import dragon3.panel.LargePanel;
import dragon3.panel.MessagePanel;
import dragon3.panel.SmallPanel;
import dragon3.view.FrameWorks;
import lombok.Getter;
import lombok.Setter;
import mine.android.ImageLoaderAND;
import mine.android.MineCanvasAND;
import mine.android.SleepManagerAND;
import mine.event.MouseAllListener;
import mine.event.PaintComponent;
import mine.event.SleepManager;
import mine.io.FileIO;
import mine.paint.MineImageLoader;

public class MainActivity extends AppCompatActivity implements FrameWorks {

    private MineCanvasAND mc;

    private Toolbar toolbar;

    @Setter
    private CommandListener commandListener;



    @Getter private PaintComponent mapPanel;
    @Getter private PaintComponent animePanel;
    @Getter private PaintComponent hPanel1;
    @Getter private PaintComponent hPanel2;
    @Getter private PaintComponent helpPanel;
    @Getter private PaintComponent smallPanel;
    @Getter private PaintComponent largePanel;
    @Getter private PaintComponent cardPanel;
    @Getter private PaintComponent dataPanel1;
    @Getter private PaintComponent dataPanel2;
    @Getter private PaintComponent messagePanel;
    @Getter private PaintComponent stageSelectPanel;

    @Getter private MineImageLoader imageLoader;
    @Getter private SleepManager sleepManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                mc.getMouseAllListener().cancel();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                mc.getMouseAllListener().accept();
            }
        });

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        FileIO.context = getApplicationContext();
        imageLoader =  new ImageLoaderAND(getApplicationContext());
        sleepManager = new SleepManagerAND();

        mc = (MineCanvasAND) findViewById(R.id.dragon_view);
        mc.setBufferSize(640, 480);

        mapPanel = mc.newLayer(0, 0, 640, 480);
        stageSelectPanel = mc.newLayer(0, 0, 640, 480);
        animePanel = mc.newLayer(0, 0, 640, 480);

        hPanel1 = mc.newLayer(0, 0, HPanel.WIDTH, HPanel.HEIGHT);
        hPanel2 = mc.newLayer(0, 0, HPanel.WIDTH, HPanel.HEIGHT);
        helpPanel = mc.newLayer(0, 0, HelpPanel.WIDTH, HelpPanel.HEIGHT);
        smallPanel = mc.newLayer(0, 0, SmallPanel.WIDTH, SmallPanel.HEIGHT);
        largePanel = mc.newLayer(0, 0, LargePanel.WIDTH, LargePanel.HEIGHT);
		cardPanel = mc.newLayer(0, 0, CardCanvas.WIDTH, CardCanvas.HEIGHT);
        dataPanel1 = mc.newLayer(0, 0, DataPanel.WIDTH, DataPanel.HEIGHT);
        dataPanel2 = mc.newLayer(0, 0, DataPanel.WIDTH, DataPanel.HEIGHT);
        messagePanel = mc.newLayer(0, 0, MessagePanel.WIDTH, MessagePanel.HEIGHT);

        mapPanel.setVisible(true);

        DragonController vp = new DragonController(this);
        this.setCommandListener(vp);
        vp.title();
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
    public void setMenu(int n) {

    }

    @Override
    public void setMouseListener(MouseAllListener mal) {
        mc.setMouseAllListener(mal);
        Log.d("SetMouseListener",  "MouseListener:" + mal.getClass());
    }
}
