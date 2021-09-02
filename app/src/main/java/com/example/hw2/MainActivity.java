package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private TextView welcome;
    private EditText username;
    private EditText password;
    private Button login_button;

    static HashMap<Integer, ArrayList<String>> credentials = loadCredentials();

    private static HashMap<Integer, ArrayList<String>> loadCredentials() {
        ArrayList<String> admin = new ArrayList<String>();
        admin.add("Admin");
        admin.add("Password");

        HashMap<Integer, ArrayList<String>> result = new HashMap<Integer, ArrayList<String>>();
        if(!result.containsKey(1)) {
            result.put(1, admin);
        }
        return result;
    }


    public static boolean validate(String name, String pass) {
        boolean valid_username = false;
        boolean valid_password = false;

        Iterator i = credentials.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry mapElement = (Map.Entry)i.next();
            ArrayList<String> credential = (ArrayList<String>) mapElement.getValue();
            String username = credential.get(0);
            String password = credential.get(1);

            if (username.equals(name) && password.equals(pass)) {
                valid_username = true;
                valid_password = true;
                break;
            }
        }

        return valid_username && valid_password;
    }


    public static ArrayList<String> getUser(String name, String pass) {
        ArrayList<String> user = new ArrayList<>();

        Iterator i = credentials.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry mapElement = (Map.Entry)i.next();
            ArrayList<String> credential = (ArrayList<String>) mapElement.getValue();
            String username = credential.get(0);
            String password = credential.get(1);

            if (username.equals(name) && password.equals(pass)) {
                String id = String.valueOf(mapElement.getKey());
                user.add(id);
                user.add(username);
                user.add(password);
                return user;
            }
        }

        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_button = findViewById(R.id.login);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pass = password.getText().toString();

                if(name.isEmpty() && pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter a Username and Password.", Toast.LENGTH_SHORT).show();
                }

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Username.", Toast.LENGTH_SHORT).show();
                }

                if (pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Password.", Toast.LENGTH_SHORT).show();
                }

                boolean isValid = validate(name, pass);

                if (isValid) {
                    ArrayList<String> user = getUser(name, pass);
                    setContentView(R.layout.activity_posts);
                    textViewResult = findViewById(R.id.text_view_result);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://jsonplaceholder.typicode.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

                    call.enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                            if (!response.isSuccessful()) {
                                textViewResult.setText("Code: " + response.code());
                                return;
                            }

                            List<Post> posts = response.body();
                            textViewResult.append("Welcome "+ name + "!\n\n");
                            for (Post post : posts) {
                                if(post.getUserId() == Integer.parseInt(user.get(0))) {
                                    String content = "";
                                    content += "ID: " + post.getId() + "\n";
                                    content += "User ID: " + post.getUserId() + "\n";
                                    content += "Title: " + post.getTitle() + "\n";
                                    content += "Text: " + post.getText() + "\n\n";

                                    textViewResult.append(content);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Post>> call, Throwable t) {
                            textViewResult.setText(t.getMessage());
                        }
                    });
                }
            }
        });


    }


}