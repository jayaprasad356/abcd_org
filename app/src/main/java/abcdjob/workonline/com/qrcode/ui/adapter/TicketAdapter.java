package abcdjob.workonline.com.qrcode.ui.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


import abcdjob.workonline.com.qrcode.Models.TicketDTO;
import abcdjob.workonline.com.qrcode.Models.UserDTO;
import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.Util.GlobalVariables;
import abcdjob.workonline.com.qrcode.Util.Method;

import abcdjob.workonline.com.qrcode.ui.CommentOneByOne;


public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    private Context mContext;
    private final Context context;
    private final ArrayList<TicketDTO> ticketDTOSList;
    private UserDTO userDTO;
    public Method method;


   /* public TicketAdapter(Context mContext, ArrayList<TicketDTO> ticketDTOSList, UserDTO userDTO) {
        this.mContext = mContext;
        method=new Method(mContext);
        this.ticketDTOSList = ticketDTOSList;
        this.userDTO = userDTO;

    }*/

    public TicketAdapter(ArrayList<TicketDTO> ticketDTOSList, Context context) {
        this.ticketDTOSList = ticketDTOSList;
        method=new Method(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ticket, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.tvTicket.setText("Title : " + ticketDTOSList.get(position).getReason());
        holder.ticketid.setText("Support Ticket Id # " + ticketDTOSList.get(position).getId());

        try {
            holder.tvDate.setText(Method.convertTimestampDateToTime((ticketDTOSList.get(position).getCreated_at())));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (ticketDTOSList.get(position).getStatus().equalsIgnoreCase("0")) {
            holder.tvStatus.setText((R.string.pending1));
            holder.llStatus.setBackgroundResource(R.drawable.rectangle_red);
        } else if (ticketDTOSList.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.tvStatus.setText((R.string.solve1));
            holder.llStatus.setBackgroundResource(R.drawable.rectangle_yellow);
        } else if (ticketDTOSList.get(position).getStatus().equalsIgnoreCase("2")) {
            holder.tvStatus.setText((R.string.close1));
            holder.tvStatus.setText("Support ticket Closed");

            //   holder.llStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_green));
          //  holder.llStatus.setBackgroundResource(R.drawable.rectangle_green));
            //holder.llStatus.setBackgroundResource(ContextCompat.getDrawable(context, R.drawable.rectangle_green));
            holder.llStatus.setBackgroundResource(R.drawable.rectangle_green);
        }

        holder.rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, CommentOneByOne.class);
                method.preferencess.setValue("ticketId", ticketDTOSList.get(position).getId());
                in.putExtra(GlobalVariables.TICKET_ID, ticketDTOSList.get(position).getId());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketDTOSList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTicket, ticketid;
        public TextView tvDate, tvStatus;
        public LinearLayout llStatus;
        public RelativeLayout rlClick;

        public MyViewHolder(View view) {
            super(view);
            llStatus = view.findViewById(R.id.llStatus);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvTicket = view.findViewById(R.id.tvTicket);
            tvDate = view.findViewById(R.id.tvDate);
            rlClick = view.findViewById(R.id.rlClick);
            ticketid = view.findViewById(R.id.tvTicketid);
        }
    }
}