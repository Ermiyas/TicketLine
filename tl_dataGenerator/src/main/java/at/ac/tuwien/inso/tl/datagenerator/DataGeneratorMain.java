package at.ac.tuwien.inso.tl.datagenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/*
import at.ac.tuwien.inso.tl.datagenerator.generator.DataGenerator;
import at.ac.tuwien.inso.tl.datagenerator.generator.EmployeeGenerator;
import at.ac.tuwien.inso.tl.datagenerator.generator.LocationGenerator;
import at.ac.tuwien.inso.tl.datagenerator.generator.NewsGenerator;*/

public class DataGeneratorMain 
{
	private static final Logger LOG = Logger.getLogger(DataGeneratorMain.class);	
	private ApplicationContext context;
	
    public static void main( String[] args ){
        DataGeneratorMain generator = new DataGeneratorMain();
        generator.generateData();
    }
    
    private void generateData(){
    	context = new ClassPathXmlApplicationContext("/datagenerator-context.xml");
    	
    	LOG.info( "---------- START DATA GENERATION ----------" );
    	
    	
    	try {
			generateFromSqlFile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	/*
        DataGenerator eg = (EmployeeGenerator)context.getBean("employeeGenerator");
        eg.generate();
        
        DataGenerator ng = (NewsGenerator)context.getBean("newsGenerator");
        ng.generate();
        
        LocationGenerator lg = (LocationGenerator)context.getBean("locationGenerator");
        lg.generate();
        */
        LOG.info( "---------- DATA GENERATION COMPLETE ----------" );
    }
    
    public static void generateFromSqlFile() throws SQLException  
    {  
        String s = new String();  
        StringBuffer sb = new StringBuffer();         
  
        try  
        {  
            FileReader fr = new FileReader(new File("test_dataScript.sql"));
            BufferedReader br = new BufferedReader(fr);  
  
            while((s = br.readLine()) != null)  
            {  
                sb.append(s);  
            }  
            br.close();                        
            
            Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/ticketlineDB", "sa", "");  
            Statement st = c.createStatement();  
  
            st.execute(sb.toString());               
        }  
        catch(Exception e)  
        {  
            LOG.error(e.getMessage());
        }  
    }
}
