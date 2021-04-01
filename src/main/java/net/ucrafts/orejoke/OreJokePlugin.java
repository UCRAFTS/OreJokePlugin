package net.ucrafts.orejoke;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import static org.bukkit.Material.DIAMOND_ORE;
import static org.bukkit.Material.IRON_ORE;

@Plugin(
        name = "OreJokePlugin",
        version = "1.0.0"
)
@Author(value = "oDD1 / Alexander Repin")
@Description(value = "Joke plugin add new actions for ore break")
public class OreJokePlugin extends JavaPlugin implements Listener
{

    private final String JOKE_NAME = "§dС 1 апреля!";


    @Override
    public void onEnable()
    {
        Bukkit.getPluginManager().registerEvents(this, this);
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!block.getType().toString().endsWith("_ORE")) {
            return;
        }

        int random = (int) (Math.random() * 100);
        Location location = block.getLocation();

        switch (block.getType()) {
            case DIAMOND_ORE:
            case IRON_ORE:
                if (random < 5) {
                    spawnJokeEntity(location);
                    break;
                }
            default:
                if (random > 75) {
                    event.setDropItems(false);
                    spawnJokeItem(location);
                }
        }

        if (random > 85) {
            player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_HURT, 1, 1);
        }
    }


    private void spawnJokeEntity(Location location)
    {
        int health = (int) (Math.random() * 32);
        Zombie entity = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        entity.setBaby();
        entity.setCustomName(this.JOKE_NAME);
        entity.setMaxHealth(health);
    }


    private void spawnJokeItem(Location location)
    {
        int amount = (int) (Math.random() * 16);
        ItemStack item = new ItemStack(Material.DIRT, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.JOKE_NAME);

        item.setItemMeta(meta);

        location.getWorld().dropItemNaturally(location, item);
    }
}
