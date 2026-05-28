package tyl.edu.dean_ck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tyl.edu.dean_ck.R;
import tyl.edu.dean_ck.model.Truyen;

public class TruyenAdapterNew extends RecyclerView.Adapter<TruyenAdapterNew.TruyenViewHolder> {

    private Context context;
    private ArrayList<Truyen> listTruyen;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public TruyenAdapterNew(Context context, ArrayList<Truyen> listTruyen) {
        this.context = context;
        this.listTruyen = listTruyen;
    }

    // Interface click ngắn
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Interface long click (cho admin xóa)
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    // Hỗ trợ tìm kiếm
    public void filterList(ArrayList<Truyen> filteredList) {
        listTruyen = filteredList;
        notifyDataSetChanged();
    }

    public Truyen getTruyenAtPosition(int position) {
        return listTruyen.get(position);
    }

    @NonNull
    @Override
    public TruyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newtruyen, parent, false);
        return new TruyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenViewHolder holder, int position) {
        Truyen truyen = listTruyen.get(position);

        holder.txtTenTruyen.setText(truyen.getTenTruyen());

        Picasso.get()
                .load(truyen.getAnh())
                .placeholder(R.drawable.ic_load)
                .error(R.drawable.ic_image)
                .into(holder.imgTruyen);

        // Reset alpha đảm bảo khi bind lại view không bị ảnh hưởng alpha cũ
        holder.itemView.setAlpha(1f);
    }

    @Override
    public int getItemCount() {
        return listTruyen.size();
    }

    public class TruyenViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenTruyen;
        ImageView imgTruyen;

        public TruyenViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenTruyen = itemView.findViewById(R.id.textviewTentruyenNew);
            imgTruyen = itemView.findViewById(R.id.imgNewTruyen);

            // Click ngắn
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });

            // Long click
            itemView.setOnLongClickListener(v -> {
                if (onItemLongClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    // Thêm hiệu ứng mờ dần trước khi gọi callback xóa
                    itemView.animate()
                            .alpha(0f)            // Mờ dần về 0
                            .setDuration(300)     // Thời gian hiệu ứng 300ms
                            .withEndAction(() -> {
                                onItemLongClickListener.onItemLongClick(getAdapterPosition());
                                itemView.setAlpha(1f); // Khôi phục độ mờ cho view sau khi xử lý
                            })
                            .start();
                    return true; // Đã xử lý sự kiện
                }
                return false;
            });
        }
    }
}
