package sayeefrm.android.safe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class DeviceViewAdapter extends RecyclerView.Adapter<DeviceViewAdapter.ViewHolder>{

    private ArrayList<String> mDeviceNames = new ArrayList<>();
    private ArrayList<Integer> mDeviceImages = new ArrayList<>();
    private ArrayList<String> mDeviceHashes = new ArrayList<>();
    private Context mContext;

    // Intent EXTRA_MESSAGE for ActiveDevice Activity
    public static final String EXTRA_MESSAGE = "sayeefrm.android.safe.MESSAGE";

    public DeviceViewAdapter(Context context, ArrayList<String> deviceNames, ArrayList<Integer> deviceImages, ArrayList<String> deviceHashes){
        mDeviceNames = deviceNames;
        mDeviceImages = deviceImages;
        mDeviceHashes = deviceHashes;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.device_text.setText(mDeviceNames.get(position));
        holder.device_hash.setText(mDeviceHashes.get(position));
        holder.device_image.setBackgroundResource(mDeviceImages.get(position));

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Take user to activity for this specific device
                Toast.makeText(mContext, mDeviceNames.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ActiveDevice.class);
                String message = mDeviceHashes.get(position);
                intent.putExtra(EXTRA_MESSAGE, message);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceHashes.size();
    }

    // Widgets within the Device view (this ViewHolder is used by this class)
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView device_image;
        TextView device_text;
        TextView device_hash;
        RelativeLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.device_image = itemView.findViewById(R.id.device_image);
            this.device_text = itemView.findViewById(R.id.device_text);
            this.device_hash = itemView.findViewById(R.id.device_hash);
            this.parent_layout = itemView.findViewById(R.id.list_item_layout);
        }
    }



}
