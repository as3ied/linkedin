package net.senior.hr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder>{
   FirebaseDatabase firebaseDatabase;
   DatabaseReference databaseReference;
   EmployerModel company;
    public JobsAdapter(List<JobModel> jobs) {
        this.jobs = jobs;
    }

    List<JobModel> jobs;
private OnViewListiner onViewListiner;

    public void setOnViewListier(OnViewListiner onViewListier) {
        this.onViewListiner = onViewListier;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        if( jobs.get(position)!=null ) {
            databaseReference.child("Employer").child(jobs.get(position).getCompId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    holder.companyName.setText(dataSnapshot.getValue(EmployerModel.class).getCompanyName());
                    holder.hrName.setText(dataSnapshot.getValue(EmployerModel.class).getHrName());
                    Picasso.get().load(dataSnapshot.getValue(EmployerModel.class).getPhoto()).into(holder.img);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            holder.title.setText(jobs.get(position).getTitle());
            holder.date.setText(jobs.get(position).getJobDate());

        }


    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,companyName,date,hrName,registDate,mobile,email,address;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            companyName=itemView.findViewById(R.id.companyName);
            date=itemView.findViewById(R.id.jobDate);
            img=itemView.findViewById(R.id.img);
            hrName=itemView.findViewById(R.id.hrName);
            registDate=itemView.findViewById(R.id.compRegistDate);
            mobile=itemView.findViewById(R.id.mobile);
            email=itemView.findViewById(R.id.email);
            address=itemView.findViewById(R.id.address);
      itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              onViewListiner.onViewClicked(getAdapterPosition());

          }
      });
        }
    }

    public interface OnViewListiner{
        public void onViewClicked(int pos);
    }
}
