package fr.yazhog.lionhub.utils;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;

public class PlayerUtils {

	public static int getUhcPlayers(){
		int i = 0;
		try{i = CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
				.filter(service -> service.getServiceId().getName().startsWith("UHC-")).mapToInt(serviceInfoSnapshot -> ServiceInfoSnapshotUtil.getPlayers(serviceInfoSnapshot).size()).sum();
		}catch(Exception e) {
			return 0;
		}
		return i;
	}
	
	public static int getKapturPlayers(){
		int i = 0;
		try{i = CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
				.filter(service -> service.getServiceId().getName().startsWith("Kaptur-")).mapToInt(serviceInfoSnapshot -> ServiceInfoSnapshotUtil.getPlayers(serviceInfoSnapshot).size()).sum();
		}catch(Exception e) {
			return 0;
		}
		return i;
	}

	public int getArenaPlayers(){
		int i = 0;
		try{i = CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
				.filter(service -> service.getServiceId().getName().startsWith("Arena-")).mapToInt(serviceInfoSnapshot -> ServiceInfoSnapshotUtil.getPlayers(serviceInfoSnapshot).size()).sum();
	}catch (Exception e) {
		return 0;
	}
		return i;
	}
	
	public static int getSkywarsPlayers(){
		int i = 0;
	try{i = CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
				.filter(service -> service.getServiceId().getName().startsWith("Skywars-")).mapToInt(serviceInfoSnapshot -> ServiceInfoSnapshotUtil.getPlayers(serviceInfoSnapshot).size()).sum();
	}catch (Exception e) {
		return 0;
	}
		return i;
	}


	public static int getPlayers(ServiceInfoSnapshot service){
		int server = 0;
		if(ServiceInfoSnapshotUtil.getPlayers(service) == null){
			return server;
		} else {
			server += ServiceInfoSnapshotUtil.getPlayers(service).size();
		}
		return server;
	}

}
