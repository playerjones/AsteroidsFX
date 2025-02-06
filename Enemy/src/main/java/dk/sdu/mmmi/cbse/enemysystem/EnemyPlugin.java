package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EnemyPlugin implements IGamePluginService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start(GameData gameData, World world) {
        // Schedule the enemy spawn every 15 seconds
        scheduler.scheduleAtFixedRate(() -> {
            Entity enemy = createEnemyShip(gameData);
            world.addEntity(enemy);
        }, 0, 15, TimeUnit.SECONDS);
    }

    private Entity createEnemyShip(GameData gameData) {
        // Create a new enemy
        return new Enemy();
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Removes all enemies from the world when stopping the plugin
        world.getEntities(Enemy.class).forEach(world::removeEntity);
    }
}
