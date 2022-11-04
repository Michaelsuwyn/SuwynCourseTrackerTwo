package com.suwyn.suwyncoursetracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suwyn.suwyncoursetracker.Entity.Assessment;
import com.suwyn.suwyncoursetracker.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View assessmentView){
            super(assessmentView);
            assessmentItemView=assessmentView.findViewById(R.id.textView4);
            assessmentView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("name", current.getName());
                    intent.putExtra("description", current.getDescription());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position){
        if(mAssessments!=null){
            Assessment current = mAssessments.get(position);
            String name = current.getName();
            String ID = Integer.toString(current.getAssessmentID());
            holder.assessmentItemView.setText(ID + ": " + name);
        }
        else {
            holder.assessmentItemView.setText("No Assessment Name");
        }
    }

    public void setAssessment(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mAssessments!=null){
            return mAssessments.size();
        }
        else {
            return 0;
        }
    }
}
