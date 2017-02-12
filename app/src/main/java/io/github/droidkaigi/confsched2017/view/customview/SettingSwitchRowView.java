package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.ViewSettingSwitchRowBinding;

public class SettingSwitchRowView extends RelativeLayout {

    private static final String TAG = SettingSwitchRowView.class.getSimpleName();

    private ViewSettingSwitchRowBinding binding;

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public SettingSwitchRowView(Context context) {
        this(context, null);
    }

    public SettingSwitchRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingSwitchRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            inflate(context, R.layout.view_setting_switch_row, this);
            return;
        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_setting_switch_row, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingSwitchRow);

        String title = a.getString(R.styleable.SettingSwitchRow_settingTitle);
        String description = a.getString(R.styleable.SettingSwitchRow_settingDescription);

        binding.settingTitle.setText(title);
        binding.settingDescription.setText(description);

        binding.getRoot().setOnClickListener(v -> toggle());
        binding.settingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
            }
        });

        a.recycle();
    }

    private void toggle() {
        boolean isChecked = binding.settingSwitch.isChecked();
        binding.settingSwitch.setChecked(!isChecked);
    }

    public void setChecked(boolean checked) {
        binding.settingSwitch.setChecked(checked);
    }

    public void init(boolean defaultValue, CompoundButton.OnCheckedChangeListener listener) {
        setDefault(defaultValue);
        setOnCheckedChangeListener(listener);
    }

    public void setDefault(boolean defaultValue) {
        binding.settingSwitch.setChecked(defaultValue);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        binding.getRoot().setEnabled(enabled);
        binding.settingSwitch.setEnabled(enabled);
        if (enabled) {
            binding.settingTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            binding.settingDescription.setTextColor(ContextCompat.getColor(getContext(), R.color.grey600));
        } else {
            int disabledTextColor = ContextCompat.getColor(getContext(), R.color.black_alpha_30);
            binding.settingTitle.setTextColor(disabledTextColor);
            binding.settingDescription.setTextColor(disabledTextColor);
        }
    }
}
