package at.ac.tuwien.swag.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.swag.model.dao.SoldierDAO;
import at.ac.tuwien.swag.model.dao.TroopDAO;
import at.ac.tuwien.swag.model.domain.Soldier;
import at.ac.tuwien.swag.model.domain.Troop;
import at.ac.tuwien.swag.webapp.service.BattleService;

import com.google.inject.Inject;

public class BattleServiceImpl implements BattleService {

    @Inject
    private SoldierDAO soldierDAO;

    @Inject
    private TroopDAO troopDAO;

    @Override
    public Boolean carryOutBattle(List<Troop> attacker, List<Troop> defender) {

        Double def = (calcValue(defender) * 1.5);
        Double att = calcValue(attacker);

        Double outcome = def - att;

        soldierDAO.beginTransaction();

        reduceTroops(defender, att);
        reduceTroops(attacker, def);

        soldierDAO.commitTransaction();

        if (outcome < 0) {
            return false;
        } else {
            return true;
        }
    }

    private void reduceTroops(List<Troop> party, Double strength) {
        for (Troop troop : party) {
            List<Soldier> soldiers = troop.getSoldiers();
            if (strength < 0) {
                break;
            }

            for (Soldier s : soldiers) {
                Double power = s.getAttackStrength() * s.getAmount();
                if (power < strength) {
                    soldierDAO.delete(s);
                    soldiers.remove(s);
                }
                strength -= power;
            }
            troopDAO.update(troop);
        }
    }

    private Double calcValue(List<Troop> party) {
        Double power = 0.0;
        for (Troop troop : party) {
            for (Soldier s : troop.getSoldiers()) {
                power += s.getAttackStrength() * s.getAmount();
            }
        }
        return power;
    }

    @Override
    public Boolean carryOutBattle(Troop attacker, Troop defender) {
        List<Troop> att = new ArrayList<Troop>();
        att.add(attacker);

        List<Troop> def = new ArrayList<Troop>();
        def.add(defender);

        return carryOutBattle(att, def);
    }

}
