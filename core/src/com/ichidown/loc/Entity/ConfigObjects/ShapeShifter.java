package com.ichidown.loc.Entity.ConfigObjects;

public class ShapeShifter
{
    private float rotation,Rdegree;
    private float size,Sdegree;
    private float alpha,Adegree;
    private boolean rotateWithBody,rotateWithDirection,scalabeColision;

    // the draw size of the unit : only the aperance & not the colision box
    public int initialWidth, initialHeigth;
    public float directionalRotation;

    public ShapeShifter(int initialWidth,int initialHeigth,float adegree, float sdegree, float rdegree)
    {
        this.initialWidth=initialWidth;
        this.initialHeigth=initialHeigth;

        Adegree = adegree;
        Sdegree = sdegree;
        Rdegree = rdegree;

        rotation=0;
        size=1;
        alpha =1;
        directionalRotation =0;

        rotateWithBody=false;
        rotateWithDirection=false;
        scalabeColision=false;
    }

    public ShapeShifter()
    {
        this.initialWidth=1;
        this.initialHeigth=1;

        Adegree = 0;
        Sdegree = 0;
        Rdegree = 0;

        rotation=0;
        size=1;
        alpha =1;
        directionalRotation =0;

        rotateWithBody=false;
        rotateWithDirection=false;
        scalabeColision=false;
    }

    public void clone(ShapeShifter shapeShifter)
    {
        shapeShifter.initialWidth=initialWidth;
        shapeShifter.initialHeigth=initialHeigth;
        shapeShifter.Adegree=Adegree;
        shapeShifter.Sdegree=Sdegree;
        shapeShifter.Rdegree=Rdegree;
        shapeShifter.setRotation(rotation);
        shapeShifter.setSize(size);
        shapeShifter.setAlpha(alpha);

        shapeShifter.setRotateWithBody(rotateWithBody);
        shapeShifter.setRotateWithDirection(rotateWithDirection);
        shapeShifter.setScalabeColision(scalabeColision);
        shapeShifter.directionalRotation = directionalRotation;
    }



    public void incrementRotation()
    {
        if(Rdegree!=0)
        {
            rotation+=Rdegree;
        }

    }

    public void incrementSize()
    {
        if(Sdegree!=0)
        {
            size += Sdegree;
            if(size<0)
            {
                size = 0;
                Sdegree=0;
            }
        }
    }

    public void incrementTrensparency()
    {
        if(Adegree!=0)
        {
            alpha += Adegree;
            if(alpha<0)
            {
                alpha=0;
                Adegree=0;
            }
        }
    }

    public float getRotation() {
        return rotation;
    }

    public float getSize() {
        return size;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setRdegree(float rdegree) {
        Rdegree = rdegree;
    }

    public void setSdegree(float sdegree) {
        Sdegree = sdegree;
    }

    public void setAdegree(float adegree) {
        Adegree = adegree;
    }

    public float getRdegree() {
        return Rdegree;
    }

    public float getSdegree() {
        return Sdegree;
    }

    public float getAdegree() {
        return Adegree;
    }

    public boolean isRotateWithBody() {
        return rotateWithBody;
    }

    public void setRotateWithBody(boolean rotateWithBody) {
        this.rotateWithBody = rotateWithBody;
    }

    public boolean isRotateWithDirection() {
        return rotateWithDirection;
    }

    public void setRotateWithDirection(boolean rotateWithDirection) {
        this.rotateWithDirection = rotateWithDirection;
    }

    public boolean isScalabeColision() {
        return scalabeColision;
    }

    public void setScalabeColision(boolean scalabeColision) {
        this.scalabeColision = scalabeColision;
    }

    public void update()
    {
            incrementRotation();
            incrementSize();
            incrementTrensparency();
    }
}
