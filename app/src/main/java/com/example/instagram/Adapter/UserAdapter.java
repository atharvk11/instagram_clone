package com.example.instagram.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.MainActivity;
import com.example.instagram.Model.User;
import com.example.instagram.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private List<User> mUsers;
    private boolean isFragment;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<User> mUsers, boolean isFragment) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final User user=mUsers.get(position);
        holder.btnFollow.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        holder.fullname.setText(user.getName());

        Picasso.get().load(user.getImageurl()).placeholder(R.mipmap.ic_launcher).into(holder.imageProfile);
        isFollowed(user.getId(),holder.btnFollow);

        if(user.getId().equals(firebaseUser.getUid())){
            holder.btnFollow.setVisibility(View.GONE);
        }
        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btnFollow.getText().toString().equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following")
                            .child(user.getId()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(user.getId()).child("followers").child(firebaseUser.getUid()).setValue(true);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid()).child("following")
                            .child(user.getId()).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(user.getId()).child("followers").child(firebaseUser.getUid()).removeValue();

                }
            }
        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("publisherId", user.getId());
                mContext.startActivity(intent);
            }
        });
        holder.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("publisherId", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    private void isFollowed(final String id, final Button btnFollow) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(firebaseUser.getUid()).child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(id).exists()){
                    btnFollow.setText("following");
                }
                else {
                    btnFollow.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imageProfile;
        public TextView username;
        public Button btnFollow;
        public TextView fullname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile=itemView.findViewById(R.id.imageProfileUserItem);
            username=itemView.findViewById(R.id.usernameTVUserItem);
            btnFollow=itemView.findViewById(R.id.buttonFollowUserItem);
            fullname=itemView.findViewById(R.id.fullnameTVUserItem);

        }
    }
}
