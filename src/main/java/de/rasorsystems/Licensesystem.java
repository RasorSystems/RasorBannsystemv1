package de.rasorsystems;

import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Licensesystem {

    public static boolean checkLicense(String license) throws IOException {
        URL url = new URL("http://45.81.234.72/check.php?license=" + license);
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder(128000);
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            int count;
            char[]data = new char[5000];
            while ((count = reader.read(data)) != -1){
                builder.append(data, 0, count);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        System.out.println(builder.toString());
        if(builder.toString().equalsIgnoreCase("true")){
            return true;
        }else {
            return false;
        }
    }
}
