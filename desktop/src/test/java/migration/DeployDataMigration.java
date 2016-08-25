package migration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dragon3.common.constant.DeployType;
import dragon3.common.util.MoveUtils;
import dragon3.data.DeployData;
import mine.io.BeanIO;
import mine.io.JsonIO;
import net.arnx.jsonic.JSON;

/**
 *
 * @author rara
 */
public class DeployDataMigration {
    
    public DeployDataMigration() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void migrate_001() throws Exception {
        DeployData[] data = (DeployData[])BeanIO.read("dragon3/data/deploy/init.xml");
        
        String json = JSON.encode(data, true);
    	
    	FileUtils.write(new File("target/InitDeploy.json"), json, "UTF-8");
    
    }
    
    @Test
    public void migrate_002() throws Exception {
    	
    	for (String fileName : getFileNames()) {
    		DeployData[] dataList = JsonIO.INSTANCE.read("dragon3/data/deploy/deploy_" + fileName + ".json", DeployData[].class);
    		int[][] map = JsonIO.INSTANCE.read("dragon3/data/map/map_" + fileName + ".json", int[][].class);
        
    		for (DeployData data : dataList) {
    			switch (data.getDeployType()) {
    			case BOX_ITEM:
    				if (map[data.getX()][data.getY()] == MoveUtils.C_BLUE) {
    					data.setDeployType(DeployType.CLEAR_ITEM);
    				} 
    				break;
    			case ENEMY_ITEM:
    				if (data.getGoalX() == data.getX() && data.getGoalY() == data.getY()) {
    					data.setDeployType(DeployType.SECRET_ITEM);
    				}
    			default:
    			}
    		}
    		String json = JSON.encode(dataList, true);
    	
    		FileUtils.write(new File("target/deploy/deploy_" + fileName + ".json"), json, "UTF-8");
    	}
    }
    
    private List<String> getFileNames() {
        List<String> bodys = new ArrayList<>();
        bodys.add("camp");
        bodys.add("kakusei");
        for (int i = 1; i <= 30; i++) {
            bodys.add(String.format("E%02d", i));
        }
        
        return bodys;
    }
}
