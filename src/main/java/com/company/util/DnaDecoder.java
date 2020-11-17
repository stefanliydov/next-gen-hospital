package com.company.util;

import org.springframework.stereotype.Component;

@Component
public class DnaDecoder {

    /**
     * Calculates the likelihood of human to have genetic disorder based on the DNA.
     *
     * @param dna – human dna to be checked
     * @return a number between 0 and 1 with the probability for disorder
     */
    public double getGeneticDisorderProbability(final String dna) {
        final int middleIndex = dna.length() / 2;

        int largestOccurrence = 0;

        for (int endIndex = dna.length() - 1; endIndex >= middleIndex; endIndex--) {
            int startIndex = 0;
            int tempOccurrence = 0;
            for (int cycleIndex = endIndex; cycleIndex >= middleIndex; cycleIndex--) {
                if (dna.charAt(startIndex) == dna.charAt(cycleIndex)) {
                    tempOccurrence++;
                    startIndex++;
                } else {
                    break;
                }
            }
            if (largestOccurrence < tempOccurrence) {
                largestOccurrence = tempOccurrence;
            }
        }

        final double result = Math.round((largestOccurrence / (double) dna.length()) * 10.0) / 10.0;
        if (result <= 0.2) {
            return 0.0;
        } else if (largestOccurrence >= middleIndex) {
            return 1.0;
        }

        return result;
    }
}
