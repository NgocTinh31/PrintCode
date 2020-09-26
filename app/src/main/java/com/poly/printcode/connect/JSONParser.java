//package com.poly.printcode.connect;
//
//import android.net.Uri;
//import android.util.Log;
//
//import org.json.JSONObject;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class JSONParser {
//    static InputStream is = null;
//    static JSONObject jObj = null;
//    static String json = "";
//
//    public JSONParser() {
//    }
//
//    public JSONObject makeHttpRequest(String duongdan, String method, List<HashMap<String, String>> params){
//        String key = "";
//        String value = "";
//        try {
//            if (method.equals("POST")){
//                URL url = new URL(duongdan);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setDoOutput(true);
//                urlConnection.setDoInput(true);
//                Uri.Builder builder = new Uri.Builder();
//                int count = params.size();
//                for (int i = 0; i < count; i++){
//                    for (Map.Entry<String, String> values: params.get(i).entrySet()){
//                        key = values.getKey();
//                        value = values.getValue();
//                    }
//                    builder.appendQueryParameter(key, value);
//                }
//                String query = builder.build().getEncodedQuery();
//                OutputStream outputStream = urlConnection.getOutputStream();
//                OutputStreamWriter outputStreamWriter = new
//                        OutputStreamWriter(outputStream);
//                BufferedWriter bufferedWriter = new
//                        BufferedWriter(outputStreamWriter);
//                bufferedWriter.write(query);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStreamWriter.close();
//                outputStream.close();
//                urlConnection.connect();
//                is = new BufferedInputStream(urlConnection.getInputStream());
//            } else if (method.equals("GET")){
//                Uri.Builder builder = new Uri.Builder();
//                int count = params.size();
//                for (int i = 0; i < count; i ++){
//                    for (Map.Entry<String, String> values:
//                            params.get(i).entrySet()){
//                        key = values.getKey();
//                        value = values.getValue();
//                    }
//                    builder.appendQueryParameter(key, value);
//                }
//                duongdan += builder.toString();
//                URL url = new URL(duongdan);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                is = new BufferedInputStream(conn.getInputStream());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            BufferedReader bufferedReader = new BufferedReader( new
//                    InputStreamReader(is, "utf-8"),8);
//            StringBuffer sb = new StringBuffer();
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null){
//                sb.append(line + "\n");
//            }
//            is.close();
//            json = sb.toString();
//        } catch (Exception e){
//            Log.e("Buffer error: ", "Error converting result" + e.toString());
//        }
//        try {
//            jObj = new JSONObject(json);
//        } catch (Exception e){
//            Log.e("JSON Parser", "Error parsing data" + e.toString());
//        }
//        return jObj;
//    }
//}
