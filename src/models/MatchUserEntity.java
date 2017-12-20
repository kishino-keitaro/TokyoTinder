package models;

public class MatchUserEntity {
	private int messageId;
	private int userId;
	private boolean read;
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	private String latest;
	private ProfileEntity ent;
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int message_id) {
		this.messageId = message_id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int user_id) {
		this.userId = user_id;
	}
	public String getLatest() {
		return latest;
	}
	public void setLatest(String latest) {
		this.latest = latest;
	}
	public ProfileEntity getEnt() {
		return ent;
	}
	public void setEnt(ProfileEntity ent) {
		this.ent = ent;
	}


}
