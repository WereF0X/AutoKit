package net.mcreator.autokit.procedures;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.level.BlockEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

@Mod.EventBusSubscriber
public class WriteJsonProcedure {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
	}

	public static void execute(LevelAccessor world, double x, double y, double z) {
		execute(null, world, x, y, z);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
		File file = new File("");
		com.google.gson.JsonObject mainObj = new com.google.gson.JsonObject();
		com.google.gson.JsonObject subObj = new com.google.gson.JsonObject();
		file = new File((FMLPaths.GAMEDIR.get().toString() + "/config"), File.separator + "test.json");
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		mainObj.addProperty("booleanValue", (world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z))));
		mainObj.addProperty("stringValue", ("Selected kit: " + "starter"));
		subObj.addProperty("numberValue", (world.getMaxLocalRawBrightness(BlockPos.containing(x, y, z))));
		mainObj.add("subObj", subObj);
		{
			Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
			try {
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(mainGSONBuilderVariable.toJson(mainObj));
				fileWriter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
}
