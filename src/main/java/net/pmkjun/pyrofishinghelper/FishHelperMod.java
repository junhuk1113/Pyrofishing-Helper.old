package net.pmkjun.pyrofishinghelper;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.pmkjun.pyrofishinghelper.item.FishItems;
import org.slf4j.Logger;

public class FishHelperMod implements ModInitializer {
	public static final String MODID = "pyrofishinghelper";

	public static final Logger LOGGER = LogUtils.getLogger();

	public FishHelperClient client;

	@Override
	public void onInitialize() {
		this.client = new FishHelperClient();
		this.client.init();
		FishItems.register();
	}
}