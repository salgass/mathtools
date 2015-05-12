package org.aminb.mathtools.app.fragment.trigonometry;
 import android.content.Context;
 import android.util.AttributeSet;
import android.widget.EditText;
 import 	android.util.Log;

/**
 * Created by gassama on 29/03/2015.
 */
public class EditTextb extends EditText { //Editext with boolean to know if the input is filled
    public boolean b;
    double value=0.0;
    boolean proposition;
    double vp=0.0;


    public EditTextb(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.b=false;
        proposition=false;
    }
    public EditTextb(Context context) {
        super(context);
        this.b=false;
        proposition=false;
    }

    public EditTextb(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.b=false;
        proposition=false;
    }
    public boolean know(){//return boolean input filled
        return this.b;
    }
    public void setV(double valeur){ //setvalue and b=true
        if(valeur!=Double.NaN) {
            double temp = this.value;
            this.value = round(valeur, 3);
            b = true;
        }
        proposition=false;
    }

    public void setVP(double valeur){//set proposition value
        if(valeur!=Double.NaN) {

            double temp = this.vp;
            this.vp = round(valeur, 3);
            proposition = true;
        }

    }
    public String print(){
        if(b) {
            return ("" + value);
        }
        else if(proposition){
            return ("(" + vp+")");

        }
        else{
            return("null");
        }
    }
    public double v(){
        return value;

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void reInit(){

        b=false;
        value=0.0;
        proposition=false;
        vp=0.0;
    }
}