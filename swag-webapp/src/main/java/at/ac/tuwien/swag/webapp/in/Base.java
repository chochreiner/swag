package at.ac.tuwien.swag.webapp.in;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.dao.BuildingDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.Building;
import at.ac.tuwien.swag.model.domain.BuildingType;
import at.ac.tuwien.swag.model.domain.Square;

import com.google.inject.Inject;

public class Base extends InPage {
    private static final long serialVersionUID = 1453724836631121134L;

    private HashMap<BuildingType, Building> buildings;

    @Inject
    private BuildingDAO buildingsDao;

    @Inject
    private SquareDAO squareDAO;

    private Square square;

    public Base(PageParameters parameters) {
        super(parameters);

        String squareId = parameters.get("id").toString();

        squareId = "10";
        // TODO remove

        square = squareDAO.findById(Long.parseLong(squareId));

        fetchBuildings();

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        Form buildWood = createForm("buildWood", "woodButton", BuildingType.WOOD);
        Form buildClay = createForm("buildClay", "clayButton", BuildingType.CLAY);
        Form buildStable = createForm("buildStable", "stableButton", BuildingType.STABLE);
        Form buildBarracks = createForm("buildBarracks", "barracksButton", BuildingType.BARRACKS);
        Form buildUpgrades = createForm("buildUpgrades", "upgradesButton", BuildingType.UPGRADE);
        Form buildDestruction = createForm("buildDestruction", "destructionButton", BuildingType.DESTRUCTION);
        Form buildGrain = createForm("buildGrain", "grainButton", BuildingType.GRAIN);
        Form buildIron = createForm("buildIron", "ironButton", BuildingType.IRON);

        add(buildWood);
        add(buildClay);
        add(buildStable);
        add(buildBarracks);
        add(buildUpgrades);
        add(buildDestruction);
        add(buildGrain);
        add(buildIron);

    }

    private Form createForm(String form, final String button, final BuildingType type) {
        Form newForm = new Form(form);
        Button newButton = new Button(button) {
            @Override
            public void onSubmit() {
                Building building = new Building();
                building.setLevel(0);
                building.setType(type);
                building.setUpgrading(false); // TODO --> sanitize
                building.setSquare(square);

                buildingsDao.beginTransaction();
                buildingsDao.insert(building);
                buildingsDao.commitTransaction();

                info(button);
            }
        };

        if (buildings.containsKey(type)) {
            Building building = buildings.get(type);
            if (building.getLevel() > 9) {
                newButton.setModel(new Model<String>("maxed"));
                newButton.setEnabled(false);
            } else {
                newButton.setModel(new Model<String>("upgrade to " + (building.getLevel() + 1)));
            }

            if (building.getUpgrading()) {
                newButton.setEnabled(false);
            }

        } else {
            newButton.setModel(new Model<String>("build"));

            // TODO check ressources for upgrading
        }

        newForm.add(newButton);
        return newForm;
    }

    private void fetchBuildings() {
        buildings = new HashMap<BuildingType, Building>();

        String query = "SELECT b FROM Building b WHERE b.square.id = :square";

        Map<String, Long> values = new HashMap<String, Long>();
        values.put("square", square.getId());

        for (Building building : buildingsDao.findByQuery(query, values)) {
            buildings.put(building.getType(), building);
        }
    }
}
