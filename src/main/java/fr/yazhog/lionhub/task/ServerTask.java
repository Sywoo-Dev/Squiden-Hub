package fr.yazhog.lionhub.task;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.yazhog.lionapi.spigot.LionSpigot;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerTask extends BukkitRunnable {

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
