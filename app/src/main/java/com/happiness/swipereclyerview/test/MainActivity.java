package com.happiness.swipereclyerview.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.happiness.swipereclyerview.R;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.layout_root) LinearLayout layoutRoot;
  @BindView(R.id.viewpager) ViewPager viewpager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    TestFragment fragment = TestFragment.newInstance("sddsf", "sdfs");

    viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override public Fragment getItem(int position) {
        if (position == 0) {
          return TestFragment.newInstance("sddsf", "sdfs");
        } else {
          return GroupFragment.newInstance("", "");
        }
      }

      @Override public int getCount() {
        return 2;
      }
    });
  }
}
