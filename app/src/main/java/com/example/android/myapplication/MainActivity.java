package com.example.android.myapplication;


        import android.app.Activity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;


public class MainActivity extends Activity
{

    private CanvasView customCanvas;
    public EditText quant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quant = (EditText) findViewById(R.id.quantity);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
    }

    public void clearCanvas(View v)
    {
        customCanvas.clearCanvas();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void randomPoints(View v)
    {
        double q;
        if(quant.getVisibility() == View.VISIBLE)
        {
            quant.setCursorVisible(false);
            if (isEmpty(quant))
                q = Math.random()*1000;
            else
                {
                    q = Integer.parseInt(quant.getText().toString());
                }
            customCanvas.randomPoints(q);
        }
        else
            {
                Toast.makeText(this, "Enter number of points", Toast.LENGTH_SHORT).show();
                quant.setVisibility(View.VISIBLE);
            }
    }

}