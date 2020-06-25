package com.example.tsmproj;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class Squat extends AppCompatActivity {
    Button viewAllButton, addButton, clearButton;
    EditText editWeight, editReps, editDate;
    TextView bestScore;
    SquatSQLHelper squatdb;
    private static final String TAG="Squat";
    private DatePickerDialog.OnDateSetListener editDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squat);

        squatdb = new SquatSQLHelper(this);
        viewAllButton = (Button) findViewById(R.id.viewSquatAll);
        clearButton = (Button) findViewById(R.id.clearButton);
        addButton = (Button) findViewById(R.id.squatAdd);
        editWeight = (EditText) findViewById(R.id.editWeightText);
        editReps = (EditText) findViewById(R.id.editRepsText);
        editDate = (EditText) findViewById(R.id.editDateText);
        bestScore = (TextView) findViewById(R.id.bestScore);
        viewall();
        getPersonalBest();
        addRecord();
        clearData();

        editDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Squat.this, android.R.style.Theme_DeviceDefault_Light_Panel,
                        editDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                dialog.show();
            }
        });
        editDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
                i1++;
                if(i2<=9&&i1<=9) {
                    editDate.setText(i + "/0" + i1 + "/"+"0" + i2);
                }
                else if(i2>=10&&i1>=10)editDate.setText(i + "/" + i1 + "/" + i2);
                else if(i2>=10&&i1<=9)editDate.setText(i + "/0" + i1 + "/" + i2);
                else if(i2<=9&&i1>=10)editDate.setText(i + "/" + i1 + "/0" + i2);
            }
        };
    }

    private void getPersonalBest() {
        int res = squatdb.getHighestVal();
        bestScore.setText(Integer.toString(res) +" kg");;
    }

    public void viewall(){
        viewAllButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = squatdb.getAllInfo();
                        if(res.getCount()==0){
                            showsquat("Error", "There's no data available!");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Date: "+res.getString(1)+"\n");
                            buffer.append("Weight: "+res.getString(2)+"kg"+"\n");
                            buffer.append("Reps: "+res.getString(3)+"\n\n");
                        }
                        showsquat("Squat history:",buffer.toString());
                    }
                }
        );

    }
    public void showsquat(String title, String message){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(title);
        b.setMessage(message);
        b.show();

    }
    public void addRecord(){
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!editWeight.getText().toString().isEmpty() && !editReps.getText().toString().isEmpty() && !editDate.getText().toString().isEmpty()){
                            squatdb.addRecord(editDate.getText().toString(),
                                    Integer.parseInt(editWeight.getText().toString()),
                                    Integer.parseInt(editReps.getText().toString()));
                            Toast.makeText(Squat.this,"Data Added!",Toast.LENGTH_LONG).show();
                            editDate.setText("");
                            editWeight.setText("");
                            editReps.setText("");
                            getPersonalBest();
                        } else
                            Toast.makeText(Squat.this,"Make sure that data is proper",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void clearData() {
        clearButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = squatdb.getAllInfo();
                        if(res.getCount()!=0){
                            squatdb.clearDatabase();
                            Toast.makeText(Squat.this, "Data Cleared!", Toast.LENGTH_LONG).show();
                            getPersonalBest();
                        } else
                            Toast.makeText(Squat.this, "No data to clear!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
