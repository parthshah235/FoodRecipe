package com.example.foodrecipe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class addRecipe extends Fragment {

    EditText textIn;
    Button addET;
    LinearLayout container1;


    private EditText dummyET;
    ImageView imgView;
    Button upload, add;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath,downloadURL;
    ProgressDialog pd;
    EditText desc,ing;
    String recName;
    Spinner spinner;
    String cuisine[] = {"Gujarati","Rajasthani","NorthIndian","SouthIndian","Chinese","Others"};
    String imgPath;
    String refid;

    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://foodrecipe-d21ab.appspot.com");    //change the url according to your firebase app

    FirebaseAuth auth;
    DatabaseReference ref;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fr = inflater.inflate(R.layout.add_recipe, null);



        imgView = fr.findViewById(R.id.imgView);
        upload = fr.findViewById(R.id.upload);
        add = fr.findViewById(R.id.add);
        dummyET = fr.findViewById(R.id.editText4);
        recName = dummyET.getText().toString();
        Log.i("Some", recName+"////");
        desc = fr.findViewById(R.id.r_desc);
        ing = fr.findViewById(R.id.ing);

        //Dynamic EditText
        addET = fr.findViewById(R.id.addEt);
        container1 = fr.findViewById(R.id.container1);

        addET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);

                EditText ing_et = (EditText) addView.findViewById(R.id.etING);
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);

                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout) addView.getParent()).removeView(addView);

                    }
                });

                container1.addView(addView);

            }
        });

        //End of Dynamic EditText

        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("Recipe").child(id);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading....");

        spinner = fr.findViewById(R.id.cuisines);

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,cuisine);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();

                if (filePath != null) {
                    pd.show();

                    String s = auth.getCurrentUser().getUid();
                    //String nm = name.getText().toString();
                    String reciName = dummyET.getText().toString().trim();
                    final StorageReference childRef = storageRef.child("images/"+s).child(reciName+".jpeg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);



                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();

                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                    Task<Uri> uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return childRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                Log.d("DownloadURL",downloadUri.toString());
                                ref.child(refid).child("imagePath").setValue(downloadUri.toString());
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return fr;
    }

    void saveRecipe()
    {
        String r_nm,description,cuisines,ingredients,imagePath;

        r_nm = dummyET.getText().toString();
        //Log.i("Name",r_nm+"::"+recName);
        description = desc.getText().toString().trim();
        cuisines = spinner.getSelectedItem().toString().trim();
        ingredients = ing.getText().toString();
        imagePath = "gs://foodrecipe-d21ab.appspot.com/images/"+auth.getCurrentUser().getUid()+'/'+r_nm+".jpeg";

        Log.e("name",r_nm);
        Log.e("Description",description);
        Log.e("Cuisines",cuisines);
        Log.e("Ingredients",ingredients);

            refid = ref.push().getKey();
        Log.e("ID",refid);
            recipe rep = new recipe(refid,r_nm, description, cuisines,ingredients,imagePath);
            //ref.child(refid).setValue(rep);
            ref.child(refid).setValue(rep);
            Toast.makeText(getContext(), "Recipe added", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                //Log.d("Filepath", data.getDataString().substring(data.getDataString().lastIndexOf(".")));
                //Setting image to ImageView
                imgView.setImageBitmap(bitmap);
                //String s1 = auth.getCurrentUser().getUid();
                //Uri uri = data.getData();
                //imgPath = storage.getReference().child("images").child(s1).child(uri.getLastPathSegment());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
