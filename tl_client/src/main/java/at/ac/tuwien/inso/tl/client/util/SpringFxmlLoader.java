package at.ac.tuwien.inso.tl.client.util;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bridge between Spring and JavaFX which allows to inject Spring beans in JavaFX controller
 * 
 * Based on http://www.javacodegeeks.com/2013/03/javafx-2-with-spring.html
 * 
 */
public class SpringFxmlLoader {
	
	private static final Logger LOG = Logger.getLogger(SpringFxmlLoader.class);
	
	private static final SpringFxmlLoader INSTANCE = new SpringFxmlLoader();

	private final ApplicationContext appContext;

	private SpringFxmlLoader() {
		this.appContext = new ClassPathXmlApplicationContext("/client-context.xml");
		((ClassPathXmlApplicationContext)this.appContext).start();
	}
	
	public static SpringFxmlLoader getInstance() {
		return INSTANCE;
	}
	
	public <T> FXMLContainer<T> loadExtended(String url) {
		try (InputStream fxmlStream = SpringFxmlLoader.class
				.getResourceAsStream(url)) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SpringFxmlLoader.class.getResource(url));
			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> clazz) {
					LOG.debug("Retrieving Spring bean for class " + clazz.getName());
					return appContext.getBean(clazz);
				}
			});
			
			loader.setResources(BundleManager.getBundle());
			return new FXMLContainer<T>(loader.load(fxmlStream), (T)loader.getController());
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}
	
	public Object load(String url) {
		return loadExtended(url).getContent();
	}

}