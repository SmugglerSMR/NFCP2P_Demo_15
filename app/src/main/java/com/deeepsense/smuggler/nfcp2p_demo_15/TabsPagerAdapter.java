package com.deeepsense.smuggler.nfcp2p_demo_15;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Smuggler on 6/12/2017.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private static final Fragment[] FRAGMENTS = new Fragment[] { new BeamWriterFragment(), new BeamReaderFragment() };
    private static final int BEAM_WRITER_FRAGMENT_INDEX = 0;
    private static final int BEAM_READER_FRAGMENT_INDEX = 1;
    private Context context;

    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        //FRAGMENTS = new BeamWriterActivity[]{new BeamWriterActivity()};
    }

    @Override
    public Fragment getItem(int position) { return FRAGMENTS[position]; }

    @Override
    public int getCount() {

        return FRAGMENTS.length;
        //return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case BEAM_WRITER_FRAGMENT_INDEX:
                return context.getString(R.string.title_fragment_beam_writer);
            case BEAM_READER_FRAGMENT_INDEX:
                return context.getString(R.string.title_fragment_beam_reader);
        }
        return null;
    }
}
