package io.github.droidkaigi.confsched2017.view.helper;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

import timber.log.Timber;

public class BottomNavigationViewHelper {

    private static final String TAG = BottomNavigationViewHelper.class.getSimpleName();

    /**
     * In the case of BottomNavigationView has above 4 items,
     * the active tab width becomes too large. The cause is shiftingMode flag.
     * We can't access the flag and can't override the class. Then I hacked like this :(
     * http://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode
     */
    public static void disableShiftingMode(@NonNull BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // Set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Timber.tag(TAG).e(e, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Timber.tag(TAG).e(e, "Unable to change value of shift mode");
        }
    }
}
