public class GradeBook {

    private double[] scores;
    private int scoresSize;

    /**
     * Constructs a gradebook with no scores and a given capacity.
     * @param capacity the maximum number of scores in this gradebook
     */
    public GradeBook(int capacity) {
        scores = new double[capacity];
        scoresSize = 0;
    }

    /**
     * Adds a score to this gradebook.
     * @param score the score to add
     * @return true if the score was added, false if the gradebook is full
     */
    public boolean addScore(double score) {
        if (scoresSize < scores.length) {
            scores[scoresSize] = score;
            scoresSize++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Computes the sum of the scores in this gradebook.
     * @return the sum of the scores
     */
    public double sum() {
        double total = 0;
        for (int i = 0; i < scoresSize; i++) {
            total = total + scores[i];
        }
        return total;
    }

    /**
     * Gets the minimum score in this gradebook.
     * @return the minimum score, or 0 if there are no scores.
     */
    public double minimum() {
        if (scoresSize == 0) return 0;

        double smallest = scores[0];
        for (int i = 1; i < scoresSize; i++) {
            if (scores[i] < smallest) {
                smallest = scores[i];
            }
        }
        return smallest;
    }

    /**
     * Gets the final score for this gradebook.
     * @return the sum of the scores, with the lowest score dropped if
     * there are at least two scores, or 0 if there are no scores.
     */
    public double finalScore() {
        if (scoresSize == 0)
            return 0;
        else if (scoresSize == 1)
            return scores[0];
        else
            return sum() - minimum();
    }

    /**
     * Returns the current number of scores stored.
     * @return scoresSize
     */
    public int getScoreSize() {
        return scoresSize;
    }

    /**
     * Returns a string containing each score followed by a space,
     * from index 0 up to scoresSize - 1.
     * Example: "50.0 75.0 "
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < scoresSize; i++) {
            result += scores[i] + " ";
        }
        return result;
    }
}