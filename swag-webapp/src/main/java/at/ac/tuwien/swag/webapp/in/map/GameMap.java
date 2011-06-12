package at.ac.tuwien.swag.webapp.in.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.base.BaseUtils;

public class GameMap extends Panel {
    private static final long serialVersionUID = -2473064801663338918L;
    
    private ListView<List<Square>> gameMaplistView;
    private IModel<List<List<Square>>> gameMapList;
    private MapUser mapUser;
	private MapModalWindow mapModalWindow;

	@Inject
    private MapUserDAO mapUserDao;
	
	@Inject
	private SquareDAO squareDao;
	
	 @Inject
	 private BaseUtils baseutils;

	private List<Square> foreignSquares;
	
    public GameMap(String id, IModel<List<List<Square>>> gameMapList) {
        super(id);

        SwagWebSession session = (SwagWebSession) getSession(); 
        this.gameMapList	= gameMapList;
        this.mapUser		= getMapuser(session.getUsername(), session.getMapname());
        this.foreignSquares = getForeignSquare(session.getUsername(), session.getMapname());
        
        this.setOutputMarkupId(true);

        this.setupGameMapView();
        this.add(gameMaplistView);
        
        this.setupMapModalWindow();
        this.add(mapModalWindow);
    }

    private void setupMapModalWindow() {
    	 // The ModalWindow, showing some choices for the user to select.
        mapModalWindow = new MapModalWindow("modalwindow"){
        	private static final long serialVersionUID = 6244873170722607468L;

			void onSelect(AjaxRequestTarget target, String selection) {
            // Handle Select action
           //     close(target);
			}

			void onCancel(AjaxRequestTarget target) {
                // Handle Cancel action
              //  close(target);
            }

			@Override
			void onSettle(AjaxRequestTarget target, long squareId) {
				
				Square square	= squareDao.findById(squareId);
				
				square.setUser(mapUser);
				mapUser.getSquares().add(square);
				
				squareDao.beginTransaction();
        			squareDao.update(square);
        			mapUserDao.update(baseutils.reduceRessources(mapUser, 2500));
        		squareDao.commitTransaction();
				
				close(target);
			}
        };
    }
    
    private void setupGameMapView() {
        gameMaplistView = new ListView<List<Square>>("gameMap", gameMapList) {
            private static final long serialVersionUID = 7083713778515545799L;

            @Override
            protected void populateItem(ListItem<List<Square>> row) {

                List<Square> rowList = row.getModelObject();

                ListView<Square> rowListView = new ListView<Square>("row", rowList) {
                    private static final long serialVersionUID = 3054181382288233598L;

                    @Override
                    protected void populateItem(ListItem<Square> squareList) {

                        final Square square = squareList.getModelObject();

                        Label label = null;
                        if(checkIfMySquare(square)) {
                                label = new Label("square", "BASEOWNEDBYME");
                                label.add(new SimpleAttributeModifier("class", "baseSquare"));
                            if (square.getIsHomeBase()) {
                                label = new Label("square", "HOMEBASE");
                                label.add(new SimpleAttributeModifier("class", "homeBaseSquare"));
                            }
                        } else {
                        	label =new Label("square", "X: " + square.getCoordX() + " EMPTY "+square.getId()+"  Y: " + square.getCoordY());

                            if(foreignSquares.contains(square)){
                            	label = new Label("square", "FOREIGNBASE");
                                label.add(new SimpleAttributeModifier("class", "baseSquare"));
                                if (square.getIsHomeBase()) {
                                    label = new Label("square", "FOREIGN-HOMEBASE");
                                    label.add(new SimpleAttributeModifier("class", "homeBaseSquare"));
                                }
                            }
                        }
                        
                        squareList.add(label);
                        
/////////////////////////////////////////// MODAL WINDOW ////////////////////////////////////////////////
                        AjaxFallbackLink<String> squareLink = new AjaxFallbackLink<String>("squareLink") {
                			private static final long serialVersionUID = -2641432580203719830L;
                			@Override
                			public void onClick(AjaxRequestTarget target) {
                				
                				// Sets current selected squareId
                				SwagWebSession session = (SwagWebSession) getSession(); 
                		        session.setSelectedSquareId(square.getId());
                				
                				if(checkIfMySquare(square)) {
                					mapModalWindow.loadBasePanel();
                				}else{
                					if(foreignSquares.contains(square)){
                						mapModalWindow.loadForeignSquareModalPanel();
                					}else {
                						mapModalWindow.loadEmptySquareModalPanel(); 
                					}
                				}
                				mapModalWindow.show(target); 
                			}
                		};			       
/////////////////////////////////////////// MODAL WINDOW ////////////////////////////////////////////////

							squareLink.setOutputMarkupId(true);
							squareList.add(squareLink);
							 									
							label.setOutputMarkupId(true);
							squareLink.add(label);
                    }
                };
                row.add(rowListView);
            }
        };
    }

    
    private MapUser getMapuser(String username, String mapname) {
        String query =
            "SELECT m FROM MapUser m LEFT JOIN FETCH m.squares WHERE m.user.username = :username AND m.map.name = :mapname";

        SwagWebSession session = (SwagWebSession) getSession();

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", session.getUsername());
        values.put("mapname", session.getMapname());

        List<MapUser> buffer = mapUserDao.findByQuery(query, values);

        if (!buffer.isEmpty()) {
        	
            return buffer.get(0);
        } else {
            setResponsePage(MapPage.class);
            
            return null;
        }
    }
    
    /**
     * 
     * @param sq
     * @return
     */
    private boolean checkIfBaseBuildings(Square sq) {
        if (sq.getBuildings() == null || sq.getBuildings().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @param sq
     * @return
     */
    private boolean checkIfMySquare(Square sq) {
        if (mapUser != null && mapUser.getSquares().contains(sq)) {
            return true;
        }
        return false;
    }
    
    private List<Square> getForeignSquare(String username, String mapname) {
    	String query ="SELECT s FROM MapUser m JOIN m.squares s WHERE m.user.username != :username AND m.map.name = :mapname";
        Map<String, String> values = new HashMap<String, String>();
        values.put("username", username);
        values.put("mapname", mapname);
        List<Square> buffer = squareDao.findByQuery(query, values);

            return buffer;
    }
}
