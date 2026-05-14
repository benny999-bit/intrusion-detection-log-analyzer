import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class Testing {
	public Testing() {
		
	}
	
	public static void main(String[] args) {
		ZonedDateTime a = ZonedDateTime.of(
		        2025, 1, 1,
		        10, 0, 0, 0,
		        ZoneId.systemDefault());

		ZonedDateTime b = ZonedDateTime.of(
		        2025, 1, 1,
		        10, 2, 59, 0,
		        ZoneId.systemDefault());

		long minutes = a.until(b, ChronoUnit.SECONDS);

		System.out.println(minutes); // 2
	}
}
