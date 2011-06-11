package at.ac.tuwien.swag.webapp.in.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import at.ac.tuwien.swag.model.dao.TroopDAO;
import at.ac.tuwien.swag.model.domain.Soldier;
import at.ac.tuwien.swag.model.domain.Troop;
import at.ac.tuwien.swag.model.dto.SoldierDTO;
import at.ac.tuwien.swag.webapp.in.InPage;

import com.google.inject.Inject;

public class Troops extends InPage {

    private static final long serialVersionUID = -2779784155739341375L;

    @Inject
    private TroopDAO troopDao;

    public Troops(PageParameters parameters) {
        super(parameters);

        String squareId = parameters.get("square").toString();

        // TODO check if this is the oweners fiels; also do in barracks, stable ...

        String query = "SELECT t FROM Troop t LEFT JOIN FETCH t.soldiers WHERE t.square.id = :square";

        Map<String, Long> values = new HashMap<String, Long>();
        values.put("square", Long.parseLong(squareId));

        List<Troop> troop = troopDao.findByQuery(query, values);

        List<SoldierDTO> soldiers = new ArrayList<SoldierDTO>();

        if (!troop.isEmpty()) {
            if (!troop.get(0).getSoldiers().isEmpty()) {

                for (Soldier s : troop.get(0).getSoldiers()) {
                    soldiers.add(new SoldierDTO(s.getType(), s.getAttackStrength(), s.getAmount(), s.getSpeed(), null));
                }
            }
        }

        add(new ListView<SoldierDTO>("soldiers", soldiers) {
            private static final long serialVersionUID = -5629758397338148788L;

            @Override
            protected void populateItem(ListItem<SoldierDTO> item) {
                SoldierDTO soldier = item.getModelObject();
                item.add(new Label("type", soldier.getType().toString()));
                item.add(new Label("amount", soldier.getAmount().toString()));
            }
        });

    }
}
