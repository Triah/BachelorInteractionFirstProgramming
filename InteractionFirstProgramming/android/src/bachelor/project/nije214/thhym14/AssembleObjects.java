package bachelor.project.nije214.thhym14;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Nicolai on 20-03-2017.
 */

public class AssembleObjects extends Activity {


    ImageView imgView;
    Button chooseObjectButton;
    Button playButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assembly_screen);
        imgView = (ImageView) findViewById(R.id.testImageView);
        Drawable d = (Drawable) getResources().getDrawable(R.drawable.firstpicture);
        imgView.setImageDrawable(d);
        imgView.setOnTouchListener(new TouchListener());
        findViewById(R.id.assembly_screen).setOnDragListener(new DragListener());
        chooseObjectButton = (Button) findViewById(R.id.chooseObjectButton);
        playButton = (Button) findViewById(R.id.playButton);
    }

    public void chooseObjectButtonClick(View view) {
        Intent gallery = new Intent(view.getContext(), ImageGallery.class);
        startActivityForResult(gallery, 0);
    }


    public void playButtonClick(View view) {
        Intent intent = new Intent(view.getContext(), AndroidLauncher.class);
        AssembleObjects.this.startActivity(intent);
    }

    public void findScriptsButtonClick(View view) {

    }

    public ArrayList<String> getAllDrawables() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<String> finalArrayList = new ArrayList<>();
        for (Field f : R.drawable.class.getFields()) {
            String fString = f.toString();
            if (fString.endsWith("picture")) {
                String[] stringList = fString.split(" ");
                for (String s : stringList) {
                    stringArrayList.add(s);
                }
            }
        }
        for (int i = 0; i < stringArrayList.size(); i++) {
            if (stringArrayList.get(i).endsWith("picture")) {
                String[] stringList = stringArrayList.get(i).split("\\.");
                for (String s : stringList) {
                    if (s.endsWith("picture")) {
                        finalArrayList.add(s);
                    }
                }
            }
        }
        return finalArrayList;
    }


    public class DragListener implements View.OnDragListener {

        ViewGroup owner;
        int action;

        @Override
        public boolean onDrag(View view, DragEvent event) {
            action = event.getAction();
            owner = (ViewGroup) view.getParent();
            if (action == DragEvent.ACTION_DROP) {
                onActionDropEvent(view, event);
            } else if (action == DragEvent.ACTION_DRAG_LOCATION) {
                onActionDragEventMovePosition(view, event);
            }
            return true;
        }

        public void onActionDropEvent(View view, DragEvent event) {
            View v = (View) event.getLocalState();
            owner.removeAllViews();
            owner.addView(view);
            v.setVisibility(View.VISIBLE);
        }

        public void onActionDragEventMovePosition(View view, DragEvent event) {
            imgView.setX(event.getX() - (imgView.getHeight() / 2));
            imgView.setY(event.getY() - (imgView.getWidth() / 2));
            int array[] = new int[2];
            imgView.getLocationInWindow(array);
        }

    }

    public final class TouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }
}




