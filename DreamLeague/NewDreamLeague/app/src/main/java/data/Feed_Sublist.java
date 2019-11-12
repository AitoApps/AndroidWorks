package data;
public class Feed_Sublist {
	private String teamid,teamname;
	public Feed_Sublist() {
	}
	public Feed_Sublist(String teamid, String teamname) {
		super();
		this.teamid=teamid;
		this.teamname=teamname;
	}

	public String getteamid() {
		return teamid;
	}
	public void setteamid(String teamid) {
		this.teamid=teamid;
	}

	public String getteamname() {
		return teamname;
	}
	public void setteamname(String teamname) {
		this.teamname=teamname;
	}
}
