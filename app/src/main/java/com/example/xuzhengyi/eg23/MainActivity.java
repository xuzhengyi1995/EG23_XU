package com.example.xuzhengyi.eg23;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.*;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.suke.widget.SwitchButton;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static java.lang.System.console;
import static java.lang.System.err;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SweetAlertDialog pDialog;
    private String email;
    public boolean is_clicked;
    public ImageButton connectBtn;
    public boolean blink_b;
    public boolean is_on=false;
    private FloatingActionButton fab;

    private ListViewAdapter listViewAdapter;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Create the list view
        listView = (ListView) findViewById(R.id.listview);
        listViewAdapter= new ListViewAdapter(this);
        listView.setAdapter(listViewAdapter);
        listViewAdapter.setMode(Attributes.Mode.Single);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int go_type=listViewAdapter.get_type(position);
                if(go_type==R.drawable.ic_2joueurs){
                    Intent intent = new Intent(MainActivity.this, Detail2Activity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
                Toast.makeText(MainActivity.this, "You have click "+position, Toast.LENGTH_SHORT).show();
            }
        });
        build_btn();
        build_spinner();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //After the window has been charged
        ImageView imageView=(ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:Go to sheet13
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        connectBtn = (ImageButton) findViewById(R.id.connect);
        is_clicked=false;
        connectBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(final View view) {
                connect(view);
            }
        });

        TextView viewEmail = (TextView) findViewById(R.id.main_email);
        viewEmail.setText(email);
        super.onWindowFocusChanged(hasFocus);
    }

    private void build_spinner(){
        final String[] type={"All","Manual","Coup","Séquence","2Joueur"};
        final int[] rid={
                0,
                R.drawable.ic_manual,
                R.drawable.ic_coup,
                R.drawable.ic_sequen,
                R.drawable.ic_2joueurs};

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item,type);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean t=true;
                if(position==0){
                    t=false;
                }
                listViewAdapter.aplyFliter(t,rid[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });


    }

    private void build_btn(){
        SubActionButton.Builder intemBuilder = new SubActionButton.Builder(this);
        com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams l_Params = new  com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams(170,170);


        //Done:Change the button image
        ImageView t1 = new ImageView(this);
        t1.setImageResource(R.drawable.ic_manual);
        SubActionButton btn_manuellement = intemBuilder.setContentView(t1).setLayoutParams(l_Params).build();
        btn_manuellement.setOnClickListener(new SubActionButton.OnClickListener(){
            @Override
            public void onClick(final View view) {
                //TODO:Go to sheet05
                Intent intent = new Intent(MainActivity.this, SetManuellementActivity.class);
                startActivity(intent);
            }
        });

        ImageView t2 = new ImageView(this);
        t2.setImageResource(R.drawable.ic_coup);
        SubActionButton btn_coup = intemBuilder.setContentView(t2).setLayoutParams(l_Params).build();
        btn_coup.setOnClickListener(new SubActionButton.OnClickListener(){
            @Override
            public void onClick(final View view) {
                //TODO:Go to sheet06
                Intent intent = new Intent(MainActivity.this, SetCoupActivity.class);
                startActivity(intent);
            }
        });

        ImageView t3 = new ImageView(this);
        t3.setImageResource(R.drawable.ic_sequen);
        SubActionButton btn_seqence = intemBuilder.setContentView(t3).setLayoutParams(l_Params).build();
        btn_seqence.setOnClickListener(new SubActionButton.OnClickListener(){
            @Override
            public void onClick(final View view) {
                //TODO:Go to sheet07
                Intent intent = new Intent(MainActivity.this, SetSequenceActivity.class);
                startActivity(intent);
            }
        });

        ImageView t4 = new ImageView(this);
        t4.setImageResource(R.drawable.ic_2joueurs);
        SubActionButton btn_2joueur = intemBuilder.setContentView(t4).setLayoutParams(l_Params).build();
        btn_2joueur.setOnClickListener(new SubActionButton.OnClickListener(){
            @Override
            public void onClick(final View view) {
                //TODO:Go to sheet08
                Intent intent = new Intent(MainActivity.this, StatusActivity.class);
                intent.putExtra("p",true);
                startActivity(intent);
            }
        });



        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btn_manuellement)
                .addSubActionView(btn_coup)
                .addSubActionView(btn_seqence)
                .addSubActionView(btn_2joueur)
                .attachTo(fab).build();
    };

    public void connect(final View view)
    {
        if(!is_clicked) {
            blink_b = true;
            is_clicked = true;
            if (!is_on) {
                new CountDownTimer(2790, 279) {
                    @Override
                    public void onTick(long ms) {
                        if (blink_b) {
                            connectBtn.setImageResource(R.drawable.ic_sensor_enable);
                            blink_b = false;
                        } else {
                            connectBtn.setImageResource(R.drawable.ic_sensor_disable);
                            blink_b = true;
                        }
                    }

                    @Override
                    public void onFinish() {
                        connectBtn.setImageResource(R.drawable.ic_sensor_enable);
                        pDialog = new SweetAlertDialog(view.getContext());
                        pDialog.setTitleText("Connected").setContentText("Connect to your sensor successfully").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        }).show();
                        is_clicked = false;
                        is_on=true;
                    }
                }.start();
            }
            else{
                connectBtn.setImageResource(R.drawable.ic_sensor_disable);
                pDialog = new SweetAlertDialog(view.getContext());
                pDialog.setTitleText("Disconnected").setContentText("The sensor has been disconnected.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                }).show();
                is_clicked = false;
                is_on=false;
            }
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //TODO:Go back
        } else if (id == R.id.nav_progress) {
            //TODO:Go to sheet09
            Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_video) {
            //TODO:Go to sheet10
            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_support) {
            //TODO:Go to sheet12
            Intent intent = new Intent(MainActivity.this, SupportActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            //TODO:Go to sheet11
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
