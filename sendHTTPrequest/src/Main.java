import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class Main {
    private static HttpURLConnection connection;

    //Method 1:java.net.HttpurlConnection to get a response from our remote server
    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/albums");
            connection = (HttpURLConnection) url.openConnection();

            //Request Setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            parse(responseContent.toString());
//            System.out.println(responseContent.toString());
            //System.out.println(status);//checking connection is established or not with 200 success status code
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.disconnect();

    }
    public static String parse(String responseBody){
        JSONArray albums=new JSONArray(responseBody);
        for(int i=0;i<albums.length();i++){
            JSONObject album=albums.getJSONObject(i);
            int id=album.getInt("id");
            int userid=album.getInt("userId");
            String title=album.getString("title");
            System.out.println(id+" "+title+" "+userid);

        }
        return null;
    }
}
