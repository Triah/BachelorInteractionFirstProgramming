package bachelor.project.nije214.thhym14;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by Nicolai on 02-03-2017.
 */

public class FileGetterClient {
    private Socket client;
    private PrintWriter printWriter;
    private String message;
    private static InputStream in;

    /*
     * ideer:
     * 1 - Send en command gennem netværket og exekver på serveren inden en serializering af filerne sendes tilbage som bytes og genopbygges på android
     * 2 - modtag et objekt af en klasse, kræver meget prediction // svær men awesome løsning
     * 3 - skab en fil ud fra den string der sendes.
     * 4 - lav en måde at skabe behaviour ud fra den string der sendes
     * 5 - compile på android dynamisk ved at append stringen ind i et pre-defineret dokument // dynamisk java fil spawn possibru?
     */
    public void ConnectAndSendMessage(){
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    File f = new File("/storage/sdcard/InteractionFirstScripts/" + "testFile.txt");
                    FileWriter fw = new FileWriter(f);
                    BufferedWriter bw = new BufferedWriter(fw);
                    Socket client = new Socket("10.0.2.2", 12345);
                    byte[] b = new byte[1024];
                    int read;
                    in = client.getInputStream();
                    while((read = in.read())!= -1) {
                        bw.write(Character.toString((char)read));
                    }
                    bw.flush();
                    bw.close();

                } catch (UnknownHostException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();


    }
}
