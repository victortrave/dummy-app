package com.example.victorardianto.myapplication;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victorardianto.myapplication.model.CinemaSeat;
import com.example.victorardianto.myapplication.model.CinemaSeatIdentity;
import com.example.victorardianto.myapplication.widget.zoomable.ZoomLayout;
import com.example.victorardianto.myapplication.widget.zoomable.ZoomableTwoDScrollFrameLayout;
import com.example.victorardianto.myapplication.widget.scroll.FakeHorizontalScrollBar;
import com.example.victorardianto.myapplication.widget.scroll.FakeScrollBar;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The Column.
     */
    final int COLUMN = 10, /**
     * The Row.
     */
    ROW = 10;
    /**
     * The Seat margin.
     */
    final int SEAT_MARGIN = 40;
    /**
     * The Seat size.
     */
    final int SEAT_SIZE = 120;

    /**
     * The Scale.
     */
    float scale = 1f;

    /**
     * The Button restore.
     */
    Button buttonRestore;
    /**
     * The Grid layout.
     */
    GridLayout gridLayout;

    /**
     * The Two d scroll view.
     */
    ZoomableTwoDScrollFrameLayout twoDScrollView;
    /**
     * The Zoom layout.
     */
    ZoomLayout zoomLayout;

    /**
     * The M fake scroll bar.
     */
    FakeScrollBar mFakeScrollBar;
    /**
     * The M fake horizontal scroll bar.
     */
    FakeHorizontalScrollBar mFakeHorizontalScrollBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridlayout);
        mFakeScrollBar = findViewById(R.id.fakeBar);
        mFakeHorizontalScrollBar = findViewById(R.id.fakeHorBar);

        twoDScrollView = findViewById(R.id.twoDScrollView);
        twoDScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mFakeScrollBar.scroll(twoDScrollView.getScrollY());
                mFakeHorizontalScrollBar.scroll(twoDScrollView.getScrollX());
            }
        });

//        zoomLayout = findViewById(R.id.zoomLayout);
//        zoomLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                mFakeScrollBar.scroll(zoomLayout.getScrollY());
//                mFakeHorizontalScrollBar.scroll(zoomLayout.getScrollX());
//            }
//        });

        buttonRestore = findViewById(R.id.buttonRestore);
        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        init();
    }

    private void init() {
        int total = COLUMN * ROW;
        for (int i = 0; i < total; i++) {
            CinemaSeat c = new CinemaSeat();
            c.setIdentity(new CinemaSeatIdentity("" + i, i));

            createSeatText(gridLayout, c, COLUMN);
        }

        mFakeScrollBar.setContentHeight(ROW * (SEAT_MARGIN + SEAT_SIZE + SEAT_MARGIN));
        mFakeHorizontalScrollBar.setContentWidth(COLUMN * (SEAT_MARGIN + SEAT_SIZE + SEAT_MARGIN));
    }

    private void createSeatText(GridLayout parent, CinemaSeat cinemaSeat, int totalSpan) {
        TextView textView = new TextView(this, null);
        textView.setBackground(new GradientDrawable());

        updateCinemaSeatText(textView, cinemaSeat);

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setGravity(Gravity.CENTER);

        int row = cinemaSeat.getIdentity().getPosition() / totalSpan;
        int col = cinemaSeat.getIdentity().getPosition() % totalSpan;

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(row, 1), GridLayout.spec(col, 1));
        layoutParams.setMargins(SEAT_MARGIN, SEAT_MARGIN, SEAT_MARGIN, SEAT_MARGIN);
        layoutParams.width = SEAT_SIZE;
        layoutParams.height = SEAT_SIZE;

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked " + cinemaSeat.getIdentity().getNumber(), Toast.LENGTH_SHORT).show();
            }
        });

        textView.setLayoutParams(layoutParams);
        parent.addView(textView);
    }

    private void updateCinemaSeatText(TextView textView, CinemaSeat cinemaSeat) {
        textView.setText(cinemaSeat.getIdentity().getNumber());
        textView.setTextColor(cinemaSeat.getTextColor());

        GradientDrawable background = (GradientDrawable) textView.getBackground();
        background.setColor(cinemaSeat.getFillColor());
    }

}
