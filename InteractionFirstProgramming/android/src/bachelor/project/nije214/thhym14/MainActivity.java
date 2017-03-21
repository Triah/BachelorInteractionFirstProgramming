package bachelor.project.nije214.thhym14;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends Activity {

    Button assembleButton;
    Button optionsButton;
    Button updateScriptsButton;
    //InterpretCode ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assembleButton = (Button) findViewById(R.id.assembleButton);
        optionsButton = (Button) findViewById(R.id.optionsButton);
        updateScriptsButton = (Button) findViewById(R.id.updateButton);
    }

    public void assembleButtonClick(View view){
        Intent assemble = new Intent(view.getContext(), AssembleObjects.class);
        startActivityForResult(assemble,0);
    }

    public void optionsButtonClick(View view){
        Intent options = new Intent(view.getContext(), Options.class);
        startActivityForResult(options,0);
    }

    public void updateButtonClick(View view){
        //MoveFilesToStorage mfts = new MoveFilesToStorage();
        //mfts.addFileToSdCard();
        /*FileGetterClient fgc = new FileGetterClient();
        fgc.ConnectAndSendMessage();
        InterpretCode ic = new InterpretCode();
        try {
            System.out.println(ic.getText());
        } catch (Exception ex){
            ex.printStackTrace();
        }*/
        Intent gameLaunch = new Intent(view.getContext(), AndroidLauncher.class);
        startActivityForResult(gameLaunch,0);
        //FindNewScriptFiles findNewScriptFiles = new FindNewScriptFiles();
        //findNewScriptFiles.recursiveSearch();
    }


}

