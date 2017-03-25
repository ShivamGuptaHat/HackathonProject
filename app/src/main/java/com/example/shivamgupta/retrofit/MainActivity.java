package com.example.shivamgupta.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.shivamgupta.retrofit.model.RootObject;
import com.example.shivamgupta.retrofit.service.APIService;
import java.util.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button getDataBtn;
    TextView textDetails;
    EditText yearText;
    //Button setDataBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataBtn = (Button)findViewById(R.id.getDataBtn);
        textDetails = (TextView) findViewById(R.id.textDetail);
        yearText = (EditText)findViewById(R.id.yearText);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPeopleDetails();
            }
        });
    }

    private void getPeopleDetails(){
        showProgressDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://aicte.comeze.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            Call<List<RootObject>> call = service.insertStudentInfo(yearText.getText().toString());
            call.enqueue(new Callback<List<RootObject>>() {
                @Override
                public void onResponse(Response<List<RootObject>> response, Retrofit retrofit) {
                    List<RootObject> students = response.body();
                    String details = "";
                    for (int i = 0; i < students.size(); i++) {
                        /*String name = students.get(i).getInstituteName();
                        String affiliated = students.get(i).getAffiliating();
                        String it = students.get(i).getInstituteType();
                        details += "\n name: " + name + " affilicated: " + affiliated + " institution types: " + it;*/

                        String aicteId = students.get(i).getAicteId();
                        String instName = students.get(i).getYear();
                        String year = students.get(i).getProgram();
                        details = "\n" + aicteId + " " + instName + " " + year;

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
