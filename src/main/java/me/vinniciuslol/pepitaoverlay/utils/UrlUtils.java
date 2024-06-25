package me.vinniciuslol.pepitaoverlay.utils;

//apache things
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlUtils {

    public static String getTextFromURL2(String _url) {
        String r = "";
        HttpURLConnection con = null;

        try {
            URL url = new URL(_url);
            con = (HttpURLConnection) url.openConnection();
            r = getTextFromConnection(con);
        } catch (IOException ignored) {
        } finally {
            if (con != null) {
                con.disconnect();
            }

        }

        return r;
    }

    public static String getTextFromURL(String _url) {
        String responseBody = "";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpGet httpGet = new HttpGet(_url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseBody = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseBody;
    }

    public static String getTextFromConnection(HttpURLConnection connection) {
        if (connection != null) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String result;
                try {
                    StringBuilder stringBuilder = new StringBuilder();

                    String input;

                    while ((input = bufferedReader.readLine()) != null) {
                        stringBuilder.append(input);
                    }

                    String res = stringBuilder.toString();
                    connection.disconnect();

                    result = res;
                } finally {
                    bufferedReader.close();
                }

                return result;
            } catch (Exception ignored) {
            }
        }

        return "";
    }
}
