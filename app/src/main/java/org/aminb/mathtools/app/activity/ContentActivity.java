/*
 * ContentActivity.java
 * Copyright (C) 2014 Amin Bandali <me@aminb.org>
 *
 * MATHTools is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MATHTools is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.aminb.mathtools.app.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.fragment.trigonometry.CalculatorFragment;
import org.aminb.mathtools.app.fragment.vector.LinesFragment;
import org.aminb.mathtools.app.fragment.vector.ProductsFragment;
import org.aminb.mathtools.app.fragment.vector.ProjectionsFragment;
import org.aminb.mathtools.app.util.Fragments;

import java.util.Locale;

public class ContentActivity extends BaseActivity implements ActionBar.TabListener{

    public static final String ARG_TITLE_ID = "titleId";
    public static final String ARG_TITLES = "titles";

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private int titleId;
    private static String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        titleId = getIntent().getExtras().getInt(ARG_TITLE_ID);
        titles = getIntent().getExtras().getStringArray(ARG_TITLES);

        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
//                mActionBar.setSubtitle(titles[position]);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            mActionBar.addTab(
                    mActionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        mActionBar.setTitle(titleId);
//        mActionBar.setSubtitle(titles[0]);
//        getActionbarSubtitle().setTextColor(getResources().getColor(R.color.actionbar_subtitle_text_color));
    }

//    public TextView getActionbarSubtitle() {
//        return (TextView) this.findViewById(R.id.action_bar_subtitle);// for default action bar
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.content, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//
//            case R.id.action_cheat_sheet:
//                mViewPager.setCurrentItem((mSectionsPagerAdapter.getCount()) - 1);
//                return true;
//            case R.id.action_feedback:
//                Intent intent_feedback = new Intent(this, FeedbackActivity.class);
//                startActivity(intent_feedback);
//                return true;
//            case R.id.action_about:
//                Utils.showAbout(this);
//                return true;
//
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        // Sparse array to keep track of registered fragments in memory
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Instantiate and return a Fragment.
            Fragment fragment = Fragments.getFragment(titleId, position);

            Bundle args = new Bundle();
            args.putInt(Fragments.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;

        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return titles[position].toUpperCase(l);
        }

        // Register the fragment when the item is instantiated
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

}
