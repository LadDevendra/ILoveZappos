package com.example.devendralad.ilovezappos.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devendralad.ilovezappos.Model.Product;
import com.example.devendralad.ilovezappos.R;
import com.example.devendralad.ilovezappos.databinding.ProductBinding;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    boolean cartFlag = false;
    FloatingActionButton fab;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public ProductPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductPage newInstance(String param1, String param2) {
        ProductPage fragment = new ProductPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout with data binding
        Bundle bundle = getArguments();
        Product first_prod = (Product)bundle.get("first_prod");
        ProductBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_page,
                container, false);
        View view = binding.getRoot();
        binding.setProduct(first_prod);

        //strikeThrough original cost
        TextView oPrice = (TextView) view.findViewById(R.id.oPriceTxt);
        oPrice.setPaintFlags(oPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Fab Animation :)
        fab = (FloatingActionButton) view.findViewById(R.id.fab_id);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Animate..
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
                objectAnimator.setDuration(500);
                objectAnimator.setInterpolator(new AccelerateInterpolator());
                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(fab, "scaleX", 1.2f, 1f);
                bounceAnimX.setDuration(500);
                bounceAnimX.setInterpolator(new OvershootInterpolator(2));
                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(fab, "scaleY", 1.2f, 1f);
                bounceAnimY.setDuration(500);
                bounceAnimY.setInterpolator(new OvershootInterpolator(2));
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if(!cartFlag)
                        {
                            fab.setImageResource(R.drawable.cart_filled);
                            Toast toast = Toast.makeText(getActivity(), "Added to cart",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM, 10, 10);
                            toast.show();
                            cartFlag = true;
                        }
                        else
                        {
                            fab.setImageResource(R.drawable.cart_empty);
                            Toast toast = Toast.makeText(getActivity(), "Removed from cart",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM, 10, 10);
                            toast.show();
                            cartFlag = false;
                        }
                    }
                });

                animatorSet.play(bounceAnimX).with(bounceAnimY).after(objectAnimator);
                animatorSet.start();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
