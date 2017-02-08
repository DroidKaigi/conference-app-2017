package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ViewInfoRowBinding;
import io.github.droidkaigi.confsched2017.view.helper.DataBindingHelper;

public class InfoRowView extends RelativeLayout {

    private static final String TAG = InfoRowView.class.getSimpleName();

    private ViewInfoRowBinding binding;

    public InfoRowView(Context context) {
        this(context, null);
    }

    public InfoRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            inflate(context, R.layout.view_info_row, this);
            return;
        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_info_row, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfoRow);

        String title = a.getString(R.styleable.InfoRow_infoTitle);
        String description = a.getString(R.styleable.InfoRow_infoDescription);

        binding.txtInfoTitle.setText(title);
        binding.txtInfoDescription.setText(description);
        DataBindingHelper.setTextLinkify(binding.txtInfoDescription, true);

        a.recycle();
    }

    public void setTitle(String title) {
        binding.txtInfoTitle.setText(title);
    }

    public void setDescription(String description) {
        binding.txtInfoDescription.setText(description);
    }

}
