package edu.byu.robpneu.cs240.familymap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.List;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.model.Event;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;
import edu.byu.robpneu.cs240.familymap.model.Filter;
import edu.byu.robpneu.cs240.familymap.model.Person;

public class SearchActivity extends AppCompatActivity {
	private EditText mSearchBox;
	private FamilyMap mFamilyMap;
	private RecyclerView mSearchRecyclerView;
	private SearchAdapter mSearchAdapter;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		getSupportActionBar().setTitle("FamilyMap: Search");
		mFamilyMap = FamilyMap.getInstance();

		mSearchBox = (EditText)findViewById(R.id.SearchBox);
		mSearchBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				updateUI(s.toString());
			}
		});

		mSearchRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
		mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		updateUI("");
	}

	private void updateUI(String searchTerm) {
		if (searchTerm.length() > 0) {
			List<Item> items = mFamilyMap.searchAll(searchTerm);
			mSearchAdapter = new SearchAdapter(items);
			mSearchRecyclerView.setAdapter(mSearchAdapter);
		}
	}

	public interface Item {
		boolean contains(String search);
	}

	private class SearchHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
		private Item mItem;
		private ImageView mImageView;
		private TextView mDescription;
		private TextView mSubDescription;
		private RelativeLayout mLayout;


		public SearchHolder(View itemView){
			super(itemView);

			mImageView = (ImageView) itemView.findViewById(R.id.search_image_view);
			mDescription = (TextView) itemView.findViewById(R.id.search_description);
			mSubDescription = (TextView) itemView.findViewById(R.id.search_subdescription);
			mLayout = (RelativeLayout) itemView.findViewById(R.id.search_layout);
		}

		public void BindSearch(Item item){
			mItem = item;
			mLayout.setOnClickListener(this);
			if(mItem.getClass() == Person.class){
				Person person = (Person) mItem;
				mDescription.setText(person.getFullName());
				mSubDescription.setText("");
				String gender = person.getGender();
				switch (gender){
					case "f":
						mImageView.setImageDrawable(mFamilyMap.getFemaleIcon());
						break;
					case "m":
						mImageView.setImageDrawable(mFamilyMap.getMaleIcon());
						break;
					default:
						mImageView.setImageDrawable(new IconDrawable(getApplicationContext(), Iconify.IconValue.fa_thumbs_down).sizeDp(60));
						Log.e("BindSearch Person", "Person gender was not m/f");
						break;
				}
			}
			else if(mItem.getClass() == Event.class){
				Event event = (Event)mItem;
				mDescription.setText(event.toString());
				Person person = mFamilyMap.getPerson(event.getPersonID());
				Filter filter = mFamilyMap.getFilterMap().get(event.getDescription());//.color(filter.getHSVColor())
				mImageView.setImageDrawable(filter.getIcon());
				if (person != null){
					mSubDescription.setText(person.getFullName());
				}
				else {
					mSubDescription.setText("");
					Log.e("bindSearch Event", "Event's person was not found: " + event.getPersonID());
				}
			}
			else
				Log.e("BindSearch class type", "item was not a person or event class. it was: " + mItem.getClass());
		}

		/**
		 * Called when a view has been clicked.
		 *
		 * @param v The view that was clicked.
		 */
		@Override
		public void onClick(View v) {
			if (mItem.getClass() == Person.class){
				Person person = (Person) mItem;
				Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
				intent.putExtra("PERSON_ID", person.getPersonID());
				startActivity(intent);
			}
			else if(mItem.getClass() == Event.class){
				// TODO launch a map activity
			}
			else
				Log.e("Search onClick", "class type: item was not a person or event class. it was: " + mItem.getClass());
		}
	}

	private class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {
		private List<Item> mItemList;

		public SearchAdapter(List<Item> itemList){
			mItemList = itemList;
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
		public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			View view = inflater.inflate(R.layout.item_search, parent, false);
			return new SearchHolder(view);
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
		public void onBindViewHolder(SearchHolder holder, int position) {
			Item item = mItemList.get(position);
			holder.BindSearch(item);
		}

		/**
		 * Returns the total number of items in the data set hold by the adapter.
		 *
		 * @return The total number of items in this adapter.
		 */
		@Override
		public int getItemCount() {
			return mItemList.size();
		}
	}
}
