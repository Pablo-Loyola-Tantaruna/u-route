package com.zea.company.route_u.adapters;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bumptech.glide.load.engine.Resource;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zea.company.route_u.R;

public class DescriptionMarks extends BottomSheetDialogFragment {


    BottomSheetDialog dialog;
    BottomSheetBehavior<View> viewBottomSheetBehavior;
    View view;




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        view = inflater.inflate(R.layout.bottom_sheet_map, container, false);
        //return super.onCreateView(inflater, container, savedInstance);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewBottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        viewBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        RelativeLayout layout = dialog.findViewById(R.id.bottomSheetLayout);
        assert layout !=null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
    }
}
