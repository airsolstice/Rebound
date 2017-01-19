package com.admin.rebound;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class MainActivity extends AppCompatActivity {

    private TextView mText;
    private LinearLayout ll;
    private SpringSystem mSpringSystem = SpringSystem.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = new TextView(this);
        mText.setText("Hello World!");
        mText.setTextColor(Color.RED);

        ll = (LinearLayout) findViewById(R.id.ll);

        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        scaleView(v,1.2f);
                        break;
                    case MotionEvent.ACTION_UP:
                        scaleView(v,1f  );
                        break;


                }
                return false;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll.addView(mText);
                animateViewDirection(mText,720,0,40,5);


            }
        });

    }

    private void animateViewDirection(final View v, float from, float to, double tension, double friction) {

        Spring spring = mSpringSystem.createSpring();
        spring.setCurrentValue(from);
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                v.setTranslationY((float) spring.getCurrentValue());
            }
        });
        spring.setEndValue(to);
    }


    public void init(){

        // Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

        // Add a spring to the system.
        Spring spring = springSystem.createSpring();

        // Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                mText.setScaleX(scale);
                mText.setScaleY(scale);
            }
        });

        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(1);


    }


    public void scaleView(View v ,float scale){
        v.animate().scaleX(scale).scaleY(scale).setDuration(80).start();
    }



}
