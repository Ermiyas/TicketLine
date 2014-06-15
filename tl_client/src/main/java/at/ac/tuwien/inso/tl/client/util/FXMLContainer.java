package at.ac.tuwien.inso.tl.client.util;

/**
 * Enthält den XML-Content und den dazugehörigen Controller
 * 
 * @param <T> Der Typ des Controllerobjektes
 */
public class FXMLContainer<T> {
	private Object content;
	private T controller;
	
	/**
	 * Initialisiert einen neuen FXMLContainer
	 * @param content Das XML-Content-Objekt
	 * @param controller Den dazugehörigen Controller
	 */
	public FXMLContainer(Object content, T controller) {
		this.content = content;
		this.controller = controller;
	}
	
	/**
	 * Liefert das XML-Content-Objekt
	 * @return XML-Content-Objekt
	 */
	public Object getContent() {
		return content;
	}
	
	/**
	 * Liefert die Instanz des Controller
	 * @return Controller
	 */
	public T getController() {
		return controller;
	}
	
}
