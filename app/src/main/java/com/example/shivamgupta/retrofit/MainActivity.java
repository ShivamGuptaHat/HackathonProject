package com.example.shivamgupta.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.shivamgupta.retrofit.model.Student;
import com.example.shivamgupta.retrofit.service.APIService;
import java.util.List;
import java.util.Scanner;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button getDataBtn;
    TextView textDetails;
    EditText editName;
    Button setDataBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataBtn = (Button)findViewById(R.id.getDataBtn);
        textDetails = (TextView) findViewById(R.id.textDetail);
        editName = (EditText) findViewById(R.id.editName);
        setDataBtn = (Button) findViewById(R.id.setDataBtn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPeopleDetails();
            }
        });

        setDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStudentDetails();
            }
        });
    }

    private void setStudentDetails(){
        showProgressDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Student student = new Student();
            Scanner kb = new Scanner(editName.getText().toString());
            student.setName(kb.next());
            student.setAddress(kb.next());
            student.setMobile(Integer.parseInt(kb.next()));

            Call<Student> call = service.insertStudentInfo(student.getName(), student.getAddress(), student.getMobile());
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Response<Student> response, Retrofit retrofit) {
                    hideProgressDialog();
                }
                @Override
                public void onFailure(Throwable t) {
                    hideProgressDialog();
                }
            });

    }catch(Exception e){
            e.printStackTrace();
            Log.d("Error","Connection failed");
        }
    }

    private void getPeopleDetails(){
        showProgressDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Call<List<Student>> call = service.getStudentDetails();
            call.enqueue(new Callback<List<Student>>() {
                @Override
                public void onResponse(Response<List<Student>> response, Retrofit retrofit) {
                    List<Student> students = response.body();
                    String details = "";
                    for (int i = 0; i < students.size(); i++) {
                        String name = students.get(i).getName();
                        String address = students.get(i).getAddress();
                        int mobile = students.get(i).getMobile();
                        details += "\n name: " + name + " adress: " + address + " mobile: " + mobile;
                    }
                    textDetails.setText(details);
                    hideProgressDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    hideProgressDialog();
                }
            });
        }catch(Exception  e){
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
            hideProgressDialog();
        }
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }
}
