package com.happiness.swipereclyerview.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.happiness.swipereclyerview.R;
import com.happiness.swipereclyerview.test.TestFragment;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.layout_root) LinearLayout layoutRoot;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    TestFragment fragment = TestFragment.newInstance("sddsf", "sdfs");

    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
  }
}
