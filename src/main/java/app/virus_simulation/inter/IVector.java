package app.virus_simulation.inter;

public interface IVector {

    /**
     * @param vector
     * @return Distance between 2 vectors
     */
    double distance(IVector vector);

    /**
     * @return Length of a vector
     */
    double abs();

    /**
     * @param vector
     * @return Vector being a result of calculating the difference of 2 vectors
     */
    IVector getVecDiff(IVector vector);

    /**
     * @return [0] = x, [1] = y
     */
    double[] getComponents();



}
