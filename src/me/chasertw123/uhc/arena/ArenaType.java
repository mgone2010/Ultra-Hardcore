package me.chasertw123.uhc.arena;


public enum ArenaType { 
	SOLO(1, true), 
	TWO(2, false), 
	THREE(3, false), 
	FOUR(4, false);

	private int playersPerTeam;
	private boolean autoTeam;

	private ArenaType(int playersPerTeam, boolean autoTeam) {
		this.playersPerTeam = playersPerTeam;
		this.autoTeam = autoTeam;
	}

	public Integer getPlayersPerTeam() {
		return playersPerTeam;
	}

	public boolean isAutoTeaming() {
		return autoTeam;
	}
}
