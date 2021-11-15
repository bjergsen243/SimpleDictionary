package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Translator {
  // TODO: If you have your own Premium account credentials, put them down here:
  private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
  private static final String CLIENT_SECRET = "PUBLIC_SECRET";
  private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

  /** Entry Point */
  public static void main(String[] args) throws Exception {
    String text = "Hello world!";
    // Translated text: Xin chao the gioi!
    System.out.println("Translated text: " + translate("en", "vi", text));
  }

  public static String translate2(String fromLang, String toLang, String text) throws Exception {
    // TODO: Should have used a 3rd party library to make a JSON string from an object
    String jsonPayload =
        new StringBuilder()
            .append("{")
            .append("\"fromLang\":\"")
            .append(fromLang)
            .append("\",")
            .append("\"toLang\":\"")
            .append(toLang)
            .append("\",")
            .append("\"text\":\"")
            .append(text)
            .append("\"")
            .append("}")
            .toString();

    URL url = new URL(ENDPOINT);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(jsonPayload.getBytes());
    os.flush();
    os.close();

    int statusCode = conn.getResponseCode();
    // System.out.println("Status Code: " + statusCode);
    BufferedReader br =
        new BufferedReader(
            new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()));
    String output;
    String res = "";
    while ((output = br.readLine()) != null) {
      res += output;
    }
    conn.disconnect();
    return res;
  }
  public static String translate(String langFrom, String langTo, String text) throws IOException {
    // INSERT YOU URL HERE
    String urlStr = "https://script.google.com/macros/s/AKfycbwBBo3bAEC5aDzqDq1gYehfcUJShDDYU6hkpB4DJWl3kXBeURuq/exec" +
        "?q=" + URLEncoder.encode(text, "UTF-8") +
        "&target=" + langTo +
        "&source=" + langFrom;
    URL url = new URL(urlStr);
    StringBuilder response = new StringBuilder();
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestProperty("User-Agent", "Mozilla/5.0");
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    return response.toString();
  }
}
