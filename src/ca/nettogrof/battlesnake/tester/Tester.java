/**
 * 
 */
package ca.nettogrof.battlesnake.tester;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author carl.lajeunesse
 *
 */
public class Tester {
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final Handler HANDLER = new Handler();
	private static final Logger LOG = LoggerFactory.getLogger(Tester.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static JsonNode sendMove (JsonNode req) {
		try {
			String snakeUrl = req.get("url").asText();

			String datajson = req.get("data").toString();
			System.out.println(snakeUrl);

			System.out.println(datajson);
			URL url;
			url = new URL(snakeUrl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = datajson.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			String reply;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				reply = response.toString();
			}
			System.out.println("Reply form snake " + reply);
			final Map<String, String> response = new HashMap<>();
			return JSON_MAPPER.readTree(reply);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static class Handler {
		
	}

}
