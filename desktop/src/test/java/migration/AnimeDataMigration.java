package migration;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dragon3.data.AnimeData;
import mine.io.BeanIO;
import net.arnx.jsonic.JSON;

/**
 *
 * @author rara
 */
public class AnimeDataMigration {
    
    public AnimeDataMigration() {
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
        AnimeData[] data = (AnimeData[])BeanIO.read("dragon3/data/anime/SystemAnime.xml");
        
        String json = JSON.encode(data, true);
    	
    	FileUtils.write(new File("target/SystemAnime.json"), json, "UTF-8");
    
    }
}
