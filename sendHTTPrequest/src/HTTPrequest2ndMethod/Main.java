package HTTPrequest2ndMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    //Method 2: java.net.http.HttPClient
    public static void main(String[] args) {
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=  HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body).
//                thenAccept(System.out::println).join();//to parse below method calling different method thenApply
        thenApply(Main::parse).join();

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
