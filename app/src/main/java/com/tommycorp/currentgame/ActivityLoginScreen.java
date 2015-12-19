package com.tommycorp.currentgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityLoginScreen extends AppCompatActivity implements AsyncResponse {

    private static final int DELETE = 1;
    APIgetUser apIgetUser = new APIgetUser();
    EditText editTextSummonname, editTextRegion;
    ImageView userIcon;
    List<ObjectUser> users = new ArrayList<ObjectUser>();
    ListView userListView;
    Uri iconUri = Uri.parse("android.resource://com.tommycorp.currentgame/drawable/dragon_trainer_tristana");
    DatabaseUser DatabaseUser;
    DatabaseChampions DatabaseChampions;
    int longClickedItemIndex;
    ArrayAdapter<ObjectUser> userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //ActivityDownload content
        DatabaseChampions = new DatabaseChampions(getApplicationContext());
        if (DatabaseChampions.getChampionsCount() < 100) {
            final Intent download = new Intent(this, ActivityDownload.class);
            startActivity(download);
            DatabaseSummonerspell DatabaseSummonerspell = new DatabaseSummonerspell(getApplicationContext());
            DatabaseSummonerspell.addSummonerSpellID();
        }

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("user");
        tabSpec.setContent(R.id.tabUser);
        tabSpec.setIndicator("User");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("add");
        tabSpec.setContent(R.id.tabAdd);
        tabSpec.setIndicator("Add");
        tabHost.addTab(tabSpec);

        editTextSummonname = (EditText) findViewById(R.id.editTextSummonname);
        editTextRegion = (EditText) findViewById(R.id.editTextRegion);
        userListView = (ListView) findViewById(R.id.listView);
        userIcon = (ImageView) findViewById(R.id.userIcon);
        DatabaseUser = new DatabaseUser(getApplicationContext());

        registerForContextMenu(userListView);

        userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

        final Intent i = new Intent(this, ActivityShowCurrentGame.class);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ObjectUser thisUser;
                thisUser = DatabaseUser.getUser(position);
                int summonerID = thisUser.getSummonID();
                i.putExtra("ID", summonerID);
                startActivity(i);
            }
        });

                //Gør at både region og summonname skal være udfyldt.
        final Button buttonApply = (Button) findViewById(R.id.buttonApply);
        buttonApply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buttonApply.setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonApply.setEnabled(String.valueOf(editTextRegion.getText()).trim().length() > 0);
            }

        });

        //Sætter alle users fra db til 'users' list, hvorefter de bliver sat ind vha. updateList
        if (DatabaseUser.getUserCount() != 0){
            users.addAll(DatabaseUser.getAllUsers());
        }
        updateList();
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderIcon(R.drawable.edit_user);
        menu.add(Menu.NONE, DELETE, Menu.NONE, "Delete");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE:
                ObjectUser thisUser;
                thisUser = DatabaseUser.getUser(longClickedItemIndex);
                users.remove(longClickedItemIndex);
                int thisUserId = thisUser.getId();
                DatabaseUser.deleteUser(String.valueOf(thisUserId));
                updateList();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void buttonApplyClick(View view) {
        String key = "a7c084f1-8ec6-4e3e-840b-085cbde654a1";
        String summonnameRaw = editTextSummonname.getText().toString();
        String summonname = summonnameRaw.replaceAll(" ", "%20");
        String region = editTextRegion.getText().toString();
        String url = "https://euw.api.pvp.net/api/lol/" + region + "/v1.4/summoner/by-name/" + summonname + "?api_key=" + key;
        if (!userExists(summonnameRaw)) {
            apIgetUser.delegate = this;
            apIgetUser.execute(url);
            return;
        }
        Toast.makeText(getApplicationContext(), "Already Exists!", Toast.LENGTH_LONG).show();
        editTextSummonname.setText("");
    }


    @Override
    public void processFinish(List<String> result) {
        String loginName = result.get(0);
        int SummonerLevel = Integer.parseInt(result.get(1));
        int SummonID = Integer.parseInt(result.get(2));
        ObjectUser user = new ObjectUser(DatabaseUser.getUserCount(), loginName, editTextSummonname.getText().toString(), editTextRegion.getText().toString(), iconUri, SummonerLevel, SummonID);
        DatabaseUser.addUser(user);
        users.add(user);
        userAdapter.notifyDataSetChanged();
    }

    //Change icon
    public void buttonIconClick(View view){
        Intent intent = new Intent();
        intent.setType("icon/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Icon"), 1);
    }

    //Hvis 'buttonIconClick' er OK, do;
    public void onActivityResult(int reqCode, int resCode, Intent data){
        if (resCode == RESULT_OK) {
            if (reqCode == 1){
                iconUri = data.getData();
                userIcon.setImageURI(data.getData());
            }
        }
    }

    private boolean userExists(String user){
        int userCount = users.size();
        for (int i = 0; i < userCount; i++) {
            if (user.compareToIgnoreCase(users.get(i).getSummonname()) == 0) {
                return true;
            }
        }
        return false;
    }

    private void updateList(){
        userAdapter = new UserListAdapter();
        userListView.setAdapter(userAdapter);
    }


    //Create list
    private class UserListAdapter extends ArrayAdapter<ObjectUser> {
        public UserListAdapter() {
            super(ActivityLoginScreen.this, R.layout.listview_login, users);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.listview_login, parent, false);
            }

            ObjectUser currentUser = users.get(position);

            TextView userSummon = (TextView) view.findViewById(R.id.userSummon);
            TextView userRegion = (TextView) view.findViewById(R.id.userRegion);
            ImageView userlistIcon = (ImageView) view.findViewById(R.id.userlistIcon);
            TextView userSummonerLevel = (TextView) view.findViewById(R.id.userSummonerLevel);
            TextView userSummonID = (TextView) view.findViewById(R.id.userSummonID);

            userlistIcon.setImageURI(currentUser.geticonUri());
            userSummon.setText(currentUser.getSummonname());
            userRegion.setText(currentUser.getRegion());
            userSummonerLevel.setText(String.valueOf(currentUser.getSummonerLevel()));
            userSummonID.setText(String.valueOf(currentUser.getSummonID()));

            return view;
        }
    }
}
