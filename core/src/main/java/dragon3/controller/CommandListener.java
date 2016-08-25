package dragon3.controller;

public interface CommandListener {
	public void executeFKeyCommand(int n, boolean shiftDown);
	public void executeMenuCommand(String command);
}
