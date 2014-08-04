package me.chasertw123.uhc.commands;

import me.chasertw123.uhc.Main;
import me.chasertw123.uhc.arena.Arena.GameState;
import me.chasertw123.uhc.teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCmd implements CommandExecutor {

	private Main plugin;

	public TeamCmd(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("Team")) {

			if (!(sender instanceof Player)) {
				plugin.sendConsole("You must be a player to use that command!");
				return true;
			}

			Player p = (Player) sender;

			/*
			 * You can make the permissions.
			 * 
			 * Try to make them like.
			 * 
			 * Example: uhc.team.create
			 */

			if (!(plugin.getA().getGameState() == GameState.LOBBY || plugin.getA().getGameState() == GameState.STARTING)) {
				plugin.sendMessage(p, ChatColor.RED + "Cannot do this command when game is " + plugin.getA().getGameState());
				return false;
			}

			if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				if (!(p.hasPermission("uhc.team.help")))
					plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				else {
					if (p.hasPermission("uhc.team.leave"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team leave - Leave your team.");
					if (p.hasPermission("uhc.team.players"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team players [Team] - Show a list of players in a team, or your own if not specified.");
					if (p.hasPermission("uhc.team.invite"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team invite - Invite a player to your team.");
					if (p.hasPermission("uhc.team.deny"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team deny <Team> - Deny an invitement.");
					if (p.hasPermission("uhc.team.accept"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team accept <Team> - Accept an invitement.");
					if (p.hasPermission("uhc.team.disband"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team disband - Disband your team.");
					if (p.hasPermission("uhc.team.create"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team create <Name> - Create a team with name.");
					if (p.hasPermission("uhc.team.join"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team join - Forcefully join a team.");
					if (p.hasPermission("uhc.team.revoke"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team revoke <Player> - Cancel an invitement.");
					if (p.hasPermission("uhc.team.kick"))
						plugin.sendMessage(p, ChatColor.GREEN + "/team kick <Player> - Kick a player out of your team D:");
				}
			}

			else {

				if (args[0].equalsIgnoreCase("leave")) {
					if (p.hasPermission("uhc.team.leave"))
						if (args.length == 1)
							if (plugin.getTm().getTeam(p) != null) {
								Team t = plugin.getTm().getTeam(p);
								if (t.getCreator().equalsIgnoreCase(p.getName())) 
									plugin.sendMessage(p, "You cannot leave your team as the creator, disband it.");
								else {
									t.removePlayer(p.getName(), true);
									plugin.sendMessage(p, "You have left your team.");
								}
							} else
								plugin.sendMessage(p, "You are not in a team.");
						else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}

				else if (args[0].equalsIgnoreCase("players")) {
					if (p.hasPermission("uhc.team.players"))
						if (args.length == 1)
							if (plugin.getTm().getTeam(p) != null) {
								Team t = plugin.getTm().getTeam(p);
								if (t.getMembers().size() != 0) {
									String members = "";
									for (String w : t.getMembers()) {
										if (w != t.getMembers().get(0))
											members = members + ", " + w ;
										else
											members = w;
									}

									if (members.contains(", ")) {
										StringBuilder builder = new StringBuilder(members);
										builder.replace(members.lastIndexOf(","), members.lastIndexOf(",") + 1, " and" );
										plugin.sendMessage(p, "Your team contains the players " + builder.toString());
									} else
										plugin.sendMessage(p, "Your team contains the players " + members);
								}
							} else
								plugin.sendMessage(p, "You are not in a team.");
						else if (args.length == 2) {
							Team team = null;
							for (Team t : Team.teamObjects) {
								if (t.getName().equalsIgnoreCase(args[1])) 
									team = t;
								else if (t.getCreator().equalsIgnoreCase(args[1]))
									team = t;
								else if (t.getMembers().contains(args[1]))
									team = t;
							}

							if (team != null) {
								if (team.getMembers().size() != 0) {
									String members = "";
									for (String w : team.getMembers()) {
										if (w != team.getMembers().get(0))
											members = members + ", " + w ;
										else
											members = w;
									}

									if (members.contains(", ")) {
										StringBuilder builder = new StringBuilder(members);
										builder.replace(members.lastIndexOf(","), members.lastIndexOf(",") + 1, " and" );
										plugin.sendMessage(p, "That team contains the players " + builder.toString());
									} else
										plugin.sendMessage(p, "That team contains the players " + members);
								}
							} else 
								plugin.sendMessage(p, ChatColor.RED + "Cannot find that team!");
						} else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}

				else if (args[0].equalsIgnoreCase("invite")) {
					if (p.hasPermission("uhc.team.invite"))
						if (args.length == 2) {
							if (Bukkit.getPlayerExact(args[1]) != null) 
								if (plugin.getTm().getTeam(p) != null) 
									if (plugin.getTm().getTeam(p).getMembers().size() + plugin.getTm().getTeam(p).getInvites().size() >= plugin.getA().getArenaType().getPlayersPerTeam())
										plugin.sendMessage(p, ChatColor.RED + "Your team is full, revoke an invite or kick a player!");
									else
										if (plugin.getTm().getTeam(p).getCreator().equalsIgnoreCase(p.getName())) {
											Player player = Bukkit.getPlayerExact(args[1]);
											if (plugin.getTm().getTeam(p).getInvites().contains(args[1]) || plugin.getTm().getTeam(p).getMembers().contains(args[1]))
												if (plugin.getTm().getTeam(player) != null) {
													plugin.getTm().getTeam(p).addInvite(player.getName(), p.getName());
													plugin.sendMessage(player, "You got invited by " + p.getName() + " to join team " + plugin.getTm().getTeam(p).getName() + "!");
												} else
													plugin.sendMessage(p, ChatColor.RED + "This player is already in a team!");
											else
												plugin.sendMessage(p, ChatColor.RED + "This player is already invited by your team or already in your team!");
										} else
											plugin.sendMessage(p, ChatColor.RED + "You are not the creator of your team!");
								else
									plugin.sendMessage(p, ChatColor.RED + "You are not in a team!");
							else
								plugin.sendMessage(p, ChatColor.RED + "Cannot find player " + args[1] + "! Typed it correctly? Caps matter.");
						}
						else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");

				}

				else if (args[0].equalsIgnoreCase("deny")) {
					if (p.hasPermission("uhc.team.deny"))
						if (args.length == 2){
							Team team = null;
							for (Team t : Team.teamObjects) {
								if (t.getName().equalsIgnoreCase(args[1])) 
									team = t;
								else if (t.getCreator().equalsIgnoreCase(args[1]))
									team = t;
								else if (t.getMembers().contains(args[1]))
									team = t;
							}

							if (team != null) {
								if (team.getInvites().contains(p.getName())) {
									team.removeInvite(p.getName());
									plugin.sendMessage(p, "You have denied " + team.getName() + " team invitement!");
								} else
									plugin.sendMessage(p, ChatColor.RED + "You are not invited in the " + team.getName() + " team!");
							} else 
								plugin.sendMessage(p, ChatColor.RED + "Cannot find team with name/member " + ChatColor.GOLD + args[1] + "!");

						}else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}

				else if (args[0].equalsIgnoreCase("accept")) {
					if (p.hasPermission("uhc.team.accept"))
						if (args.length == 2) {
							Team team = null;
							for (Team t : Team.teamObjects) {
								if (t.getName().equalsIgnoreCase(args[1])) 
									team = t;
								else if (t.getCreator().equalsIgnoreCase(args[1]))
									team = t;
								else if (t.getMembers().contains(args[1]))
									team = t;
							}

							if (team != null) {
								if (team.getInvites().contains(p.getName())) {
									team.addPlayer(p.getName());
									plugin.sendMessage(p, "You have joined the " + team.getName() + " team!");
								} else
									plugin.sendMessage(p, ChatColor.RED + "You are not invited in the " + team.getName() + " team!");
							} else 
								plugin.sendMessage(p, ChatColor.RED + "Cannot find team with name/member " + ChatColor.GOLD + args[1] + "!");

						}else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}

				else if (args[0].equalsIgnoreCase("disband")) {
					if (p.hasPermission("uhc.team.disband"))
						if (args.length == 1)
							if (plugin.getTm().getTeam(p) != null)
								if (plugin.getTm().getTeam(p).getCreator() == p.getName()) {
									plugin.getTm().getTeam(p).sendMessage("Your team got disbanded by " + p.getName() + " D:");
									Team.teamObjects.remove(plugin.getTm().getTeam(p));
								} else
									plugin.sendMessage(p, ChatColor.RED + "You are not creator of your team");
							else
								plugin.sendMessage(p, ChatColor.RED + "You don't have a team to disband!");
						else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}

				else if (args[0].equalsIgnoreCase("create")) {
					if (p.hasPermission("uhc.team.create"))
						if (args.length == 2) {
							for (Team t : Team.teamObjects) {
								if (t.getName().equalsIgnoreCase(args[1])) {
									plugin.sendMessage(p, ChatColor.RED + "This teamname already exists!");
									return false;
								} else if (t.getCreator().equalsIgnoreCase(args[1])) {
									plugin.sendMessage(p, ChatColor.RED + "You already have a team, do /team disband to create a new one!");
									return false;
								} else if (t.getMembers().contains(args[1])) {
									plugin.sendMessage(p, ChatColor.RED + "You are already in a team, do /team leave to create a new one!");
									return false;
								} 
							}

							plugin.getTm().createTeam(p, args[1]);
						}
						else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}

				else if (args[0].equalsIgnoreCase("join")) {
					if (p.hasPermission("uhc.team.join"))
						if (args.length == 2) {
							Team team = null;
							for (Team t : Team.teamObjects) {
								if (t.getName().equalsIgnoreCase(args[1])) 
									team = t;
								else if (t.getCreator().equalsIgnoreCase(args[1]))
									team = t;
								else if (t.getMembers().contains(args[1]))
									team = t;
							}

							if (team != null)
								team.addPlayer(p.getName());
							else
								plugin.sendMessage(p, ChatColor.RED + "Cannot find team with name/member " + ChatColor.GOLD + args[1] + "!");

						} else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}


				else if (args[0].equalsIgnoreCase("kick")) {
					if (p.hasPermission("uhc.team.kick"))
						if (args.length == 2)
							if (Bukkit.getPlayerExact(args[1]) != null) 
								if (plugin.getTm().getTeam(p) != null) 
									if (plugin.getTm().getTeam(Bukkit.getPlayerExact(args[1])) == plugin.getTm().getTeam(p))
										if (plugin.getTm().getTeam(p).getCreator() == p.getName()) {
											plugin.getTm().getTeam(p).removePlayer(args[1], true);
											plugin.getTm().getTeam(p).sendMessage(p.getName() + " kicked " + args[1] + " out of the team! D:");
										} else
											plugin.sendMessage(p, ChatColor.RED + "You are not creator of this team!");
									else
										plugin.sendMessage(p, ChatColor.RED + "Player is not in the same team as you!");
								else
									plugin.sendMessage(p, ChatColor.RED + "You are not in a team");
							else 
								plugin.sendMessage(p, ChatColor.RED + "Cannot find this player");
						else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}


				else if (args[0].equalsIgnoreCase("revoke")) {
					if (p.hasPermission("uhc.team.revoke"))
						if (args.length == 2)
							if (Bukkit.getPlayerExact(args[1]) != null) 
								if (plugin.getTm().getTeam(p) != null) 
									if (plugin.getTm().getTeam(p).getInvites().contains(args[1]))
										if (plugin.getTm().getTeam(p).getCreator() == p.getName()) {
											plugin.getTm().getTeam(p).removeInvite(args[1]);
											plugin.getTm().getTeam(p).sendMessage(p.getName() + " removed the invite of " + args[1]);
											plugin.sendMessage(Bukkit.getPlayerExact(args[1]), p.getName() + " revoked your invitement");
										} else
											plugin.sendMessage(p, ChatColor.RED + "You are not creator of this team!");
									else
										plugin.sendMessage(p, ChatColor.RED + "Player was not invited in your team!");
								else
									plugin.sendMessage(p, ChatColor.RED + "You are not in a team");
							else 
								plugin.sendMessage(p, ChatColor.RED + "Cannot find this player");
						else
							plugin.sendMessage(p, ChatColor.RED + "Invalid amount of arguments!");
					else
						plugin.sendMessage(p, ChatColor.RED + "You don't have permission for this command.");
				}
				else {
					plugin.sendMessage(p, ChatColor.GOLD + args[0] + ChatColor.RED + " is an unknown argument!");
					return true;
				}
			}
		}

		return true;
	}
}
