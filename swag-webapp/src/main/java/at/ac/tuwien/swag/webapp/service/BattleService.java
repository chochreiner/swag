package at.ac.tuwien.swag.webapp.service;

import java.util.List;

import at.ac.tuwien.swag.model.domain.Troop;

public interface BattleService {

    public Boolean carryOutBattle(List<Troop> attacker, List<Troop> defender);

    public Boolean carryOutBattle(Troop attacker, Troop defender);

}
