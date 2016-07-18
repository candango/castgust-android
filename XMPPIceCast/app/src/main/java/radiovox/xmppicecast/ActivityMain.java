package radiovox.xmppicecast;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatMessageListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by Charline on 7/18/16.
 */

public class ActivityMain extends Activity {

    private String TAG = "MainActivity";

    private static final String HOST = "therealtalk.org";
    private static final int port = 5222;


    private String userName = "benhurmarques";
    private String password = "emnconde448";

    private ChatManager chatManager;
    private Chat newChat;
    private ChatMessageListener chatMessageListener;
    private ConnectionListener connectionListener;
    private XMPPTCPConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }


    @Override
    protected void onResume() {
        super.onResume();


        client();

    }

    private void client() {


        try {


            ConnectionConfiguration conf = new ConnectionConfiguration(HOST, port, HOST);
            conf.setDebuggerEnabled(true);

            conf.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            connection = new XMPPTCPConnection(conf);


            try {
                connection.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                connection.login(userName, password);
            } catch (Exception e) {
                e.printStackTrace();
            }


            ChatManager chatmanager = ChatManager.getInstanceFor(connection);
            Chat newChat = chatmanager.createChat("piraz@liberdade.digital", chatMessageListener);


            try {

                newChat.sendMessage("versão para o Git");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception se) {
            Log.e(TAG, se.toString());
        }


    }



}
