package dragon3.edit.deploy;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dragon3.Statics;
import dragon3.data.BodyData;
import dragon3.data.DeployData;
import dragon3.edit.DaggerEditorComponent;
import dragon3.edit.EditorComponent;
import dragon3.edit.deploy.paint.BasicPaint;
import dragon3.edit.deploy.paint.GoalPaint;
import dragon3.edit.deploy.paint.SortPaint;
import dragon3.image.ImageManager;
import mine.MineException;
import mine.MineUtils;
import mine.awt.MineAwtUtils;
import mine.edit.EditList;
import mine.edit.EditMenuBar;
import mine.edit.EditToolBar;
import mine.event.CommandListener;
import mine.file.FileCommand;
import mine.file.FileManager;
import mine.file.FileWorks;
import mine.io.JsonIO;
import mine.paint.MineImageLoader;
import mine.paint.UnitMap;

@SuppressWarnings("serial")
public class DeployEditor extends JFrame implements MainWorks<DeployData>, CommandListener, FileWorks, ListSelectionListener, KeyListener {

	private static final String BASIC_HELP = "基本操作　左クリック：キャラを配置する。　右クリック：キャラを選択する。";
	private static final String SORT_HELP = "並べ替え　左クリック：選択した順にキャラを並べ替える。　右クリック：キャンセル";
	private static final String GOAL_HELP = "目標設定　左クリック：目標を設定する。　右クリック：キャンセル";

	private String title = "DeployEditor";

	@Inject Statics statics;
	private FileManager fileManager;
	@Inject ImageManager imageManager;

	private EditList<DeployData> editList;
	private EditMenuBar menuBar;

	private MapPanel mapPanel; // マップパネル
	@Inject StatusPanel statusPanel; // ステータスパネル
	private UnitMap map; // ユニットマップ
	private JLabel help; // ヘルプ

	/*** コンストラクタ *****************************************************/

	@Inject
	public DeployEditor() {
		super();
	}
	
	public void setup() {

		initMap();

		mapPanel = new MapPanel(map);
		//statusPanel = new StatusPanel();

		help = new JLabel("help");
		help.setFont(MineAwtUtils.getFont(12));

		fileManager = new FileManager("deploys.txt", 10, "data.json", statusPanel, this);
		menuBar = new EditMenuBar(this);
		editList = new EditList<>(statusPanel);
		editList.addListSelectionListener(this);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.add(statusPanel, "North");
		eastPanel.add(new JScrollPane(editList), "Center");

		EditToolBar toolBar = new EditToolBar(this);
		toolBar.addSeparator();
		toolBar.add(toolBar.newButton("Sort", "sort"));
		toolBar.add(toolBar.newButton("Goal", "goal"));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar, "North");
		getContentPane().add(eastPanel, "East");
		getContentPane().add(mapPanel, "Center");
		getContentPane().add(help, "South");
		setJMenuBar(menuBar);

		setBasicPaint();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		MineAwtUtils.setCenter(this);

		try {
			fileManager.load();
		} catch (MineException e) {
			fileManager.create();
		}
		repaintMap();
		setVisible(true);
		
		mapPanel.addKeyListener(this);
	}
	
	public static void main(String[] args) throws Exception {
		EditorComponent og = DaggerEditorComponent.builder().build();
		DeployEditor deployEditor = og.getDeployEditor();
		deployEditor.setup();
	}

	/*** ユニットマップ設定 *************************************************/

	private void initMap() {
		MineImageLoader mil = imageManager.getImageLoader();
		
		map = new UnitMap(4, 20, 15, mil);
		map.setTile(Page.BACK, imageManager.getStageBack(), -1);
		map.setTile(Page.OBJ, imageManager.getStageObj(), -1);
		map.setTile(Page.CHARA, imageManager.getBodyImageList().getImageList(), -1);
		map.setTile(Page.WAKU, imageManager.getWaku()[2], 0);
		map.clear(Page.CHARA, -1);
		map.setVisible(Page.OBJ, true);
		map.setVisible(Page.CHARA, true);
		map.setVisible(Page.WAKU, true);
	}

	/*** 基本関数 *******************************************************/

	private DeployData search(int x, int y) {
		for (DeployData deploy : editList.getList()) {
			if (deploy.getX() != x)
				continue;
			if (deploy.getY() != y)
				continue;
			return deploy;
		}
		return null;
	}

	private void repaintMap() {
		map.clear(Page.CHARA, -1);
		map.clear(Page.WAKU, 0);

		for (DeployData deploy : editList.getList()) {

			BodyData body = statics.getBodyData(deploy.getBodyId());
			map.setData(Page.CHARA, deploy.getX(), deploy.getY(), imageManager.getBodyImageList().getNum(body.getImage()));
		}

		try {
			DeployData deploy = (DeployData) editList.getSelectedData();
			map.setData(Page.WAKU, deploy.getX(), deploy.getY(), 1);
			if (deploy.getGoalX() != 0 || deploy.getGoalY() != 0) {
				map.setData(Page.WAKU, deploy.getGoalX(), deploy.getGoalY(), 4);
			}
		} catch (MineException e) {
		}
		mapPanel.repaint();
	}

	/*** ImoWorks - BasicPaint ***************************************/

	public void addUnit(int x, int y) {
		DeployData deploy = new DeployData();
		statusPanel.getData(deploy);
		deploy.setX(x);
		deploy.setY(y);
		editList.addData(deploy);

		BodyData body = statics.getBodyData(deploy.getBodyId());
		map.setData(Page.CHARA, deploy.getX(), deploy.getY(), imageManager.getBodyImageList().getNum(body.getImage()));
		repaintMap();
	}

	public void copyUnit(int x, int y) {
		DeployData deploy = search(x, y);
		if (deploy != null) {
			editList.selectData(deploy);
		}
		repaintMap();
		mapPanel.requestFocus();
	}

	public void removeUnit(int x, int y) {
		DeployData deploy = search(x, y);
		if (deploy != null) {
			editList.removeData(deploy);
			map.setData(Page.CHARA, x, y, -1);
			repaintMap();
		}
	}

	/*** ImoWorks - SortPaint ***************************************/

	@Override
	public void moveUnit(int x, int y, List<DeployData> dstData) {
		DeployData deploy = search(x, y);
		moveUnit(deploy, dstData);
	}

	@Override
	public void moveNextUnit(List<DeployData> dstData) {
		DeployData deploy = (DeployData) editList.getDataAt(0);
		moveUnit(deploy, dstData);
	}

	@Override
	public void undoUnit(List<DeployData> dstData) {
		if (dstData.size() != 0) {
			DeployData deploy = (DeployData) dstData.get(dstData.size() - 1);
			dstData.remove(deploy);
			editList.addDataAt(0, deploy);
			BodyData body = statics.getBodyData(deploy.getBodyId());
			map.setData(Page.CHARA, deploy.getX(), deploy.getY(), imageManager.getBodyImageList().getNum(body.getImage()));
			repaintMap();
		}
	}

	private void sortEnd(List<DeployData> dstData) {
		editList.setList(dstData);
		setBasicPaint();
		repaintMap();
	}

	private void moveUnit(DeployData deploy, List<DeployData> dstData) {
		editList.removeData(deploy);
		dstData.add(deploy);
		map.setData(Page.CHARA, deploy.getX(), deploy.getY(), -1);
		repaintMap();
		if (editList.getList().size() == 0) {
			sortEnd(dstData);
		}
	}

	/*** ImoWorks - GoalPaint ***************************************/

	@Override
	public void setGoal(int x, int y) {
		try {
			DeployData deploy = (DeployData) editList.getSelectedData();
			deploy.setGoalX(x);
			deploy.setGoalY(y);
			statusPanel.setData(deploy);
			setBasicPaint();
		} catch (MineException e) {
			e.printStackTrace();
		}
		repaintMap();
	}

	@Override
	public void setBasicPaint() {
		mapPanel.setMouseListener(new BasicPaint(this, map));
		help.setText(BASIC_HELP);
	}

	/*** FileManager *************************************************/

	@Override
	public void create(String file) {
		editList.initData();
		String name = new File(file).getName();
		String deployFile = name.replaceAll("map_", "deploy_");
		setTitle(deployFile + " - " + title);
	}

	@Override
	public void load(String file) throws MineException {
		String deployFile = file.replaceAll("\\\\", "/").replaceAll(Statics.MAP_DIR, Statics.DEPLOY_DIR).replaceAll("map_", "deploy_");
		String mapFile = file.replaceAll("\\\\", "/").replaceAll(Statics.DEPLOY_DIR, Statics.MAP_DIR).replaceAll("deploy_", "map_");

		editList.loadData(deployFile);

		if (new File(mapFile).exists()) {
			map.setPage(Page.OBJ, JsonIO.INSTANCE.read(mapFile, int[][].class));
		} else {
			map.clear(Page.OBJ, 0);
			System.out.println("MapFile is not found. [" + new File(mapFile).getAbsolutePath() + "]");
		}

		setTitle(new File(deployFile).getName() + " - " + title);
		menuBar.setFileList(fileManager.getNameList());
	}


	@Override
	public void save(String file) throws MineException {
		String deployFile = file.replace('\\', '/').replaceAll(Statics.MAP_DIR, Statics.DEPLOY_DIR).replaceAll(".txt", ".json");

		editList.saveData(deployFile);

		setTitle(new File(deployFile).getName() + " - " + title);
		menuBar.setFileList(fileManager.getNameList());
	}

	/*** アクション ***********************************/

	@Override
	public void doCommand(String command) {
		try {
			if (command.equals(FileCommand.NEW)) {
				fileManager.create();
				repaintMap();
			}
			if (command.equals(FileCommand.OPEN)) {
				fileManager.open();
				repaintMap();
			}
			for (int i = 0; i < 10; i++) {
				if (command.equals(FileCommand.OPEN_AT + i)) {
					fileManager.openAt(i);
					repaintMap();
				}
			}
			if (command.equals(FileCommand.SAVE)) {
				fileManager.save();
			}
			if (command.equals(FileCommand.SAVE_AS)) {
				fileManager.saveAs();
			}
		} catch (MineException e) {
			e.printStackTrace();
		}

		if (command.equals("create")) {
			editList.createData();
			repaintMap();
		}
		if (command.equals("delete")) {
			editList.removeSelectedData();
			repaintMap();
		}
		if (command.equals("accept")) {
			editList.acceptData();
			repaintMap();
		}
		if (command.equals("up")) {
			editList.up();
		}
		if (command.equals("down")) {
			editList.down();
		}
		if (command.equals("goal")) {
			if (editList.getSelectedIndex() != -1) {
				mapPanel.setMouseListener(new GoalPaint(this));
				help.setText(GOAL_HELP);
			}
		}
		if (command.equals("sort")) {
			if (editList.getList().size() != 0) {
				mapPanel.setMouseListener(new SortPaint(this, map));
				help.setText(SORT_HELP);
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		repaintMap();
	}
	
	private void moveUnit(int mx, int my) {
		DeployData deploy;
		try {
			deploy = (DeployData) editList.getSelectedData();
			deploy.setX(MineUtils.INSTANCE.mid(0, deploy.getX() + mx, map.getMapWidth() - 1));
			deploy.setY(MineUtils.INSTANCE.mid(0, deploy.getY() + my, map.getMapHeight() - 1));
			if (deploy.getGoalX() != 0 || deploy.getGoalY() != 0) {
				deploy.setGoalX(MineUtils.INSTANCE.mid(0, deploy.getGoalX() + mx, map.getMapWidth() - 1));
				deploy.setGoalY(MineUtils.INSTANCE.mid(0, deploy.getGoalY() + my, map.getMapHeight() - 1));
			}
			statusPanel.setData(deploy);
			repaintMap();
		} catch (MineException e1) {
			e1.printStackTrace();
			return;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			moveUnit(0, -1);
			break;
		case KeyEvent.VK_DOWN:
			moveUnit(0, 1);
			break;
		case KeyEvent.VK_LEFT:
			moveUnit(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
			moveUnit(1, 0);
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {

	}
}
