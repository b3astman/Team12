package tcss450.uw.edu.team12;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import java.net.URL;

/**
 * An activity that displays an RSS feed displaying alerts/revisions to bus schedules.
 */
public class TransitAlertsActivity extends AppCompatActivity {

    public static final String ANY = "Any";
    private static final String URL = "https://public.govdelivery.com/topics/WAKCDOT_255/feed.rss";
    public static String sPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_alerts);

        setTitle(getResources().getText(R.string.transit_alerts));
        loadPage();
    }

    /**
     * Uses AsyncTask to download the XML feed from King County Metro (feed hosted on another domain).
     */
    public void loadPage() {

        new DownloadXmlTask().execute(URL);
    }

    /**
     * Implementation of AsyncTask used to download XML feed from King County Metro
     */
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return getResources().getString(R.string.xml_error);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Displays the HTML string in the UI via a WebView
            WebView myWebView = (WebView) findViewById(R.id.webView);
            myWebView.loadData(result, "text/html", null);


        }
    }

    /**
     * Uploads XML, parses it, and combines it with HTML markup. Returns HTML string.
     */
    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        TransitAlertsXmlParser transitAlertsXmlParser = new TransitAlertsXmlParser();
        List<TransitAlertsXmlParser.Item> items = null;
        String title = null;
        String url = null;

        // Checks whether the user set the preference to include summary text
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean pref = sharedPrefs.getBoolean("summaryPref", false);

        StringBuilder htmlString = new StringBuilder();

        try {
            stream = downloadUrl(urlString);
            items = transitAlertsXmlParser.parse(stream);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        // TransitAlertsXmlParser returns a List (called "items") of Item objects.
        // Each Item object represents a single post in the XML feed.
        // This section processes the items list to combine each entry with HTML markup.
        // Each item is displayed in the UI as a link
        for (TransitAlertsXmlParser.Item item : items) {
            htmlString.append(item.pubDate);
            htmlString.append("<p><a href='");
            htmlString.append(item.link);
            htmlString.append("'>" + item.title + "</a></p>");

        }
        return htmlString.toString();
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     */
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

}
