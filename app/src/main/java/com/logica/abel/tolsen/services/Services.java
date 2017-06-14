package com.logica.abel.tolsen.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abel on 13/06/17.
 */
public class Services extends AsyncTask<String, String, String> {

    private Activity activity;
    private final String CONTENT_TYPE = "Content-Type";
    private final String APPLICATION_JSON = "application/json";
    private final String AUTHORIZATION = "Authorization";
    private final String BASIC_AUTH = "api2016:pasmo123";
    private final int RESULT_OK = 200;
    private String method;
    private int currentStatusCode;
    private String model;
    private String url;
    private final String POST = "POST";
    private final String GET = "GET";
    private final String DELETE = "DELETE";
    private final String PUT = "PUT";
    private ServiceInterface serviceInterface;
    private String token;
    private String uuid;

    public Services(Activity activity, String url, String method, ServiceInterface serviceInterface, String token, String uuid) {
        this.activity = activity;
        this.url = url;
        this.method = method;
        this.serviceInterface = serviceInterface;
        this.token = token;
        this.uuid = uuid;
    }

    public Services(Activity activity, String url, String model, String method, ServiceInterface serviceInterface) {
        this.activity = activity;
        this.url = url;
        this.model = model;
        this.method = method;
        this.serviceInterface = serviceInterface;
    }

    @Override
    protected String doInBackground(String... strings) {

        if (method.equals(GET)) {
            return getService();
        }

        if (method.equals(DELETE)) {
            return deleteService();
        }

        if (method.equals(PUT)) {
            return putService();
        }

        return postService();

    }

    private String getService() {
        StringBuilder stringBuilder = null;
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET);
            connection.setDoInput(true);
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            connection.setRequestProperty(AUTHORIZATION, "Basic " + Base64.encodeToString(BASIC_AUTH.getBytes(), Base64.NO_WRAP));
            connection.setRequestProperty("lang", "es");
            //connection.setConnectTimeout(30);

            if (token != null) {
                connection.setRequestProperty("token", token);
            }

            if (uuid != null) {
                connection.setRequestProperty("uuid", uuid);
            }

            currentStatusCode = connection.getResponseCode();

            if (currentStatusCode == RESULT_OK) {

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8);
                String line;
                stringBuilder = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String postService() {

        StringBuilder stringBuilder = null;
        URL url = null;
        try {
            url = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert url != null;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(POST);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            connection.setRequestProperty(AUTHORIZATION, "Basic " + Base64.encodeToString(BASIC_AUTH.getBytes(), Base64.NO_WRAP));
            connection.setRequestProperty("lang", "es");

            if (model != null) {
                connection.getOutputStream().write(model.getBytes("UTF-8"));
            }

            currentStatusCode = connection.getResponseCode();

            if (RESULT_OK == currentStatusCode) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!= null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String deleteService() {

        StringBuilder stringBuilder = null;
        URL url = null;
        try {
            url = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert url != null;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(DELETE);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            connection.setRequestProperty(AUTHORIZATION, "Basic " + Base64.encodeToString(BASIC_AUTH.getBytes(), Base64.NO_WRAP));
            connection.setRequestProperty("lang", "es");

            if (model != null) {
                connection.getOutputStream().write(model.getBytes("UTF-8"));
            }

            currentStatusCode = connection.getResponseCode();

            if (RESULT_OK == currentStatusCode) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!= null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String putService() {

        StringBuilder stringBuilder = null;
        URL url = null;
        try {
            url = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert url != null;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(PUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            connection.setRequestProperty(AUTHORIZATION, "Basic " + Base64.encodeToString(BASIC_AUTH.getBytes(), Base64.NO_WRAP));
            connection.setRequestProperty("lang", "es");

            if (model != null) {
                connection.getOutputStream().write(model.getBytes("UTF-8"));
            }

            currentStatusCode = connection.getResponseCode();

            if (RESULT_OK == currentStatusCode) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine())!= null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        /*ApiResponseHandler apiResponse = new ApiResponseHandler(activity, response);

        if (apiResponse.isValid()) {
            serviceInterface.onSuccess(
                    apiResponse.getResponse()
            );
            return;
        }

        if (apiResponse.isInvalidSession()) {
            serviceInterface.onSessionExpired(
                    GenericAlerts.expiredSessionAlert(activity)
            );
            return;
        }

        serviceInterface.onFailure(
                apiResponse.getError()
        );*/

    }
}
