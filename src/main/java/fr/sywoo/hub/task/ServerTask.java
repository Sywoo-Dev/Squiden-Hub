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
        boolean needArena = false; boolean needKit = false;
        for(ServiceInfoSnapshot snapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices()){
            if(snapshot.getServiceId().getName().startsWith("Arena-")){
                if(ServiceInfoSnapshotUtil.getPlayers(snapshot) == null) continue;
                if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() >= 25){
                    needArena = true;
                } else if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() < 25){
                    needArena = false;
                }
            }
        }
        
        for(ServiceInfoSnapshot snapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices()){
            if(snapshot.getServiceId().getName().startsWith("ArenaKit-")){
                if(ServiceInfoSnapshotUtil.getPlayers(snapshot) == null) continue;
                if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() >= 25){
                    needKit = true;
                } else if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() < 25){
                    needKit = false;
                }
            }
        }
        
        if(needArena){
            LionSpigot.get().getServerManager().createServer("Arena");
        }
        
        if(needKit){
            LionSpigot.get().getServerManager().createServer("ArenaKit");
        }
    }
}
