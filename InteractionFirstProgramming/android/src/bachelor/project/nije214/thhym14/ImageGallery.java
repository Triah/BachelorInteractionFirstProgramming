package bachelor.project.nije214.thhym14;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Nicolai on 20-03-2017.
 */

public class ImageGallery extends Activity {
    private final String image_titles[] = {
            "bowser",
            "falco",
            "sheik",
            "roy",
            "ganondorf",
            "captainfalco",
            "mario",
            "drmario",
            "fox"
    };

    private final Integer image_ids[] = {
            R.drawable.bowser,
            R.drawable.falco,
            R.drawable.sheik,
            R.drawable.roy,
            R.drawable.ganondorf,
            R.drawable.captainfalco,
            R.drawable.mario,
            R.drawable.drmario,
            R.drawable.fox,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_screen);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);


    }
    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }
}
