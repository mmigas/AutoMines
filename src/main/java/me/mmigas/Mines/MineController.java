package me.mmigas.Mines;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MineController {
	private static ArrayList<Mine> mines = new ArrayList<>();


	public void createMine(String name) {
		mines.add(new Mine(name));
	}

	public void createMine(String name, World world, BlockVector3 minPosition, BlockVector3 maxPosition) {
		mines.add(new Mine(name, world, minPosition, maxPosition));
	}

	public void deleteMine(String name) {
		mines.remove(getMine(name));
	}

	public void setMineArea(String name, BlockVector3 minPosition, BlockVector3 maxPosition) {
		Mine mine = getMine(name);
		if (mine != null) {
			mine.setMinPosition(minPosition);
			mine.setMaxPosition(minPosition);
		}
	}

	public void sendInfo(String name, CommandSender commandSender){
		Mine mine = getMine(name);
		commandSender.sendMessage(name + "'s info: ");
		for (Map.Entry<Double, Material> entry: mine.getContent().entrySet()) {
			commandSender.sendMessage(" - " + entry.getValue() + ": " + entry.getKey());
		}

		commandSender.sendMessage("Flags: ");

		for(Flags flag: mine.getFlags()){
			commandSender.sendMessage(" - " + flag);
		}

	}

	public void changeBlock(String name, Material material, double percentage) {
		Mine mine = getMine(name);
		assert mine != null;
		mine.getContent().put(percentage,material);
	}

	public void resetMine(String name) {
		Objects.requireNonNull(getMine(name)).reset();
	}

	public void startResetTimer(String name, int time){
		Mine mine = getMine(name);
		assert mine != null;
		mine.getFlags().add(Flags.TimeReset);
		mine.startTimerCooldown(time);
	}

	public void stopResetTimer(String name){
		Mine mine = getMine(name);
		assert mine != null;
		mine.getFlags().remove(Flags.TimeReset);
		mine.stopTimerCooldown();
	}

	public boolean validatMine(String name) {
		for (Mine mine : mines) {
			if (mine.getName().equals(name))
				return true;
		}
		return false;
	}

	private Mine getMine(String name) {
		for (Mine m : mines) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
}
