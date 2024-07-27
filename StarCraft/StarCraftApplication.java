import static view.InputView.*;
import static view.ResultView.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.PlayerUnits;
import model.ProtosFactory;
import model.TerranFactory;
import model.ZergFactory;
import util.RandomUnitGenerator;

public class StarCraftApplication {
    private static Map<Integer, String> RACES_MAP = new HashMap<>() {
        {
            put(1, "테란");
            put(2, "프로토스");
            put(3, "저그");
        }
    };

    public static void main(String[] args) {
        int playerRacesNum = inputRaces();
        int comRacesNum = RandomUnitGenerator.generate(1, RACES_MAP.size() + 1);
        PlayerUnits playerUnits = selectRaces(playerRacesNum);
        PlayerUnits comUnits = selectRaces(comRacesNum);

        while (!playerUnits.endGame() && !comUnits.endGame()) {
            System.out.println();
            System.out.println("적군 : " + RACES_MAP.get(comRacesNum));
            printUnits(comUnits);

            System.out.println();

            System.out.println("아군 : " + RACES_MAP.get(playerRacesNum));
            printUnits(playerUnits);

            System.out.println();
            System.out.println("공격을 수행할 아군 유닛과 공격할 적군 유닛을 선택하세요.");
            List<Integer> playerAttack = inputAttack();
            comUnits = comUnits.of(playerAttack.get(1), comUnits.playerUnit(playerAttack.get(1)).attackedUnit(playerUnits.playerUnit(playerAttack.get(0))));
            
            playerAttack.clear();
            
            if (playerUnits.endGame() || comUnits.endGame())
                break;

            List<Integer> comAttack = new ArrayList<>();
            comAttack.add(RandomUnitGenerator.generate(0, comUnits.size()));
            comAttack.add(RandomUnitGenerator.generate(0, playerUnits.size()));
            playerUnits = playerUnits.of(comAttack.get(1), playerUnits.playerUnit(comAttack.get(1)).attackedUnit(comUnits.playerUnit(comAttack.get(0))));

            comAttack.clear();
        }
        
        boolean isPlayerWin = (playerUnits.size() == 0) ? false : true;

        printResult(isPlayerWin);
    }
    
    private static PlayerUnits selectRaces(int racesNum) {
        if (racesNum == 1) {
            TerranFactory terranFactory = new TerranFactory();
            return PlayerUnits.from(terranFactory.createUnits());
        }
        if (racesNum == 2) {
            ProtosFactory protosFactory = new ProtosFactory();
            return PlayerUnits.from(protosFactory.createUnits());
        }

        ZergFactory zergFactory = new ZergFactory();
        return PlayerUnits.from(zergFactory.createUnits());
    }
}