package at.ac.tuwien.swag.webapp.in.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.dao.BuildingDAO;
import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.domain.Building;
import at.ac.tuwien.swag.model.domain.BuildingType;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Square;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.map.MapPage;

import com.google.inject.Inject;

public abstract class BasePanel extends Panel {
	private static final long serialVersionUID = -7684943204211861124L;

	private HashMap<BuildingType, Building> buildings;

    @Inject
    private BuildingDAO buildingsDao;

    @Inject
    private SquareDAO squareDAO;

    @Inject
    private MapUserDAO mapUserDAO;

    private Square square;
    private MapUser mapuser;

    public BasePanel(String id, long squareId) {
        super(id);

        // Retrieves the square
        square = squareDAO.findById(squareId);

        setMapuser();

        fetchBuildings();

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);

        setupForm();

        PageParameters params = new PageParameters();
        params.add("square", squareId);

        setupLinks(params);

    }

    private void setMapuser() {
        String query =
            "SELECT m FROM MapUser m LEFT JOIN FETCH m.squares WHERE m.user.username = :username AND m.map.name = :mapname";

        SwagWebSession session = (SwagWebSession) getSession();

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", session.getUsername());
        values.put("mapname", session.getMapname());

        List<MapUser> buffer = mapUserDAO.findByQuery(query, values);

        if (!buffer.isEmpty()) {
            mapuser = buffer.get(0);
        } else {
            setResponsePage(MapPage.class);
        }

        if (!mapuser.getSquares().contains(square)) {
            setResponsePage(MapPage.class);
        }
    }

    private void setupLinks(PageParameters params) {
        BookmarkablePageLink barracksLink = new BookmarkablePageLink("barracksLink", Barracks.class, params);
        if (!buildings.containsKey(BuildingType.BARRACKS)) {
            barracksLink.setVisible(false);
        }
        add(barracksLink);
        BookmarkablePageLink stableLink = new BookmarkablePageLink("stableLink", Stable.class, params);
        if (!buildings.containsKey(BuildingType.STABLE)) {
            stableLink.setVisible(false);
        }
        add(stableLink);
        BookmarkablePageLink destructionLink = new BookmarkablePageLink("destructionLink", Destruction.class, params);
        if (!buildings.containsKey(BuildingType.DESTRUCTION)) {
            destructionLink.setVisible(false);
        }
        add(destructionLink);
        BookmarkablePageLink upgradeLink = new BookmarkablePageLink("upgradeLink", Upgrades.class, params);
        if (!buildings.containsKey(BuildingType.UPGRADE)) {
            upgradeLink.setVisible(false);
        }
        add(upgradeLink);
        add(new BookmarkablePageLink("troopsLink", Troops.class, params));
    }

    private void setupForm() {
        Form<?> buildWood = createForm("buildWood", "woodButton", BuildingType.WOOD);
        Form<?> buildClay = createForm("buildClay", "clayButton", BuildingType.CLAY);
        Form<?> buildStable = createForm("buildStable", "stableButton", BuildingType.STABLE);
        Form<?> buildBarracks = createForm("buildBarracks", "barracksButton", BuildingType.BARRACKS);
        Form<?> buildUpgrades = createForm("buildUpgrades", "upgradesButton", BuildingType.UPGRADE);
        Form<?> buildDestruction = createForm("buildDestruction", "destructionButton", BuildingType.DESTRUCTION);
        Form<?> buildGrain = createForm("buildGrain", "grainButton", BuildingType.GRAIN);
        Form<?> buildIron = createForm("buildIron", "ironButton", BuildingType.IRON);

        add(buildWood);
        add(buildClay);
        add(buildStable);
        add(buildBarracks);
        add(buildUpgrades);
        add(buildDestruction);
        add(buildGrain);
        add(buildIron);
    }

    private Form<?> createForm(String form, final String button, final BuildingType type) {
        Form<?> newForm = new Form<Object>(form);

        final Building building = buildings.get(type);

        AjaxButton newButton = new AjaxButton(button) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                if (building != null) {

                    Integer newLevel = building.getLevel() + 1;
                    building.setLevel(newLevel);

                    buildingsDao.beginTransaction();
                    buildingsDao.update(building);
                    buildingsDao.commitTransaction();

                    info("upgraded");
                } else {

                    Building building = new Building();
                    building.setLevel(1);
                    building.setType(type);
                    building.setUpgrading(false); // TODO --> sanitize
                    building.setSquare(square);

                    buildingsDao.beginTransaction();
                    buildingsDao.insert(building);
                    buildingsDao.commitTransaction();

                    info("built");
                }

                target.addComponent(form);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                // TODO Auto-generated method stub

            }

        };

        if (building != null) {

            if (building.getLevel() > 9) {
                newButton.setModel(new Model<String>("maxed"));
                newButton.setEnabled(false);
            } else {
                Integer newLevel = building.getLevel() + 1;
                newButton.setModel(new Model<String>("upgrade to " + newLevel));
            }

            if (building.getUpgrading()) {
                newButton.setEnabled(false);
            }

            if (!checkRessources(500)) {
                newButton.setEnabled(false);
            }

        } else {
            newButton.setModel(new Model<String>("build"));
            if (!checkRessources(1000)) {
                newButton.setEnabled(false);
            }
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

    private boolean checkRessources(Integer res) {

        if (mapuser.getClayRessource().getAmount() < res) {
            return false;
        }

        if (mapuser.getGrainRessource().getAmount() < res) {
            return false;
        }

        if (mapuser.getIronRessource().getAmount() < res) {
            return false;
        }

        if (mapuser.getWoodRessource().getAmount() < res) {
            return false;
        }

        return true;
    }

    public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onSelect(AjaxRequestTarget target, String selection);
}
