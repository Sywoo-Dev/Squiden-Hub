package fr.sywoo.hub.utils;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;

public class PlayerUtils {

	
	@SuppressWarnings("deprecation")
	public static int getGamePlayer(String str){
		int i = 0;
		try{i = CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
				.filter(service -> service.getServiceId().getName().startsWith(str + "-")).mapToInt(serviceInfoSnapshot -> ServiceInfoSnapshotUtil.getPlayers(serviceInfoSnapshot).size()).sum();
		}catch(Exception e) {
			return 0;
		}
		return i;
	}

	@SuppressWarnings("deprecation")
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
