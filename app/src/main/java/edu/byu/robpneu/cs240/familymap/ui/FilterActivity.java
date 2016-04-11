package edu.byu.robpneu.cs240.familymap.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.List;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;
import edu.byu.robpneu.cs240.familymap.model.Filter;

public class FilterActivity extends AppCompatActivity {
	private RecyclerView mFilterRecyclerView;
	private FilterAdapter mFilterAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		getSupportActionBar().setTitle("FamilyMap: Filter");

		mFilterRecyclerView = (RecyclerView) findViewById(R.id.filter_recycler_view);
		mFilterRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		updateUI();

	}

	private void updateUI(){
		List<Filter> filters = FamilyMap.getInstance().getFiltersList();
		mFilterAdapter = new FilterAdapter(filters);
		mFilterRecyclerView.setAdapter(mFilterAdapter);
	}

	private class FilterHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
		private Filter mFilter;
		private TextView mDescription;
		private TextView mSubDescription;
		private Switch mSwitch;
		private ImageView mImageView;

		public FilterHolder(View itemView){
			super(itemView);

			mDescription = (TextView) itemView.findViewById(R.id.filter_description);
			mSubDescription = (TextView) itemView.findViewById(R.id.filer_subdescription);
			mSwitch = (Switch) itemView.findViewById(R.id.filter_switch);
			mImageView = (ImageView) itemView.findViewById(R.id.filter_image_view);
		}

		public void BindFilter(Filter filter){
			mFilter = filter;
			mDescription.setText(mFilter.getDescription() + " Events");
			mDescription.setTextColor(Color.BLACK);
			mSubDescription.setText("FILTER BY " + mFilter.getDescription().toUpperCase() + " EVENTS");
			mSubDescription.setTextColor(Color.BLACK);
			mSwitch.setChecked(mFilter.isShown());
			mSwitch.setOnCheckedChangeListener(this);
			mImageView.setImageDrawable(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_circle).sizeDp(30).color(filter.getColor()));
		}

		/**
		 * Called when the checked state of a compound button has changed.
		 *
		 * @param buttonView The compound button view whose state has changed.
		 * @param isChecked  The new checked state of buttonView.
		 */
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			mFilter.setShown(isChecked);
		}
	}

	private class FilterAdapter extends RecyclerView.Adapter<FilterHolder>{

		private List<Filter> mFilters;

		public FilterAdapter(List<Filter> filters){
			mFilters = filters;
		}

		/**
		 * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
		 * an item.
		 * <p/>
		 * This new ViewHolder should be constructed with a new View that can represent the items
		 * of the given type. You can either create a new View manually or inflate it from an XML
		 * layout file.
		 * <p/>
		 * The new ViewHolder will be used to display items of the adapter using
		 * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
		 * different items in the data set, it is a good idea to cache references to sub views of
		 * the View to avoid unnecessary {@link View#findViewById(int)} calls.
		 *
		 * @param parent   The ViewGroup into which the new View will be added after it is bound to
		 *                 an adapter position.
		 * @param viewType The view type of the new View.
		 * @return A new ViewHolder that holds a View of the given view type.
		 * @see #getItemViewType(int)
		 * @see #onBindViewHolder(ViewHolder, int)
		 */
		@Override
		public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
			View view = layoutInflater.inflate(R.layout.item_filter, parent, false);
			return new FilterHolder(view);
		}

		/**
		 * Called by RecyclerView to display the data at the specified position. This method should
		 * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
		 * position.
		 * <p/>
		 * Note that unlike {@link ListView}, RecyclerView will not call this method
		 * again if the position of the item changes in the data set unless the item itself is
		 * invalidated or the new position cannot be determined. For this reason, you should only
		 * use the <code>position</code> parameter while acquiring the related data item inside
		 * this method and should not keep a copy of it. If you need the position of an item later
		 * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
		 * have the updated adapter position.
		 * <p/>
		 * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
		 * handle effcient partial bind.
		 *
		 * @param holder   The ViewHolder which should be updated to represent the contents of the
		 *                 item at the given position in the data set.
		 * @param position The position of the item within the adapter's data set.
		 */
		@Override
		public void onBindViewHolder(FilterHolder holder, int position) {
			Filter filter = mFilters.get(position);
			holder.BindFilter(filter);
		}

		/**
		 * Returns the total number of items in the data set hold by the adapter.
		 *
		 * @return The total number of items in this adapter.
		 */
		@Override
		public int getItemCount() {
			return mFilters.size();
		}
	}

}