package club.mcams.carpet;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class AmsServerPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
    }
}
