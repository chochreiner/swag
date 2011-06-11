package at.ac.tuwien.swag.webapp.in;

import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;

import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Square;

public class GameMap extends WebMarkupContainer {

    private static final long serialVersionUID = -2473064801663338918L;
    private ListView<List<Square>> gameMaplistView;
    private IModel<List<List<Square>>> gameMapList;
    private MapUser mapUser;

    public GameMap(String id, MapUser mapUser, IModel<List<List<Square>>> gameMapList) {
        super(id);

        this.mapUser = mapUser;
        this.gameMapList = gameMapList;

        this.setOutputMarkupId(true);

        this.setupGameMapView();
        this.add(gameMaplistView);
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

                        Square square = squareList.getModelObject();

                        Label label = null;
                        if (mapUser.getSquares().contains(square)) {

                            label = new Label("square", "X: " + square.getCoordX() + " AAAAA Y: " + square.getCoordY());
                            if (checkIfBuildings(square)) {
                                label = new Label("square", "BASEOWNEDBYME");
                                label.add(new SimpleAttributeModifier("class", "baseSquare"));
                            }
                            if (square.getIsHomeBase()) {
                                label = new Label("square", "HOMEBASE");
                                label.add(new SimpleAttributeModifier("class", "homeBaseSquare"));
                            }
                        } else {
                            if (checkIfBuildings(square)) {
                                label =
                                    new Label("square", "X: " + square.getCoordX() + " "
                                            + square.getUser().getUser().getUsername() + " Y: " + square.getCoordY());
                                label.add(new SimpleAttributeModifier("class", "baseSquare"));
                            }

                            label =
                                new Label("square", "X: " + square.getCoordX() + " EMPTY Y: " + square.getCoordY());
                        }
                        label.setOutputMarkupId(true);
                        squareList.add(label);
                    }
                };
                row.add(rowListView);
            }
        };
    }

    private boolean checkIfBuildings(Square sq) {
        if (sq.getBuildings() == null || sq.getBuildings().isEmpty()) {
            return false;
        }
        return true;
    }

    public void updateModel() {
        gameMaplistView.setModel(gameMapList);
    }
}
