package com.example.music.ButtonView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import com.example.music.HauptMain.Music;
import com.example.music.R;

public class ViewButton extends View {
    private ShapeDrawable shapeDrawable;
    public ViewButton(Context context) {
        super(context);
        int x=10;
        int y=10;
        int width=10;
        int height =10;
        setContentDescription(context.getResources().getString(R.string.draw));
        shapeDrawable = new ShapeDrawable(new OvalShape());
        // If the color isn't set, the shape uses black as the default.
        shapeDrawable.getPaint().setColor(0xff74AC23);
        // If the bounds aren't set, the shape can't be drawn.
        shapeDrawable.setBounds(x, y, x + width, y + height);
    }
    public void onDraw(Canvas canvas) {
        shapeDrawable.draw(canvas);
    }
}
