package com.example.loginsignup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.R;
import com.example.loginsignup.helper.LocationHelper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;


public class ViewAttendanceAdapter extends FirebaseRecyclerAdapter<LocationHelper, ViewAttendanceAdapter.AttendanceViewHolder> {
    public ViewAttendanceAdapter(@NonNull FirebaseRecyclerOptions<LocationHelper> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position, @NonNull LocationHelper model) {




        holder.viewAddress.setText(model.getAddress());


        holder.viewDate.setText(model.getDate());
        holder.viewTime.setText(model.getTime());

    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_view_single_row, parent, false);
        return new AttendanceViewHolder(view);
    }



    class AttendanceViewHolder extends RecyclerView.ViewHolder {
                TextView viewAddress;
        TextView viewDate;
                TextView viewTime;



        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);


            viewAddress= (TextView)itemView.findViewById(R.id.txt_viewaddress);
            viewDate = (TextView) itemView.findViewById(R.id.txt_viewdate);
            viewTime=(TextView)itemView.findViewById(R.id.txt_viewtime);

        }
    }
}
