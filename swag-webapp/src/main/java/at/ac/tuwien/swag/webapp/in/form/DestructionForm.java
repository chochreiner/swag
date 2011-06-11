package at.ac.tuwien.swag.webapp.in.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.Model;

import at.ac.tuwien.swag.model.dao.MapUserDAO;
import at.ac.tuwien.swag.model.dao.SoldierDAO;
import at.ac.tuwien.swag.model.dao.SquareDAO;
import at.ac.tuwien.swag.model.dao.TroopDAO;
import at.ac.tuwien.swag.model.domain.MapUser;
import at.ac.tuwien.swag.model.domain.Soldier;
import at.ac.tuwien.swag.model.domain.SoldierType;
import at.ac.tuwien.swag.model.domain.Troop;
import at.ac.tuwien.swag.webapp.SwagWebSession;
import at.ac.tuwien.swag.webapp.in.base.BaseUtils;
import at.ac.tuwien.swag.webapp.in.map.MapPage;

import com.google.inject.Inject;

public class DestructionForm extends Form<Void> {
    private static final long serialVersionUID = 6040480253914226510L;

    @Inject
    private MapUserDAO mapUserDAO;

    @Inject
    private TroopDAO troopDAO;

    @Inject
    private SoldierDAO soldierDAO;

    @Inject
    private SquareDAO squareDAO;

    private Troop troop;
    private MapUser mapuser;
    private BaseUtils baseutils;
    private Long squareId;

    private RequiredTextField<String> amount;
    private DropDownChoice<SoldierType> dropdown;

    public DestructionForm(String id, Long squareId) {
        super(id);
        baseutils = new BaseUtils();
        this.squareId = squareId;

        setTroop(squareId);

        setMapuser(squareId);

        amount = new RequiredTextField<String>("amount", new Model<String>());
        dropdown =
            new DropDownChoice<SoldierType>("soldierType", new Model<SoldierType>(), Arrays.asList(new SoldierType[]{
                SoldierType.ALMIGHTYFABIAN }));

        add(amount);
        add(dropdown);
    }

    @Override
    protected void onSubmit() {

        Integer size = Integer.parseInt(amount.getModel().getObject());

        switch (dropdown.getModel().getObject()) {
            case ALMIGHTYFABIAN: {
                if (!baseutils.checkRessources(mapuser, (1000 * size))) {
                    return;
                }

                soldierDAO.beginTransaction();
                mapUserDAO.update(baseutils.reduceRessources(mapuser, (1000 * size)));

                Soldier soldier = null;

                for (Soldier s : troop.getSoldiers()) {
                    if (s.getType() == SoldierType.ALMIGHTYFABIAN) {
                        soldier = s;
                        soldier.setAmount(s.getAmount() + size);
                        soldierDAO.update(soldier);
                        break;
                    }
                }

                if (soldier == null) {
                    troop.setSquare(squareDAO.findById(squareId));
                    soldier = new Soldier(SoldierType.ALMIGHTYFABIAN, 100.0, size, 100.0, troop);
                    soldierDAO.insert(soldier);
                }
                soldierDAO.commitTransaction();
                info("You have built " + size + " " + SoldierType.ALMIGHTYFABIAN);

            }
                break;
        }
    }

    private void setMapuser(Long squareId) {
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

        if (!mapuser.getSquares().contains(squareDAO.findById(squareId))) {
            setResponsePage(MapPage.class);
        }
    }

    private void setTroop(Long squareId) {
        String query =
            "SELECT t FROM Troop t LEFT JOIN FETCH t.soldiers WHERE t.square.id = :square";

        Map<String, Long> values = new HashMap<String, Long>();
        values.put("square", squareId);

        List<Troop> buffer = troopDAO.findByQuery(query, values);

        if (!buffer.isEmpty()) {
            troop = buffer.get(0);
        } else {
            troop = new Troop(0, squareDAO.findById(squareId), new ArrayList<Soldier>());
        }
    }
}
