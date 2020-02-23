package com.men.myselfexercises;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static int openDialogId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Button btnOpenDialog = (Button) findViewById(R.id.btn_opendialog);
         btnOpenDialog.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showMyDialog(openDialogId);
             }
         });
    }

    private void showMyDialog(int openDialogId) {
        AlertDialog dialog = OpenDialog.createDialog(this);
        dialog.show();
    }


}
