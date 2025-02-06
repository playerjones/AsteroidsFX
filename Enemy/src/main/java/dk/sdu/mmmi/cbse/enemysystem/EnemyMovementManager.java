package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class EnemyMovementManager implements IEntityProcessingService {

    private static final double ENEMY_SPEED = 0.5;
    private static final double TURN_PROBABILITY = 0.02; // 2% chance for at ændre retning per frame
    private static final double MAX_TURN_ANGLE = 5;      // Maksimal drejevinkel i grader

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {

            // Tilfældig ændring af retning (sker sjældent pga. TURN_PROBABILITY)
            if (Math.random() < TURN_PROBABILITY) {
                double randomTurn = (Math.random() - 0.5) * 2 * MAX_TURN_ANGLE; // ±5 grader
                enemy.setRotation(enemy.getRotation() + randomTurn);
            }

            // Beregn bevægelse baseret på retning
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));

            enemy.setX(enemy.getX() + changeX * ENEMY_SPEED);
            enemy.setY(enemy.getY() + changeY * ENEMY_SPEED);

            // Skærm-wrap logik
            if (enemy.getX() < 0) {
                enemy.setX(gameData.getDisplayWidth());
            }
            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(0);
            }
            if (enemy.getY() < 0) {
                enemy.setY(gameData.getDisplayHeight());
            }
            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(0);
            }
        }
    }
}
