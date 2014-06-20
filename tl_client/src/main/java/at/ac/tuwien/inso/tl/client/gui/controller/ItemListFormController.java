/**
 * Sample Skeleton for "ItemListForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;

/**
 * @author Robert Bekker 8325143
 *
 */
@Controller @Scope("prototype") 
public class ItemListFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ItemListFormController.class);

	private static final String LONG_DATE_FORMAT = "dd.MM.yyyy";
	
	// TODO div. Services einbinden
	
	@Autowired private BasketService basketService;	
	@Autowired private EntryService entryService;
	
	
	// FXML-injizierte Variablen

    @FXML private ResourceBundle resources;				// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;							// URL location of the FXML file that was given to the FXMLLoader

    @FXML private AnchorPane apItemList;								// fx:id="apItemList"		// eigenes Root-Pane 
    @FXML private TitledPane tpItemList;		 						// fx:id="tpItemList"
    @FXML private TableView<ItemDto> tvItemList;						// fx:id="tvItemList" 
    @FXML private TableColumn<ItemDto, String> tcItemMark;				// fx:id="tcItemMark" 
    @FXML private TableColumn<ItemDto, String> tcItemType;				// fx:id="tcItemType" 
    @FXML private TableColumn<ItemDto, String> tcItemDescr;				// fx:id="tcItemDescr" 
    @FXML private TableColumn<ItemDto, Integer> tcItemId;				// fx:id="tcItemId" 
    @FXML private TableColumn<ItemDto, String> tcItemAmount;			// fx:id="tcItemAmount" 
    @FXML private TableColumn<ItemDto, String> tcItemBuyWithPoints;		// fx:id="tcItemBuyWithPoints" 
    @FXML private TableColumn<ItemDto, String> tcItemSold;				// fx:id="tcItemSold" 

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
        assert resources != null : 				"\"resources\" was not injected: check your Controller-file 'ItemListFormController.java'.";
        assert location != null : 				"\"location\" was not injected: check your Controller-file 'ItemListFormController.java'.";
        assert apItemList != null : 			"fx:id=\"apItemList\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tpItemList != null : 			"fx:id=\"tpItemList\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tvItemList != null : 			"fx:id=\"tvItemList\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tcItemMark != null : 			"fx:id=\"tcItemMark\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tcItemType != null : 			"fx:id=\"tcItemType\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tcItemDescr != null : 			"fx:id=\"tcItemDescr\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tcItemId != null : 				"fx:id=\"tcItemId\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tcItemAmount != null : 			"fx:id=\"tcItemAmount\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tcItemBuyWithPoints != null : 	"fx:id=\"tcItemBuyWithPoints\" was not injected: check your FXML file 'ItemListForm.fxml'.";
        assert tcItemSold != null : 			"fx:id=\"tcItemSold\" was not injected: check your FXML file 'ItemListForm.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

        // ------------------------------------
        
//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				LOG.info("");
//        
//				if (null != apItemList) {
//        
//					// TODO restliche Initialisierung
//		
//				}
//		    }
//		});

        // Platzhalter-Text fuer leere Liste festlegen
        tvItemList.setPlaceholder(new Text(intString("stornopage.emptyentrylist")));

        // keine weiteren leeren Spalten anzeigen
//		tvItemList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tvItemList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

		// TODO restliche Initialisierung

	}

	// Getter fuer alle Tabellenspalten, ...

	public AnchorPane getApItemList() { return apItemList; }
	public TitledPane getTpItemList() { return tpItemList; }
	public TableView<ItemDto> getTvItemList() { return tvItemList; }
	public TableColumn<ItemDto, String> getTcItemMark() { return tcItemMark; }
	public TableColumn<ItemDto, String> getTcItemType() { return tcItemType; }
	public TableColumn<ItemDto, String> getTcItemDescr() { return tcItemDescr; }
	public TableColumn<ItemDto, Integer> getTcItemId() { return tcItemId; }
	public TableColumn<ItemDto, String> getTcItemAmount() { return tcItemAmount; }
	public TableColumn<ItemDto, String> getTcItemBuyWithPoints() { return tcItemBuyWithPoints; }
	public TableColumn<ItemDto, String> getTcItemSold() { return tcItemSold; }

	/**
	 * Titel der TitlePane sprachabhaengig setzen
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		LOG.info("");
		
		tpItemList.setText(intString(title));
	}

	/**
	 * Liste leeren
	 */
	public void clearList() {
		LOG.info("");
		
		// DTO alle Felder loeschen
		setList();
	}

	/**
	 * Zeilen loeschen
	 */
	public void setList() {
		LOG.info("");
		
		setList((List<ContainerDto>) null);
	}

	/**
	 * Zeilen entsprechend einem Warenkorb setzen
	 * 
	 * @param entryDtoList
	 */
	public void setList(BasketDto basket) throws ServiceException {
		setList(basketService.getEntryTicketArticlePerformanceRowSeatContainers(basket.getId()));
	}
	
	/**
	 * Zeilen setzen
	 * 
	 * @param containerDtoList
	 */
	public void setList(List<ContainerDto> containerDtoList) {
		LOG.info("");
		
		// DTO uebernehmen und alle Felder setzen
		
		// Tabelle loeschen
		tvItemList.getItems().clear();
		
		if (containerDtoList != null) {
			// Listen-Daten laden
			ObservableList<ItemDto> items = FXCollections.observableArrayList();
			for (ContainerDto containerDto : containerDtoList) {
				items.add(new ItemDto(containerDto));
			}
			
			// Daten in Spalten laden
			tcItemMark.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("mark"));						// "mark" -> call 'ItemDto.getMark()'
			tcItemType.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("type"));						// "type" -> call 'ItemDto.getType()'
			tcItemDescr.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("descr"));					// "descr" -> call 'ItemDto.getDescr()'
			tcItemId.setCellValueFactory(new PropertyValueFactory<ItemDto, Integer>("id"));							// "id" -> call 'ItemDto.getId()'
			tcItemAmount.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("amount"));					// "amount" -> call 'ItemDto.getAmount()'
			tcItemBuyWithPoints.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("buyWithPoints"));	// "buyWithPoints" -> call 'ItemDto.getBuyWithPoints()'
			tcItemSold.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("sold"));						// "sold" -> call 'ItemDto.getSold()'
			
//			tvItemList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tvItemList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
			
			// Daten in Tabelle schreiben
			tvItemList.setItems(items);
		}
		
	}

	/**
	 * Zeilen setzen
	 * 
	 * @param entryDtoList
	 */
	public void redrawList() {
		LOG.info("");
		
		// Workaround to force refresh: toggle visibility of each row twice
		for (int col = 0; col < tvItemList.getColumns().size() ; col++) {
			tvItemList.getColumns().get(col).setVisible(! tvItemList.getColumns().get(col).isVisible());
			tvItemList.getColumns().get(col).setVisible(! tvItemList.getColumns().get(col).isVisible());
		}
	}

	/**
	 * Alle Zeilen in neuer Item-Liste zurueckgeben
	 * 
	 * @return
	 */
	private List<ItemDto> getItemList() {
		LOG.info("");
		
		return tvItemList.getItems();
	}
	
	/**
	 * Alle Zeilen in neuer Entry-Liste zurueckgeben
	 * 
	 * @return
	 */
	public List<ContainerDto> getList() {
		LOG.info("");
		
		List<ItemDto> itemList = getItemList();
		List<ContainerDto> containerList = new ArrayList<ContainerDto>();
		for (ItemDto item : itemList) {
			containerList.add(item.getContainer());
		}
		return containerList;
	}
	
	/**
	 * Nur markierte Zeilen in neuer Entry-Liste zurueckgeben
	 * 
	 * @return
	 */
	public List<ContainerDto> getMarkedList() {
		LOG.info("");
		
		List<ItemDto> itemList = getItemList();
		List<ContainerDto> containerList = new ArrayList<ContainerDto>();
		for (ItemDto item : itemList) {
			if (item.getMarkIt()) {
				containerList.add(item.getContainer());
			}
		}
		return containerList;
	}
	
	/**
	 * Aktuell ausgewaehltes Element (de-)markieren
	 */
	public void toggleSelectedItem() {
		if (getItem() != null) {
			getItem().setMark(! getItem().getMarkIt());
			redrawList();
		}
	}
	
	/**
	 * Alle Items der Liste (de-)markieren
	 * 
	 * @param markIt
	 */
	public void markAllItems(Boolean markIt) {
		List<ItemDto> items = tvItemList.getItems();
		for (ItemDto item : items) {
			item.setMark(markIt);
		}
		redrawList();
	}
	
	/**
	 * Liste von Items anhand einer Liste von Entries (de-) markieren
	 * 
	 * @param containerList Gesuchte Eintraege
	 * @param markIt Items (de-)markeiren
	 */
	public void markItems(List<ContainerDto> containerList, Boolean markit) {
		for (ContainerDto container : containerList) {
			markItem(container, markit);
		}
	}
	/**
	 * Item mit dem passenden Eintrag (de-)markieren
	 *  
	 * @param container Gesuchter Eintrag
	 * @param markIt Item (de-)markeiren
	 */
	public void markItem(ContainerDto container, Boolean markIt) {
		if (getItem(container)!= null) {
			getItem(container).setMark(markIt);
			redrawList();
		}
	}
	
	/**
	 * Aktuell ausgewaehltes Item zurueckgeben
	 * 
	 * @return
	 */
	public ItemDto getItem() {
		LOG.info("");
		
		// ausgewaehlte Zeile zurueckgeben
		return tvItemList.getSelectionModel().getSelectedItem();
	}
	/**
	 * Item eines bestimmten Entries zurueckgeben
	 * 
	 * @return
	 */
	public ItemDto getItem(ContainerDto container) {
		LOG.info("");
		for (ItemDto item : tvItemList.getItems()) {
			if (item.getEntry().equals(container.getEntryDto()) || 
					(container.getEntryDto().getId() != null && item.getEntry().getId() != null && item.getEntry().getId().equals(container.getEntryDto().getId()))) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * Aktuell ausgewaehltes Entry zurueckgeben
	 * 
	 * @return
	 */
	public EntryDto getEntry() {
		LOG.info("");
		
		// ausgewaehlte Zeile zurueckgeben
		return getItem().getEntry();
	}
	
	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, NULL in "" uebersetzt
	 * 
	 * @param title
	 */
	private static String intString(String text) {
		LOG.info("");
		return intString(text, true);
	}
	
	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, default NULL in "" uebersetzen
	 * 
	 * @param title
	 */
	private static String intString(String text, Boolean noNull) {
		LOG.info("");
		
		if (noNull == null) {
			noNull = true;
		}
		if (text == null && ! noNull) {
			return null;
		}
		if (text == null || text.trim().equals("")) {
			return "";
		}
		try {
			return BundleManager.getBundle().getString(text).trim();
		} catch (RuntimeException e) {
			return text.trim();
		}
	}

	/**
	 * Sub-Klasse, um die zusammengesetzen Listenelemente aufbauen zu koennen
	 * 
	 * @author Robert Bekker 8325143
	 *
	 */
	public class ItemDto {
		private ContainerDto containerDto;
		private Boolean markit;				// Markierung, ob storniert werden soll
		private String mark;				// Textrepraesentation der Markierung
		private String type;
		private String descr;
		private Integer id;					// von EntryDto.id
		private String amount;				// von EntryDto.amount
		private String buyWithPoints;		// von EntryDto.buyWithPoints
		private String sold;				// von EntryDto.sold
		private Boolean reversable;
		
		// Konstruktor
		public ItemDto(ContainerDto containerDto) {
			if (containerDto == null) {
				this.containerDto = new ContainerDto();
			}
			else{
				this.containerDto = containerDto;
			}
			

			// Objekt-Variablen setzen
			
			this.markit = false;				// initialize as not marked for reverse (storno)
			this.mark = "";	
			this.type = "";
			this.descr = "";
			if (containerDto.getTicketDto() != null) {
				this.type = intString("stornopage.ticket");
				if (containerDto.getShowDto() != null && containerDto.getPerformanceDto() != null) {
					this.descr = new SimpleDateFormat(LONG_DATE_FORMAT).format(containerDto.getShowDto().getDateOfPerformance());
					this.descr += " '" + containerDto.getPerformanceDto().getDescription() + "' "; 
					this.descr += intString("stornopage.room") + ": " + containerDto.getShowDto().getRoom();
					if (containerDto.getSeatDto() != null) {
						this.descr += ", " + intString("stornopage.seat") + " " + containerDto.getSeatDto().getSequence();
						if (containerDto.getRowDto() != null) {
							this.descr += ", " + intString("stornopage.row") + " " + containerDto.getRowDto().getSequence();
						}
					}
				}
			} else if (containerDto.getArticleDto() != null) {
				this.type = intString("stornopage.article");
				this.descr = containerDto.getArticleDto().getDescription();
			} 
			
			// Werte von uebergebener EntryDto
			this.id = containerDto.getEntryDto().getId();
			this.amount = String.format("%.2f", containerDto.getEntryDto().getAmount()/100.0);
			if (containerDto.getEntryDto().getBuyWithPoints() == null) {
				this.buyWithPoints = ""; 
			} else if (containerDto.getEntryDto().getBuyWithPoints()) {
				this.buyWithPoints = intString("stornopage.points");
			} else {
				this.buyWithPoints = intString("stornopage.money");
			}
			if (containerDto.getEntryDto().getSold() == null) {
				this.sold = ""; 
			} else if (containerDto.getEntryDto().getSold()) {
				this.sold = intString("stornopage.sold");
			} else {
				this.sold = intString("stornopage.reserved");
			}
		}

		public ContainerDto getContainer() {
			
			return containerDto;
		}

		public EntryDto getEntry() { return containerDto.getEntryDto(); }
	    public String getMark() { return mark; }
	    public Boolean getMarkIt() { return markit; }
	    public void setMark(Boolean markit) {
	    	this.markit = markit;
	    	if (markit == null) {
		    	this.mark = ""; 
	    	} else if (markit) {
		    	this.mark = intString("stornopage.reverse");
	    	} else {
	    		this.mark = "";
	    	}
	    }
		public String getType() { return type; }
		public String getDescr() { return descr; }
		public Integer getId() { return id; }
		public String getAmount() { return amount; }
		public String getBuyWithPoints() { return buyWithPoints; }
		public String getSold() { return sold; }
		public Date getDate() {
			if (containerDto.getShowDto() != null) {
				return containerDto.getShowDto().getDateOfPerformance();
			}
			return null; 
		}
		public Boolean getReversable() {
			// erst bei Bedarf Stornierbarkeit abfragen
			if (this.reversable == null) {
	    		// TODO Stornier-Logik aus FE in Server umlagern!
				try {
					this.reversable = entryService.isReversible(containerDto.getEntryDto().getId());
				} catch (ServiceException e) {
				}
			}
			return this.reversable;
		}
	}
}
