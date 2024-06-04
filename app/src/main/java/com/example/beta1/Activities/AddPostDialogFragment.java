package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refForum;
import static com.example.beta1.Helpers.FBRefs.refUsers;
import static com.example.beta1.Helpers.Utilities.db2Dsiplay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.beta1.Objs.Message;
import com.example.beta1.R;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddPostDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_post_dialog, null);
        builder.setView(view)
                .setTitle("Add Post")
                .setPositiveButton("Add post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText etPostText = view.findViewById(R.id.etPostText);
                        EditText etPostTitle = view.findViewById(R.id.etPostTitle);
                        String title = etPostTitle.getText().toString();
                        String text = etPostText.getText().toString();
                        String name = user.getName();
                        Calendar calNow = Calendar.getInstance();
                        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyyMMddhhmm");
                        String time =sdfTime.format(calNow.getTime());;
                        String userId = user.getuId();
                        final String msgId = FirebaseDatabase.getInstance().getReference().push().getKey();
                        Message msg = new Message(userId, name,text ,time ,title,msgId);
                        refForum.child(msgId).setValue(msg);
                    }
                })
                .setNegativeButton("Cancel", null);
        return builder.create();

    }

}