package com.example.a0040.music.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.a0040.music.ListFragment;
import com.example.a0040.music.R;
import com.facebook.drawee.backends.pipeline.Fresco;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private FrameLayout frameLayout;
    private SearchView searchView;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.container);


        Fresco.initialize(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int i = checkSelfPermission(permissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
                showDialog();
            }
        }
    }


    private void showDialog() {

        new AlertDialog.Builder(this).setTitle("存储权限不可用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions();
                        ;
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setCancelable(false).show();
//            }
//        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 321);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        showDialog();
                    } else
                        finish();
                }

            } else {
                Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_serach, menu);
        MenuItem menuItem = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("嘀~   学生卡");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            private ListFragment listFragment;

            @Override
            public boolean onQueryTextSubmit(String query) {
//                keyWord = query;
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                FragmentManager supportFragmentManager = getSupportFragmentManager();

                //事务是不能共享的，每次用到都要重新开启一个事务，之后提交
                FragmentTransaction fragmentTransactiontwo = supportFragmentManager.beginTransaction();
                //参数：1.父容器   2.要替换的fragment。


                if (listFragment != null) {
                    fragmentTransactiontwo.remove(listFragment);
                }

                listFragment = ListFragment.newInstance(query);
                fragmentTransactiontwo.add(R.id.container, listFragment);
                fragmentTransactiontwo.commit();


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
