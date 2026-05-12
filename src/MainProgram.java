import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * change files
→ git add .
→ git commit -m "message"
→ git push
 */

public class MainProgram {
	private LogParser parser;
	private DetectionEngine detect;

	public MainProgram() {
		this.detect = new DetectionEngine();
	}

	public static void main(String[] args) throws IOException {
		MainProgram main = new MainProgram();
		main.start();
	}

	public void start() throws IOException {
		String demoPath = "C:/Users/smithbd/Downloads/exampleLogformat.txt";
		Path path = Paths.get(demoPath);
		this.parser = new LogParser(path);
		List<LogEntry> list = this.parser.listCopy();
		StringBuilder result = new StringBuilder();
		for (LogEntry entry : list) {
			this.detect.addFailedLogins(entry);
			System.out.println(entry.getTimestamp() + " " + "IP=" + entry.getIp() + " " + "USER=" + entry.getUsername()
					+ " " + "ACTION=" + entry.getAction() + " " + "STATUS=" + entry.getStatus());
			System.out.println(this.detect.failedLogin(entry));
		}
		for (String ip : this.detect.getSusCopy()) {
			result.append(ip + " exceeded failed login treshold.");
			result.append("\n");
		}
		System.out.println(this.detect.allFailedLogins());
		System.out.println("Suspicious IPs:");
		if (result.length() == 0) {
			System.out.println("NONE");
		} else {
			System.out.println(result.toString());
		}

	}
}
