package com.example.vploaia.musicappfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.vploaia.musicapp.R;
import com.example.vploaia.musicappfragments.ItemsListFragment.OnListItemSelectedListener;

/**
 * Created by vploaia on 3/1/2017.
 */

public class ItemsListActivity extends AppCompatActivity implements OnListItemSelectedListener {
    private boolean isTwoPane = false;
    public TrackService trackService = WebTrackService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        determinePaneLayout();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_local:
                trackService = new LocalTrackService(ItemsListActivity.this);
                break;

            case R.id.item_web:
                trackService = WebTrackService.getInstance();
                break;
        }
        return true;
    }

    private void determinePaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.activity_item_detail_container);
        if (fragmentItemDetail != null) {
            isTwoPane = true;
            ItemsListFragment fragmentItemsList =
                    (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
            fragmentItemsList.setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(Track track) {
        if (isTwoPane) { // single activity with list and detail
            // Replace frame layout with correct detail fragment
            ItemDetailFragment fragmentItem = ItemDetailFragment.newInstance(track);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.activity_item_detail_container, fragmentItem);
            ft.commit();
        } else { // separate activities
            // launch detail activity using intent
            Intent i = new Intent(ItemsListActivity.this, ItemDetailActivity.class);
            i.putExtra("track", track);
            startActivity(i);
        }
    }


}
