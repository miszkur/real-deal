package com.example.misradbru.realdeal.foundproducts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.FoundProduct;
import com.example.misradbru.realdeal.data.ProductRepositoryImpl;

import java.util.ArrayList;

public class FoundProductsFragment extends Fragment implements FoundProductsContract.View {

    String mSearchPhrase;
    String mUid;
    ProgressBar mProgressBar;
    ListView mFoundProductsList;

    private FoundProductsContract.UserActionsListener mActionsListener;

    public FoundProductsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String searchProductId, String uid) {
        FoundProductsFragment fragment = new FoundProductsFragment();
        Bundle args = new Bundle();
        args.putString(FoundProductsActivity.SEARCH_PRODUCT_ID, searchProductId);
        args.putString(FoundProductsActivity.UID, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchPhrase = getArguments().getString(FoundProductsActivity.SEARCH_PRODUCT_ID);
            mUid = getArguments().getString(FoundProductsActivity.UID);
        }

        mActionsListener = new FoundProductsPresenter(new ProductRepositoryImpl(), this);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_found_products, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.found_products_progressbar);
        mFoundProductsList = view.findViewById(R.id.found_products_listview);
    }

    @Override
    public void onResume() {
        super.onResume();
        FoundProductsAdapter foundProductsAdapter =
                new FoundProductsAdapter(getContext(), new ArrayList<FoundProduct>());
        mFoundProductsList.setAdapter(foundProductsAdapter);
        mActionsListener.showFoundProducts(mSearchPhrase, foundProductsAdapter);

    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
            mFoundProductsList.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mFoundProductsList.setVisibility(View.VISIBLE);
        }
    }
}
