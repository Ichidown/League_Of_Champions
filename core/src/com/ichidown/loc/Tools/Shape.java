package com.ichidown.loc.Tools;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Shape
{
    public static CircleShape newCircleShape(float radius)
    {
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        return circle;
    }

    public static PolygonShape newPolygonShape(Vector2[] vertecies)
    {
        PolygonShape polygon = new PolygonShape();
        polygon.set(vertecies);
        return polygon;
    }

    public static EdgeShape newLineShape(float x1, float y1, float x2, float y2)
    {
        EdgeShape edge = new EdgeShape();
        edge.set(new Vector2(x1, y1), new Vector2(x2, y2));
        return edge;
    }
}
