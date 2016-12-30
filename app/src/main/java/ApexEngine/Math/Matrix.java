package ApexEngine.Math;

import ApexEngine.Math.Matrix;

public abstract class Matrix   
{
    public abstract float[] getValues()  ;

    public Matrix()  {
    }

    public Matrix(Matrix other)  {
        if (other.getValues().length >= getValues().length)
        {
            for (int i = 0;i < getValues().length;i++)
            {
                getValues()[i] = other.getValues()[i];
            }
        }
        else if (other.getValues().length < getValues().length)
        {
            for (int i = 0;i < other.getValues().length;i++)
            {
                getValues()[i] = other.getValues()[i];
            }
        }
          
    }

}


