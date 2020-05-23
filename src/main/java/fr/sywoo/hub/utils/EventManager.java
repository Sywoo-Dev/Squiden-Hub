package fr.sywoo.hub.utils;

import org.bukkit.plugin.PluginManager;

import fr.sywoo.hub.Hub;
import fr.sywoo.hub.command.CommandJoin;
import fr.sywoo.hub.command.JumpCommand;
import fr.sywoo.hub.command.PermCommand;
import fr.sywoo.hub.command.RankCommand;
import fr.sywoo.hub.listeners.CancelledEvents;
import fr.sywoo.hub.listeners.EntityUse;
import fr.sywoo.hub.listeners.PlayerChat;
import fr.sywoo.hub.listeners.PlayerJoin;
import fr.sywoo.hub.listeners.PlayerMove;
import fr.sywoo.hub.listeners.PlayerQuit;
import fr.sywoo.hub.listeners.jump.Jump;
import fr.sywoo.hub.listeners.jump.JumpInteract;
import fr.sywoo.hub.task.MessageTask;
import fr.sywoo.hub.task.ScoreboardTask;
import fr.sywoo.hub.task.ServerTask;

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