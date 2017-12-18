package models;

public class MessageEntity {
	private int messageId;
	private int speaker;
	private String message;
	private String time;

	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public int getSpeaker() {
		return speaker;
	}
	public void setSpeaker(int speaker) {
		this.speaker = speaker;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}


}
