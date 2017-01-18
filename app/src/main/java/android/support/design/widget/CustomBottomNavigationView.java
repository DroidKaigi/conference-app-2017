package android.support.design.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * https://github.com/yanzm/BottomNavigationSample/blob/master/app/src/main/java/android/support/design/widget/CustomBottomNavigationView.java
 */
public class CustomBottomNavigationView extends BottomNavigationView {

    private int checkedPosition = 0;

    private OnNavigationItemSelectedListener listener;

    public CustomBottomNavigationView(Context context) {
        super(context);
        setupListener();
    }

    private void setupListener() {
        super.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean result = listener != null && listener.onNavigationItemSelected(item);
                if (result) {
                    final int position = findPosition(item);
                    if (position >= 0) {
                        checkedPosition = position;
                    }
                }
                return result;
            }
        });
    }

    private int findPosition(MenuItem menuItem) {
        final Menu menu = getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            final MenuItem item = menu.getItem(i);
            if (item.getItemId() == menuItem.getItemId()) {
                return i;
            }
        }
        return -1;
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupListener();
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupListener();
    }

    @Override
    public void setOnNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void inflateMenu(int resId) {
        super.inflateMenu(resId);
        final Menu menu = getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            final MenuItem item = menu.getItem(i);
            if (item.isChecked()) {
                checkedPosition = i;
                break;
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.checkedPosition = checkedPosition;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(state);
        selectItemByPosition(ss.checkedPosition);
    }

    private void selectItemByPosition(int position) {
        final MenuItem item = getMenu().getItem(position);
        if (item != null) {
            selectItem(item.getItemId());
        }
    }

    public void selectItem(int menuId) {
        final View view = findViewById(menuId);
        if (view != null) {
            view.performClick();
        }
    }

    static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        int checkedPosition;

        /**
         * Constructor called from {@link CustomBottomNavigationView#onSaveInstanceState()}
         */
        SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            checkedPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(checkedPosition);
        }

        @Override
        public String toString() {
            return "CustomBottomNavigationView.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " checkedPosition=" + checkedPosition + "}";
        }
    }
}