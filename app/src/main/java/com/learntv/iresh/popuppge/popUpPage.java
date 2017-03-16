package com.learntv.iresh.popuppge;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.learntv.iresh.popuppge.animating.PopUpAnimating;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class popUpPage extends AppCompatActivity implements TextToSpeech.OnInitListener {

    ImageButton fatherBtn, motherBtn, daughterBtn, sonBtn, nextPageBtn,replayBtn;
    TextView memberText;
    Map<String, ImageButton> map;
    private TextToSpeech tts;
    boolean endingAnimations  = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_page);

        fatherBtn = (ImageButton) findViewById(R.id.fatherBtn);
        sonBtn = (ImageButton) findViewById(R.id.sonBtn);
        daughterBtn = (ImageButton) findViewById(R.id.daughterBtn);
        motherBtn = (ImageButton) findViewById(R.id.motherBtn);
        memberText = (TextView) findViewById(R.id.memberText);
        nextPageBtn = (ImageButton) findViewById(R.id.nextPageBtn);
        replayBtn = (ImageButton) findViewById(R.id.replayBtn);




        //memberText = (TextView)findViewById(R.id.memberText);
        tts = new TextToSpeech(this, this);
        tts.setLanguage(Locale.US);

        map = new HashMap<>();
        map.put("Father", fatherBtn);
        map.put("Mother", motherBtn);
        map.put("Daughter", daughterBtn);
        map.put("Son", sonBtn);
//        nextPageBtn.setVisibility(View.INVISIBLE);

        btnMapLooper();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageButton b = (ImageButton) v;
                b.setVisibility(View.INVISIBLE);

                btnMapLooper();
            }
        });
    }



    public void btnMapLooper(){
        nextPageBtn.setVisibility(View.GONE);
        replayBtn.setVisibility(View.GONE);
        fatherBtn.setVisibility(View.VISIBLE);
        motherBtn.setVisibility(View.VISIBLE);
        daughterBtn.setVisibility(View.VISIBLE);
        sonBtn.setVisibility(View.VISIBLE);



        int i = 1;
        if (i<map.size()){

            for (Map.Entry<String, ImageButton> entry : map.entrySet()) {
                imageBtnAnimate(entry.getKey(), entry.getValue(), i);

                i++;
            }

        }
    }

    private void imageBtnAnimate(final String name, final ImageButton member, final int delay) {

        final ViewPropertyAnimator anim = member.animate();

        anim.scaleX(1.5f).scaleY(1.5f).setDuration(2000).translationX(0).translationY(400).setStartDelay(delay * 3000).withStartAction(new Runnable() {
            @Override
            public void run() {
                say("this is " + name);
                memberText.setText(name);
            }
        })
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        anim.setStartDelay(2000);
                        anim.scaleX(1f).scaleY(1f).translationX(0).translationY(0).setDuration(1000).setStartDelay(1000);
                        if (delay==map.size()){
                            endingAnimations = true;

                            fatherBtn.setVisibility(View.INVISIBLE);
                            motherBtn.setVisibility(View.INVISIBLE);
                            daughterBtn.setVisibility(View.INVISIBLE);
                            sonBtn.setVisibility(View.INVISIBLE);
                            nextPageBtn.setVisibility(View.VISIBLE);
                            replayBtn.setVisibility(View.VISIBLE);

                        }

                        //state[0] = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }


    @Override
    public void onInit(int i) {
        say("Identify the family members!");
    }

    public void say(String text2say) {
        tts.speak(text2say, TextToSpeech.QUEUE_FLUSH, null);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("popUpPage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
