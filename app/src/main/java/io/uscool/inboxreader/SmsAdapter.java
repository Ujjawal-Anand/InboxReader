package io.uscool.inboxreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ujjawal on 6/8/17.
 */


 public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.ViewHolder> {
        private Context mContext;
        private List<Sms> mMessageList;
//        private AdapterView.OnItemClickListener mClickListener;

        /**
         * Class constructor
         * @param context context in which this is running in
         * @param messageList list containing {@link Sms} to populate in the view
         */
        public SmsAdapter(Context context, List<Sms> messageList) {
            this.mContext = context;
            this.mMessageList = messageList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms_card,
                    parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            int size = mMessageList.size();
            if(size == 0) {
                holder.contactNumber.setText(" ");
                holder.body.setText("No Message Found!! \nTry Again with Different Keyword");
                holder.body.setTextSize(20);
            } else {
                // as message should be in descending order of date and time
                Sms message = mMessageList.get(size - position - 1);

                holder.contactNumber.setText(message.getAddress());
                holder.body.setText(message.getMsg());

            }
        }

        @Override
        public int getItemCount() {
            if(mMessageList.isEmpty()) {
                return 1;
            }
            return mMessageList.size();
        }


        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView contactNumber;  // TextView to set contact Number
            private TextView body;

            public ViewHolder(View itemView) {
                super(itemView);
                contactNumber = (TextView) itemView.findViewById(R.id.number);
                body = (TextView) itemView.findViewById(R.id.body);
            }
        }
    }
