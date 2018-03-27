package au.com.realestate.modules.hometime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.com.realestate.modules.hometime.R;
import au.com.realestate.mvp.models.Tram;
import butterknife.BindView;
import butterknife.ButterKnife;



public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {


    private LayoutInflater layoutInflater;
    private List<Tram> tramList = new ArrayList<>();

    public HomeAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public class Holder extends RecyclerView.ViewHolder {


        Context context;

        @BindView(R.id.textview_title)
        protected TextView itemTitle;

        @BindView(R.id.textview_time)
        protected TextView itemTime;


        public Holder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Tram tram) {

            itemTitle.setText(tram.Destination);
            itemTime.setText(dateFromDotNetDate(tram.PredictedArrivalDateTime).toString());
        }
    }

    public void addList(List<Tram> tramsList) {
        this.tramList.clear();
        this.tramList.addAll(tramsList);
        notifyDataSetChanged();
    }
    public void clearList() {
        this.tramList.clear();
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.bind(tramList.get(position));
    }

    @Override
    public int getItemCount() {
        return tramList.size();
    }

    private Date dateFromDotNetDate(String dotNetDate) {

        int startIndex = dotNetDate.indexOf("(") + 1;
        int endIndex = dotNetDate.indexOf("+");
        String date = dotNetDate.substring(startIndex, endIndex);

        Long unixTime = Long.parseLong(date);
        return new Date(unixTime);
    }

}
