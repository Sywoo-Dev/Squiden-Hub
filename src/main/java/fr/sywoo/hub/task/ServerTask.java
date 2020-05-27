package fr.sywoo.hub.task;

import org.bukkit.scheduler.BukkitRunnable;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.sywoo.api.spigot.LionSpigot;

public class ServerTask extends BukkitRunnable {

    @SuppressWarnings("deprecation")
	@Override
    public void run() {
        boolean needStart = false;
        for(ServiceInfoSnapshot snapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices()){
            if(snapshot.getServiceId().getName().startsWith("Arena-")){
                if(ServiceInfoSnapshotUtil.getPlayers(snapshot) == null) continue;
                if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() >= 25){
                    needStart = true;
                } else if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() < 25){
                    needStart = false;
                    return;
                }
            }
        }
        if(needStart){
            LionSpigot.get().getServerManager().createServer("Arena");
        }
    }
}
