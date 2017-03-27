package com.qubuss.todotransfer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qubuss.todotransfer.database.DbContract;
import com.qubuss.todotransfer.database.DbHelper;
import com.qubuss.todotransfer.domain.Transfer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<Transfer> arrayList = new ArrayList<>();
    private AlertDialog alertDialog;
    private EditText nameET;
    private EditText bankNameET;
    private Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        resources = getResources();

        init();
        initAlertDialog();

        readFromLocalStorage();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();

            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ){ //| ItemTouchHelper.RIGHT
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    final int fromPos = viewHolder.getAdapterPosition();
//                    final int toPos = viewHolder.getAdapterPosition();
//                    // move item in `fromPos` to `toPos` in adapter.
                return true;// true if moved, false otherwise
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                recyclerAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                TextView test = (TextView) viewHolder.itemView.findViewById(R.id.transferIdTV);
                Log.e("VIEWHOLDER", test.getText().toString().trim());
                String idString = test.getText().toString().trim();
                deleteFromLocalStorage(idString);
            }

        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void init(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initAlertDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(dialogView);
        nameET = (EditText) dialogView.findViewById(R.id.nameET);
        bankNameET = (EditText) dialogView.findViewById(R.id.bankNameET);
        alertDialog.setTitle(resources.getString(R.string.dodaj));

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, resources.getString(R.string.zapisz), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameET.getText().toString().trim();
                String bankName = bankNameET.getText().toString().trim();
                saveToLocalStorage(name, bankName);
            }
        });

    }


    private void readFromLocalStorage(){

        arrayList.clear();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalDataBase(database);
        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex(DbContract.ID));
            String name = cursor.getString(cursor.getColumnIndex(DbContract.NAME));
            String bankName = cursor.getString(cursor.getColumnIndex(DbContract.BANK_NAME));
            Log.e("ID", id+"");
            arrayList.add(new Transfer(id, name, bankName));
        }

        recyclerAdapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();
    }

    private void saveToLocalStorage(String name, String bankName){

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDataBase(name, bankName, database);
        readFromLocalStorage();
        dbHelper.close();

    }

    private void deleteFromLocalStorage(String id){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int i = dbHelper.deleteFromLocalDataBase(id, database);
        Log.e("DELETE", i+"");
        readFromLocalStorage();
        dbHelper.close();
    }
}

