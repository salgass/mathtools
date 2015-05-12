/*
 * CalculatorFragment.java
 * Copyright (C) 2014 Amin Bandali <me@aminb.org>
 *
 * MATHTools is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MATHTools is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.aminb.mathtools.app.fragment.trigonometry;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;
import 	android.util.Log;
import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.fragment.trigonometry.EditTextb;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import java.lang.Math;

public class CalculatorFragment extends Fragment {

    @InjectView(R.id.trigoLengthSeg) LinearLayout row1;
    @InjectView(R.id.trigoLengthAB) EditTextb lAB;
    @InjectView(R.id.trigoLengthBC) EditTextb lBC;
    @InjectView(R.id.trigoLengthCA) EditTextb lCA;


    @InjectView(R.id.trigoAng) LinearLayout row2;
    @InjectView(R.id.trigoAngABC) EditTextb aABC;

    @InjectView(R.id.trigoAngCAB) EditTextb aCAB;


    @InjectView(R.id.btnTrigoclear)Button btnClear;
    @InjectView(R.id.btnPropTrigo)Button btnprop;

    @InjectView(R.id.result) TextView tVResult;
    @InjectView(R.id.info) TextView info;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trigonometry_calculator, container, false);
        ButterKnife.inject(this, rootView);
        info.setText("AB=hypothenus");

        doInit();
        return rootView;
    }


    private void doInit() {


        // set up TextWatcher for all EditTexts
        lAB.addTextChangedListener(new MTWatcher(lAB));
        lBC.addTextChangedListener(new MTWatcher(lBC));
        lCA.addTextChangedListener(new MTWatcher(lCA));
        aABC.addTextChangedListener(new MTWatcher(aABC));
        aCAB.addTextChangedListener(new MTWatcher(aCAB));

    }

    @OnClick(R.id.btnTrigoclear)
    void clearAll() {
        lAB.setText("");
        lBC.setText("");
        lCA.setText("");
        aCAB.setText("");
        aABC.setText("");
        tVResult.setText("");
        lAB.reInit();
        lBC.reInit();
        lCA.reInit();
        aCAB.reInit();
        aABC.reInit();
        tVResult.setText("");
        lAB.requestFocus();
    }
    @OnClick(R.id.btnPropTrigo)
    void moreProp() {
        inputChanged2();
    }

    public void changeProposition(){

        String s2="";
        if(lAB.proposition) {
            s2 +=" AB= "+lAB.print();
        }
        if(lBC.proposition) {
            s2 +=" BC="+lBC.print();
        }
        if(lCA.proposition) {
            s2 +=" CA="+lCA.print();
        }
        if(aCAB.proposition) {

            s2 +=" CAB= "+aCAB.print();
        }
        if(aABC.proposition) {
            s2 +=" ABC="+aABC.print();
        }

        tVResult.setText(""+s2);
        Log.e("prop",s2);

    }
    public class MTWatcher implements TextWatcher {

        private EditTextb mEditText;

        public MTWatcher(EditTextb editText) {
            mEditText = editText;
        }

        public void afterTextChanged(Editable s) {
           // Log.e("aftertextchanged",s.toString());

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count >0 ) {
                mEditText.setV(Double.parseDouble(s.toString()));
            }
            else if(mEditText.b && count ==0){

                mEditText.reInit();

            }
           inputChanged();
        }




    }

    private void inputChanged2() {

        if( !(lAB.b && lBC.b && lCA.b && aABC.b && aCAB.b)) {
            boolean changement=true;



            //AB
            if(!lAB.b && !lAB.proposition && aCAB.b && lCA.b) {
                lAB.setVP(lCA.v() / Math.cos(Math.toRadians(aCAB.v())));
            }

            else if(!lAB.b && !lAB.proposition && aABC.b && lBC.b) {
                lAB.setV(lBC.v() / Math.cos(Math.toRadians(aABC.v())));
            }
            else if(!lAB.b  && !lAB.proposition && aABC.b && lCA.b) {
                lAB.setVP(lCA.v() / Math.sin(Math.toRadians(aCAB.v())));
            }
            // BC
            else if(!lBC.b  && !lBC.proposition && aCAB.b && lAB.b) {
                lBC.setVP(Math.sin(Math.toRadians(aCAB.v())) * lAB.v());
            }
            else if(!lBC.b  && !lBC.proposition  && aCAB.b && lCA.b) {
                lBC.setVP(Math.tan(Math.toRadians(aCAB.v())) * lCA.v());
            }
            else if(!lBC.b  && !lBC.proposition  && aABC.b && lAB.b) {


                lBC.setVP(Math.cos(Math.toRadians(aABC.v())) * lAB.v());
            }
            else if(!lBC.b  && !lBC.proposition  && aABC.b && lCA.b) {
                lBC.setVP(lCA.v()/Math.tan(Math.toRadians(aABC.v())) );

            }

            //CA
            else if(!lCA.b  && !lCA.proposition  && aCAB.b && lAB.b) {
                lCA.setVP(Math.cos(Math.toRadians(aCAB.v())) * lAB.v());
            }
            else if(!lCA.b   && !lCA.proposition  && aCAB.b && lBC.b) {
                lCA.setVP(lBC.v() / Math.tan(Math.toRadians(aCAB.v())));
            }
            else if(!lCA.b   && !lCA.proposition  && aABC.b && lAB.b) {
                lCA.setVP(Math.sin(Math.toRadians(aABC.v())) * lAB.v());
            }
            else if(!lCA.b   && !lCA.proposition  && aABC.b && lBC.b) {
                lCA.setVP(Math.tan(Math.toRadians(aABC.v())) * lBC.v());

            }
            //ABC
            else if(!aABC.b   && !aABC.proposition  && lAB.b && lBC.b) {
                aABC.setVP(Math.toDegrees(Math.acos(lBC.v()/lAB.v())));
            }
            else if( !aABC.b    && !aABC.proposition  && lCA.b && lAB.b) {
                aABC.setVP(Math.toDegrees(Math.asin(lCA.v() / lAB.v())));
            }
            else if( !aABC.b   && !aABC.proposition   && lCA.b && lBC.b) {
                aABC.setVP(Math.toDegrees(Math.atan(lCA.v() / lBC.v())));
            }
            //CAB
            else if(!aCAB.b    && !aCAB.proposition  && lBC.b && lAB.b) {

                aCAB.setVP(Math.toDegrees(Math.asin(lBC.v() / lAB.v())));

            }
            else if(!aCAB.b   && !aCAB.proposition && lCA.b && lAB.b) {
                aCAB.setVP(Math.toDegrees(Math.acos(lCA.v() / lAB.v())));
            }
            else if( !aCAB.b   && !aCAB.proposition && lBC.b && lCA.b) {
                aCAB.setVP(Math.toDegrees(Math.atan(lBC.v() / lCA.v())));
            }
            else{
                changement=false;
                //changeProposition();
            }

            if(changement){

                changeProposition();

            }



        }


    }

    private void inputChanged() {
        if( !(lAB.b && lBC.b && lCA.b && aABC.b && aCAB.b)) {
        boolean changement=true;



        //AB
        if(!lAB.b && aCAB.b && lCA.b) {
            lAB.setVP(lCA.v() / Math.cos(Math.toRadians(aCAB.v())));
        }

         else if(!lAB.b && aABC.b && lBC.b) {
            lAB.setV(lBC.v() / Math.cos(Math.toRadians(aABC.v())));
        }
         else if(!lAB.b && aABC.b && lCA.b) {
            lAB.setVP(lCA.v() / Math.sin(Math.toRadians(aABC.v())));
        }
       // BC
         else if(!lBC.b && aCAB.b && lAB.b) {
            lBC.setVP(Math.sin(Math.toRadians(aCAB.v())) * lAB.v());
        }
         else if(!lBC.b && aCAB.b && lCA.b) {
            lBC.setVP(Math.tan(Math.toRadians(aCAB.v())) * lCA.v());
        }
         else if(!lBC.b && aABC.b && lAB.b) {


            lBC.setVP(Math.cos(Math.toRadians(aABC.v())) * lAB.v());
        }
         else if(!lBC.b && aABC.b && lCA.b) {
            lBC.setVP(lCA.v()/Math.tan(Math.toRadians(aABC.v())) );

        }

        //CA
         else if(!lCA.b && aCAB.b && lAB.b) {
            lCA.setVP(Math.cos(Math.toRadians(aCAB.v())) * lAB.v());
        }
         else if(!lCA.b && aCAB.b && lBC.b) {
            lCA.setVP(lBC.v() / Math.tan(Math.toRadians(aCAB.v())));
        }
         else if(!lCA.b && aABC.b && lAB.b) {
            lCA.setVP(Math.sin(Math.toRadians(aABC.v())) * lAB.v());
        }
         else if(!lCA.b && aABC.b && lBC.b) {
            lCA.setVP(Math.tan(Math.toRadians(aABC.v())) * lBC.v());

        }
        //ABC
         else if(!aABC.b && lAB.b && lBC.b) {
                aABC.setVP(Math.toDegrees(Math.acos(lBC.v()/lAB.v())));
            }
        else if( !aABC.b && lCA.b && lAB.b) {
            aABC.setVP(Math.toDegrees(Math.asin(lCA.v() / lAB.v())));
        }
        else if( !aABC.b && lCA.b && lBC.b) {
            aABC.setVP(Math.toDegrees(Math.atan(lCA.v() / lBC.v())));

        }
        //CAB
         else if(!aCAB.b && lBC.b && lAB.b) {
            aCAB.setVP(Math.toDegrees(Math.asin(lBC.v() / lAB.v())));
        }
         else if(!aCAB.b && lCA.b && lAB.b) {
            aCAB.setVP(Math.toDegrees(Math.acos(lCA.v() / lAB.v())));
        }
        else if( !aCAB.b && lBC.b && lCA.b) {
            aCAB.setVP(Math.toDegrees(Math.atan(lBC.v() / lCA.v())));
        }
        else{
        changement=false;
            //changeProposition();
        }

        if(changement){
            changeProposition();

        }



        }

    }


}
