/**
 * Sample Skeleton for "ItemListForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.ArticleService;
import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.PerformanceService;
import at.ac.tuwien.inso.tl.client.client.RowService;
import at.ac.tuwien.inso.tl.client.client.SeatService;
import at.ac.tuwien.inso.tl.client.client.ShowService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.ArticleDto;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

/**
 * @author Robert Bekker 8325143
 *
 */
@Controller @Scope("prototype") 
public class ItemListFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ItemListFormController.class);

	private static final String LONG_DATE_FORMAT = "dd.MM.yyyy";
	
	// TODO div. Services einbinden
	@Autowired private EntryService entryService;				// Entry-Services
	@Autowired private ArticleService articleService;			// Article-Services
	@Autowired private TicketService ticketService;				// Ticket-Services
	@Autowired private SeatService seatService;					// Seat-Services
	@Autowired private RowService rowService;					// Row-Services
	@Autowired private ShowService showService;					// Show-Services
	@Autowired private PerformanceService performanceService;	// Performance-Services
	
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
		
		setList((List<EntryDto>) null);
	}

	/**
	 * Zeilen entsprechend einem Warenkorb setzen
	 * 
	 * @param entryDtoList
	 */
	public void setList(BasketDto basket) {
		List<EntryDto> entryDtoList = new ArrayList<EntryDto>();

		// TODO Entry-Service einbinden
		try {
			entryDtoList = entryService.getList(basket);				// get Entry-List by Basket
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
		
		setList(entryDtoList);
	}
	
	/**
	 * Zeilen setzen
	 * 
	 * @param entryDtoList
	 */
	public void setList(List<EntryDto> entryDtoList) {
		LOG.info("");
		
		// DTO uebernehmen und alle Felder setzen
		
		// Tabelle loeschen
		tvItemList.getItems().clear();
		
		if (entryDtoList != null) {
			// Listen-Daten laden
			ObservableList<ItemDto> items = FXCollections.observableArrayList();
			for (EntryDto entry : entryDtoList) {
				items.add(new ItemDto(entry));
			}
			
			// Daten in Spalten laden
			tcItemMark.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("mark"));						// "Id" -> call 'ItemDto.getMark()'
			tcItemType.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("type"));						// "Id" -> call 'ItemDto.getType()'
			tcItemDescr.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("descr"));					// "Id" -> call 'ItemDto.getDescr()'
			tcItemId.setCellValueFactory(new PropertyValueFactory<ItemDto, Integer>("id"));							// "Id" -> call 'ItemDto.getId()'
			tcItemAmount.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("amount"));					// "title" -> call 'ItemDto.getAmount()'
			tcItemBuyWithPoints.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("buyWithPoints"));	// "title" -> call 'ItemDto.getBuyWithPoints()'
			tcItemSold.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("sold"));						// "title" -> call 'ItemDto.getSold()'
			
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
	public List<EntryDto> getList() {
		LOG.info("");
		
		List<ItemDto> itemList = getItemList();
		List<EntryDto> entryList = new ArrayList<EntryDto>();
		for (ItemDto item : itemList) {
			entryList.add(item.getEntry());
		}
		return entryList;
	}
	
	/**
	 * Nur markierte Zeilen in neuer Entry-Liste zurueckgeben
	 * 
	 * @return
	 */
	public List<EntryDto> getMarkedList() {
		LOG.info("");
		
		List<ItemDto> itemList = getItemList();
		List<EntryDto> entryList = new ArrayList<EntryDto>();
		for (ItemDto item : itemList) {
			if (item.getMarkIt()) {
				entryList.add(item.getEntry());
			}
		}
		return entryList;
	}
	
	/**
	 * Aktuell ausgewaehltes Element (de-)markieren
	 */
	public void toggleMarkedItem() {
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
	 * Aktuell ausgewaehltes Item zurueckgeben
	 * 
	 * @return
	 */
	private ItemDto getItem() {
		LOG.info("");
		
		// ausgewaehlte Zeile zurueckgeben
		return tvItemList.getSelectionModel().getSelectedItem();
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
		private EntryDto entry;
		private ArticleDto article;
		private TicketDto ticket;
		private SeatDto seat;
		private RowDto row;
		private ShowDto show;
		private PerformanceDto performance;

		private Boolean markit;				// Markierung, ob storniert werden soll
		private String mark;				// Textrepraesentation der Markierung
		private String type;
		private String descr;
		private Integer id;					// von EntryDto.id
		private String amount;				// von EntryDto.amount
		private String buyWithPoints;		// von EntryDto.buyWithPoints
		private String sold;				// von EntryDto.sold
		
		// Konstruktor
		public ItemDto(EntryDto entry) {
			if (entry == null) {
				entry = new EntryDto();
			}

			this.entry = entry;
			// TODO testweise div. verknuepfte Dto's haendisch geholt, ist aber zu langsam
			// TODO schlussendlich muss dies am Server zusammengestoepselt werden.
			if (entry.getArticleId() != null) {
				try {
					this.article = articleService.getById(entry.getArticleId());			// get Article of Entry
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (entry.getTicketId() != null) {
				try {
					this.ticket = ticketService.getById(entry.getTicketId());					// get Ticket of Entry
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (this.ticket.getShowId() == null) {
					try {
						this.seat = seatService.getSeat(this.ticket);							// get Seat of Ticket
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (this.seat != null) {
						try {
							this.row = rowService.getRow(this.seat);							// get Seat of Ticket
						} catch (ServiceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (this.row != null) {
							try {
								this.show = showService.getShow(this.row);						// get Show of Row
							} catch (ServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} else {
					try {
						this.show = showService.getShow(this.ticket);							// get Show of Ticket
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (this.show != null) {
					try {
						this.performance = performanceService.getPerformance(this.show);		// get Performance of Show
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			// Objekt-Variablen setzen
			
			this.markit = false;				// initialize as not marked for reverse (storno)
			this.mark = "";	
			this.type = "";
			this.descr = "";
			if (ticket != null) {
				this.type = intString("stornopage.ticket");
				if (show != null && performance != null) {
					this.descr = new SimpleDateFormat(LONG_DATE_FORMAT).format(show.getDateOfPerformance());
					this.descr += " '" + performance.getDescription() + "' "; 
					this.descr += intString("stornopage.room") + ": " + show.getRoom();
					if (seat != null) {
						this.descr += ", " + intString("stornopage.seat") + " " + seat.getSequence();
						if (row != null) {
							this.descr += ", " + intString("stornopage.row") + " " + row.getSequence();
						}
					}
				}
			} else if (article != null) {
				this.type = intString("stornopage.article");
				this.descr = article.getDescription();
			} 
			
			// Werte von uebergebener EntryDto
			this.id = entry.getId();
			this.amount = String.format("%.2f", entry.getAmount()/100.0);
			if (entry.getBuyWithPoints() == null) {
				this.buyWithPoints = ""; 
			} else if (entry.getBuyWithPoints()) {
				this.buyWithPoints = intString("stornopage.points");
			} else {
				this.buyWithPoints = intString("stornopage.money");
			}
			if (entry.getSold() == null) {
				this.sold = ""; 
			} else if (entry.getSold()) {
				this.sold = intString("stornopage.sold");
			} else {
				this.sold = intString("stornopage.reserved");
			}
		}

		public EntryDto getEntry() { return entry; }
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
		
	}
}
