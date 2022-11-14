package abcdjob.workonline.com.qrcode.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import abcdjob.workonline.com.qrcode.R;

public class RedeemAdapter extends RecyclerView.Adapter<RedeemAdapter.MyViewHolder> {

    //private static final String TAG="RecyclerAdapter";
    List<Redeem_Data> redeem_data;
    private Context context;

    // int count=0;
    public RedeemAdapter(List<Redeem_Data> redeem_data, Context context) {
        this.redeem_data = redeem_data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Log.i(TAG, "onCreateViewHolder: " + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.redeem_data, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return new RedeemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Redeem_Data redeemData = redeem_data.get(position);

        //Picasso.with(context)
        //      .load(listData
        //            .getImage_url())
        //  .into(holder.imageView);


        switch (redeemData.getType()) {
            case "PAID":
                holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.name.setText("Amount Redeemed from  wallet");
                holder.amount.setText(redeemData.getAmount() + " ₹");
                holder.type.setText(redeemData.getType());
                holder.type.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.date.setText(redeemData.getPaid_date());
                holder.date.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.txnt.setText("Paid Date");


                break;
            case "PENDING":
                holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.name.setText("Amount Redeemed from  wallet");
                holder.amount.setText(redeemData.getAmount() + " ₹");
                holder.type.setText(redeemData.getType());
                holder.type.setTextColor(ContextCompat.getColor(context, R.color.warningColor));
                holder.date.setText(redeemData.getReq_date());
                holder.date.setTextColor(ContextCompat.getColor(context, R.color.favcolour));
                holder.txnt.setText("Requested Date");
                break;
            case "CANCELLED":
                holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.name.setText("Amount Redeemed from  wallet");
                holder.amount.setText(redeemData.getAmount() + " ₹");
                holder.type.setText(redeemData.getType());
                holder.type.setTextColor(ContextCompat.getColor(context, R.color.BloddColour));
                holder.date.setText(redeemData.getCancelled_date());
                holder.date.setTextColor(ContextCompat.getColor(context, R.color.BloddColour));
                holder.txnt.setText("Cancelled Date");
                break;
        }


        holder.date.setText(redeemData.getReq_date());
        holder.paytm_no.setText(redeemData.getPaytm_no());
        holder.method_type.setText(redeemData.getPayment_method());




        //holder.status.setText(listData.getStatus());
        //holder.joining.setText(listData.getJoining());
    }

    @Override
    public int getItemCount() {
        return redeem_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        //ImageView imageView;
        LinearLayout linearLayout;
        TextView amount,date,type,name,paytm_no,method_type,txnt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView= itemView.findViewById(R.id.imageViewImageMedia);
            amount=itemView.findViewById(R.id.amount);
            date=itemView.findViewById(R.id.wallet_date);
            name=itemView.findViewById(R.id.name);
            paytm_no=itemView.findViewById(R.id.redeempaytmno);
            type=itemView.findViewById(R.id.redeemstatus);
            method_type=itemView.findViewById(R.id.method_type);
            txnt=itemView.findViewById(R.id.txnt);
            //joining=itemView.findViewById(R.id.joining);



        }
    }
}