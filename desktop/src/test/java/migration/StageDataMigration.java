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

import dragon3.data.StageData;
import mine.io.BeanIO;
import net.arnx.jsonic.JSON;

/**
 *
 * @author rara
 */
public class StageDataMigration {
    
    public StageDataMigration() {
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

    //@Test
    public void migrate_001() throws Exception {
        StageData[] data = (StageData[])BeanIO.read("dragon3/data/stage/StageData.xml");
        
        String json = JSON.encode(data, true);
    	
    	FileUtils.write(new File("target/StageData.json"), json, "UTF-8");
    
    }
    
    //@Test
    public void migrate_002() throws Exception {
    	List<StageData> list = new ArrayList<>();
    	
    	for (int i = 1; i <= 30; i++) {
    		StageData stage = new StageData();
    		stage.setId(String.format("D%02d", i));
    		switch (i) {
    		case 1:
    			stage.setName("Tutorial");
    			break;
    		case 28:
    			stage.setName("Extra Stage 1");
    			break;
    		case 29:
    			stage.setName("Extra Stage 2");
    			break;
    		case 30:
    			stage.setName("Extra Stage 3");
    			break;
    		default:
    			stage.setName("Starge " + (char)('A' - 2 + i));
    		}
    		
    		stage.setLevel(1 + i / 3);
    		list.add(stage);
    	}
    	String json = JSON.encode(list, true);
    	FileUtils.write(new File("target/StageData.json"), json, "UTF-8");
    }
}
