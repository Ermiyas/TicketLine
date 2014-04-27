package at.ac.tuwien.inso.tl.datagenerator;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.ac.tuwien.inso.tl.datagenerator.generator.DataGenerator;
import at.ac.tuwien.inso.tl.datagenerator.generator.EmployeeGenerator;
import at.ac.tuwien.inso.tl.datagenerator.generator.NewsGenerator;

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
        
        DataGenerator eg = (EmployeeGenerator)context.getBean("employeeGenerator");
        eg.generate();
        
        DataGenerator ng = (NewsGenerator)context.getBean("newsGenerator");
        ng.generate();
        
        LOG.info( "---------- DATA GENERATION COMPLETE ----------" );
    }
}
