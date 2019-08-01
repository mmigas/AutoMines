package me.mmigas.Mines;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.mmigas.AutoMines;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

class Mine {
	private String name;
	private BlockVector3 minPosition, maxPosition;
	private World world;

	private TreeMap<Double, Material> content = new TreeMap<>();

	private ArrayList<Flags> flags = new ArrayList<>();

	Mine(String name) {
		this.name = name;
		content.put(1.00, Material.AIR);
	}

	Mine(String name, World world, BlockVector3 minPosition, BlockVector3 maxPosition) {
		this.name = name;
		this.minPosition = minPosition;
		this.maxPosition = maxPosition;
		this.world = world;
		content.put(1.00, Material.AIR);
	}

	void reset() {

		CuboidRegion area = new CuboidRegion(minPosition, maxPosition);

		ArrayList<Material> materialsGenerated = generatedMaterialsList();

		int counter = 0;
		for (int z = 0; z < area.getLength(); z++) {
			for (int y = 0; y < area.getHeight(); y++) {
				for (int x = 0; x < area.getWidth(); x++) {
					int finalX = minPosition.getX() + x;
					int finalY = minPosition.getY() + y;
					int finalZ = minPosition.getZ() + z;

					world.getBlockAt(new Location(world, (double) finalX, (double) finalY, (double) finalZ)).setType(materialsGenerated.get(counter));
					counter++;
				}
			}
		}
	}

	private ArrayList<Material> generatedMaterialsList() {
		ArrayList<Material> materials = new ArrayList<>();

		Random random = new Random();
		for (int i = 0; i < mineSize(); i++) {
			int probabilityGenerated = random.nextInt(1);
			AutoMines.getInstance().getLogger().info(probabilityGenerated + "");
			for (Map.Entry<Double, Material> entry : content.entrySet()) {
				if (probabilityGenerated < entry.getKey()) {
					materials.add(entry.getValue());
					break;
				}
			}
		}

		return materials;
	}

	private BukkitRunnable resetTask;

	void startTimerCooldown(int periodInSec) {
		resetTask = (BukkitRunnable) new BukkitRunnable() {
			@Override
			public void run() {
				reset();
			}
		}.runTaskTimerAsynchronously(AutoMines.getInstance(), 0, periodInSec);
	}

	void stopTimerCooldown() {
		Bukkit.getScheduler().cancelTask(resetTask.getTaskId());
	}

	private int mineSize(){
		CuboidRegion area = new CuboidRegion(minPosition, maxPosition);
		return area.getArea();
	}

	String getName() {
		return name;
	}

	TreeMap<Double, Material> getContent() {
		return content;
	}

	ArrayList<Flags> getFlags() {
		return flags;
	}

	void setMinPosition(BlockVector3 minPosition) {
		this.minPosition = minPosition;
	}

	void setMaxPosition(BlockVector3 maxPosition) {
		this.maxPosition = maxPosition;
	}


}

