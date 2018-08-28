package com.adinalaptuca.visitorsbook.activities.main.room.RoomsFilter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.adinalaptuca.visitorsbook.AppDelegate;
import com.adinalaptuca.visitorsbook.R;
import com.adinalaptuca.visitorsbook.activities.main.room.RoomsFragment.RoomsFragment;
import com.adinalaptuca.visitorsbook.custom.BaseFragment;
import com.adinalaptuca.visitorsbook.custom.BaseToolbarFragment;
import com.adinalaptuca.visitorsbook.model.Office;
import com.adinalaptuca.visitorsbook.model.Room;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class RoomsFilterFragment extends BaseToolbarFragment implements RoomsFilterContract.View, RoomsFragment.OnRoomSelectListener {

    private RoomsFilterContract.Presenter presenter;

    @BindView(R.id.seekBarSize)
    protected SeekBar seekBarSize;

    @BindView(R.id.viewPagerFloor)
    protected ViewPager viewPagerFloor;
    private FloorAdapter adapter;

    @BindView(R.id.viewUtilities)
    protected ViewGroup viewUtilities;

    private RoomsFragment.OnRoomSelectListener mOnRoomSelectListener;

    @Override
    public String getToolbarTitle() {
        return getString(R.string.placeholderSelectARoom);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_room_filter;
    }

    @Override
    protected void initView(View v) {
        presenter = new Presenter(this);
        presenter.fetchFloors();
        presenter.fetchUtilites();

        adapter = new FloorAdapter();
        viewPagerFloor.setAdapter(adapter);
    }

    @OnClick(R.id.btnFloorNoLeft)
    public void floorLeftClicked() {
        if (viewPagerFloor.getCurrentItem() > 0)
            viewPagerFloor.setCurrentItem(viewPagerFloor.getCurrentItem() - 1);
    }

    @OnClick(R.id.btnFloorNoRight)
    public void floorRightClicked() {
        if (viewPagerFloor.getCurrentItem() < viewPagerFloor.getAdapter().getCount() - 1)
            viewPagerFloor.setCurrentItem(viewPagerFloor.getCurrentItem() + 1);
    }

    @Override
    public void floorsFetched() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void utilitiesFetched() {
        viewUtilities.removeAllViews();

        for (String utility : presenter.getUtilites()) {
            CheckBox chkUtility = new CheckBox(getActivity());
            chkUtility.setText(utility);
            chkUtility.setPadding((int) getResources().getDimension(R.dimen.activity_vertical_marginHalf), 0, 0, 0);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin = (int) getResources().getDimension(R.dimen.activity_vertical_marginHalf);

            viewUtilities.addView(chkUtility, lp);
        }
    }

    private class FloorAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return presenter.getListFloors().size() + 1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String textFloor = getResources().getString(R.string.anyFloor);
            if (position > 0)
                textFloor = String.format(Locale.getDefault(), "%s: %d", getResources().getString(R.string.Floor), presenter.getListFloors().get(position - 1));

            TextView name = new TextView(container.getContext());
            name.setText(textFloor);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            name.setTextColor(getResources().getColor(R.color.buttonNoBackground_textColor));
            name.setGravity(Gravity.CENTER);
            container.addView(name, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return name;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView ((View) object);
        }
    }

    @OnClick(R.id.btnSearch)
    public void searchClicked() {
        List<String> utilities = new ArrayList<>();

        for (int i=0; i<viewUtilities.getChildCount(); i++) {
            CheckBox chk = (CheckBox) viewUtilities.getChildAt(i);

            if (chk.isChecked())
                utilities.add(chk.getText().toString());
        }

        presenter.searchRooms(seekBarSize.getProgress(),
                viewPagerFloor.getCurrentItem() == 0 ? null : viewPagerFloor.getCurrentItem() - 1,
                utilities);
    }

    @Override
    public void searchResultsFetched(ArrayList<Room> result) {
        RoomsFragment fragment = RoomsFragment.newInstance(result);
        fragment.setOnRoomSelectListener(this);
        addFragment(fragment);
    }

    public void setOnRoomSelectListener(RoomsFragment.OnRoomSelectListener listener) {
        this.mOnRoomSelectListener = listener;
    }

    @Override
    public void onRoomSelected(Room room) {
        mOnRoomSelectListener.onRoomSelected(room);
        getActivity().onBackPressed();
    }
}
