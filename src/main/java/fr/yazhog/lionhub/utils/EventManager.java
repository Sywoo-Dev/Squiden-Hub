package fr.yazhog.lionhub.utils;

import org.bukkit.plugin.PluginManager;

import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.command.CommandJoin;
import fr.yazhog.lionhub.command.JumpCommand;
import fr.yazhog.lionhub.command.PermCommand;
import fr.yazhog.lionhub.command.RankCommand;
import fr.yazhog.lionhub.listeners.CancelledEvents;
import fr.yazhog.lionhub.listeners.EntityUse;
import fr.yazhog.lionhub.listeners.PlayerChat;
import fr.yazhog.lionhub.listeners.PlayerJoin;
import fr.yazhog.lionhub.listeners.PlayerMove;
import fr.yazhog.lionhub.listeners.PlayerQuit;
import fr.yazhog.lionhub.listeners.jump.Jump;
import fr.yazhog.lionhub.listeners.jump.JumpInteract;
import fr.yazhog.lionhub.task.MessageTask;
import fr.yazhog.lionhub.task.ScoreboardTask;
import fr.yazhog.lionhub.task.ServerTask;

public class EventManager {

    private Hub hub;

    public EventManager(Hub hub) {
        this.hub = hub;
    }

    public void register(PluginManager pm) {
        pm.registerEvents(new PlayerJoin(hub), hub);
        pm.registerEvents(new EntityUse(), hub);
        pm.registerEvents(new PlayerQuit(hub), hub);
        pm.registerEvents(new CancelledEvents(), hub);
        pm.registerEvents(new PlayerMove(), hub);
        pm.registerEvents(new Jump(hub), hub);
        pm.registerEvents(new JumpInteract(), hub);
        pm.registerEvents(new PlayerChat(), hub);
        new ScoreboardTask(hub).runTaskTimer(hub, 20, 20);
        new ServerTask().runTaskTimer(hub, 20*60, 0);
        new MessageTask().runTaskTimer(hub, 20*60, 20*60);
        hub.getCommand("rank").setExecutor(new RankCommand());
        hub.getCommand("jump").setExecutor(new JumpCommand());
        hub.getCommand("perm").setExecutor(new PermCommand());
        hub.getCommand("join").setExecutor(new CommandJoin());
    }
}