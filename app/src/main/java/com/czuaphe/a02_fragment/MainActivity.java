package com.czuaphe.a02_fragment;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends FragmentActivity {

    static final int NUM_ITEMS = 4;
    PagerSlidingTabStrip tab;

    ViewPager mPager;

    MyAdapter mAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        tab = (PagerSlidingTabStrip) findViewById(R.id.pager_tabs);
        mAdaper = new MyAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdaper);
        tab.setViewPager(mPager);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, dm);
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(0);
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {

        private final String[] TITLE = getResources().getStringArray(R.array.page_name);

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            MyFragment myFragment = new MyFragment();

            bundle.putInt("page_num", position);
            myFragment.setArguments(bundle);
            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }
    }

    public static class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

        private int pageNum;
        private TextView tv1;
        private SwipeRefreshLayout mRefreshLayout;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View pager = inflater.inflate(R.layout.pager_fragment, container, false);

            tv1 = (TextView) pager.findViewById(R.id.tv);
            Bundle bundle = getArguments();
            pageNum = bundle.getInt("page_num");
            tv1.setText(pageNum + "");


            mRefreshLayout = (SwipeRefreshLayout) pager.findViewById(R.id.refresh_layout);
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

            return pager;
        }

        @Override
        public void onRefresh() {
            mRefreshLayout.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(false);
                }
            }, 3000);
        }
    }
}
