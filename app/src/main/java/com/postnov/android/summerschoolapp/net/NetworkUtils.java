package com.postnov.android.summerschoolapp.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by postnov on 21.02.2016.
 */
public class NetworkUtils
{
    /*
     * Обертка вокруг ниже стоящих методов.
     * Получаем поток, отдаем поток в метод для считывания строк.
     * В итоге получаем строку с JSON'ом
     */

    public static String getSourceString(String urlString) throws IOException
    {
        InputStream stream = null;
        String str = "";

        try {
            stream = downloadUrl(urlString);
            str = readStringFromStream(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return str;
    }

    /*
     * создаем соединение,
     * отправляем запрос,
     * возвращается поток
     */
    private static InputStream downloadUrl(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();
        return conn.getInputStream();
    }

    /*
     * Считываем данные из потока
     */
    private static String readStringFromStream(InputStream stream) throws IOException
    {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(reader);
        String read;

        while((read=br.readLine()) != null) {
            sb.append(read);
        }

        br.close();
        return sb.toString();
    }
}
