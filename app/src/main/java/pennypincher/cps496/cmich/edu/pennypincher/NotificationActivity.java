package pennypincher.cps496.cmich.edu.pennypincher;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NotificationActivity extends AppCompatActivity {
    TextView SpentTV;
    TextView BudgetTV;
    TextView OverUnder;
    TextView OverUnderLabel;
    ImageView PassFailpng;
    ConstraintLayout statusBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        SpentTV = findViewById(R.id.Spent);
        BudgetTV = findViewById(R.id.Budget);
        OverUnder = findViewById(R.id.OverUnder);
        OverUnderLabel = findViewById(R.id.OverUnderLabel);
        PassFailpng = findViewById(R.id.IVGif);
        statusBack = findViewById(R.id.StatusBack);
        float total = getIntent().getExtras().getFloat("total_spent");
        float Budget = getIntent().getExtras().getFloat("Budget");
        Log.d("total_spentServ",Double.toString(total));
        Log.d("total_spentServ1",Double.toString(Budget));
        if((Budget - total) > 0){
            OverUnderLabel.setTextColor(Color.GREEN);
            OverUnderLabel.setText("Under Budget");
            OverUnder.setTextColor(Color.GREEN);
            statusBack.setBackgroundColor(Color.GREEN);
            PassFailpng.setImageDrawable(getDrawable(R.mipmap.thumbsup));
        }else if((Budget - total) < 0){
            OverUnderLabel.setTextColor(Color.RED);
            OverUnderLabel.setText("Over Budget");
            OverUnder.setTextColor(Color.RED);
            statusBack.setBackgroundColor(Color.RED);
            PassFailpng.setImageDrawable(getDrawable(R.mipmap.burningmoney));
        }else{
            OverUnderLabel.setTextColor(Color.YELLOW);
            OverUnderLabel.setText("On Budget");
            OverUnder.setTextColor(Color.YELLOW);
            statusBack.setBackgroundColor(Color.YELLOW);
            PassFailpng.setImageDrawable(getDrawable(R.mipmap.lifesupport));
        }
        SpentTV.setText(String.format("%.02f",total));
        BudgetTV.setText(String.format("%.02f",Budget));
        startCountAnimation(Budget,total);
    }

    public void Back2Home(View v){
        Intent intent = new Intent(NotificationActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void startCountAnimation(float Budget,float total){
        ValueAnimator animator = ValueAnimator.ofFloat(0,(Budget - total));
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                OverUnder.setText(String.format("%.02f",valueAnimator.getAnimatedValue()));
            }
        });
        animator.start();
    }
}
