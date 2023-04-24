package Algorithms.Genetic;

import java.util.Arrays;
import java.util.Random;

public class MaxOnePartTwo {
    // Static constants that can be adjusted if we want
    // These control some high-level parameters for our simulation
    static int POPULATION_SIZE = 10;
    static int GENE_SIZE = 20;

    // Each simulation keeps track of the current population
    // and the current generation number
    Individual[] population;
    int generationNum;

    // Creates a new MaxOne simulation
    public MaxOnePartTwo() {
        population = new Individual[POPULATION_SIZE];
        generationNum = 0;

        // Populate the initial population
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual();
        }
    }

    // Returns a new array of individuals chosen to be the
    // parents of the next generation
    // We will be using tournament selection to select
    // each parent
    // * We choose two random parents, and then add the
    //   fitter parent to the parents array
    // We repeat until we have the number of parents we want
    public Individual[] selectParentsTournament() {
        Individual[] parents = new Individual[POPULATION_SIZE];

        for (int i = 0; i < parents.length; i++) {
            int rand1 = (int) (Math.random() * population.length);
            int rand2 = (int) (Math.random() * population.length);

            Individual ind1 = population[rand1];
            Individual ind2 = population[rand2];

            parents[i] = (ind1.calculateFitness() > ind2.calculateFitness()) ? ind1 : ind2;
        }

        return parents;
    }

    // Returns an array of offspring created by crossover
    // We will be creating pairs of children using single-point
    // crossover.
    // * Given some random index i, child1 will inherit genes
    //   0, 1, ... i-1 from parent1 and genes i, i+1, ... 7 from parent2
    // * child2 will inherit genes 0, 1, ... i-1 from parent2 and genes
    //   i, i+1, ... 7 from parent1
    // * We use parents 1 and 2 to generate children 1 and 2, and parents
    //   3 and 4 to generate children 3 and 4, and so on...
    public Individual[] singlePointCrossover(Individual[] parents) {
        Individual[] nextGeneration = new Individual[POPULATION_SIZE];

        for (int i = 0; i < nextGeneration.length; i += 2) {
            nextGeneration[i] = new Individual();
            nextGeneration[i + 1] = new Individual();
            int splitter = (int) (Math.random() * 8);
            for (int j = 0; j < splitter; j++) {
                nextGeneration[i].genes[j] = parents[i].genes[j];
                nextGeneration[i + 1].genes[j] = parents[i + 1].genes[j];
            }
            for (int j = splitter; j < nextGeneration[i].genes.length; j++) {
                nextGeneration[i].genes[j] = parents[i + 1].genes[j];
                nextGeneration[i + 1].genes[j] = parents[i].genes[j];
            }
        }

        return nextGeneration;
    }

    // Given an array containing the next generation,
    // iterate through all individuals and all genes,
    // and flip a bit with a 10% chance
    public Individual[] mutation(Individual[] nextGeneration) {
        for (int i = 0; i < nextGeneration.length; i++) {
            Individual ind = nextGeneration[i];
            for (int geneIndex = 0; geneIndex < ind.genes.length; geneIndex++) {
                int rand = (int) (Math.random() * 10);
                if (rand < 1) {
                    ind.genes[geneIndex] = (ind.genes[geneIndex] == 0) ? 1 : 0;
                }
            }
        }

        return nextGeneration;
    }


    // This is where you should run all of your "experiments"
    // Runs the entirety of the genetic algorithm simulation
    public void runGA() {
        generationNum = 0;
        while (getMaxFitness() < GENE_SIZE) {
            generationNum++;

            // Carry out selection
            Individual[] parents = selectParentsTournament();
//            Individual[] parents = selectParentsRoulette();

//            Individual[] nextGeneration = singlePointCrossover(parents);
//            Individual[] nextGeneration = twoPointCrossover(parents);
            Individual[] nextGeneration = uniformCrossover(parents);

            nextGeneration = mutation(nextGeneration);

            // Optional: Carry out elitism
            nextGeneration = elitism(nextGeneration);

            population = nextGeneration;
//            printPopulation(generationNum);
        }
    }

    // Returns a new array of individuals for the chosen
    // parents
    // We will be using *roulette* selection to select
    // each parent
    public Individual[] selectParentsRoulette() {
        int total = 0;
        for (Individual ind: population) {
            total += ind.calculateFitness();
        }

        Random rng = new Random();
        Individual[] parents = new Individual[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i++) {
            int x = rng.nextInt(total);

            int partialSum = 0;

            for (int j = 0; j < POPULATION_SIZE; j++) {
                Individual ind = population[j];

                partialSum += ind.calculateFitness();

                if (partialSum > x) {
                    parents[i] = ind;
                    break;
                }
            }
        }

        return parents;
    }

    public Individual[] twoPointCrossover(Individual[] parents) {
        Individual[] nextGeneration = new Individual[(POPULATION_SIZE)];
        Random rng = new Random();

        for (int i = 0; i < POPULATION_SIZE/2; i++) {
            Individual parent1 = parents[i*2];
            Individual parent2 = parents[i*2 + 1];

            int crossoverPoint1 = rng.nextInt(parent1.genes.length);
            int crossoverPoint2 = rng.nextInt(parent1.genes.length);

            if (crossoverPoint1 > crossoverPoint2) {
                int temp = crossoverPoint1;
                crossoverPoint1 = crossoverPoint2;
                crossoverPoint2 = temp;
            }

            Individual child1 = new Individual();
            Individual child2 = new Individual();

            for (int j = 0; j < crossoverPoint1; j++) {
                child1.genes[j] = parent1.genes[j];
                child2.genes[j] = parent2.genes[j];
            }

            for (int j = crossoverPoint1; j < crossoverPoint2; j++) {
                child1.genes[j] = parent2.genes[j];
                child2.genes[j] = parent1.genes[j];
            }

            for (int j = crossoverPoint2; j < parent1.genes.length; j++) {
                child1.genes[j] = parent1.genes[j];
                child2.genes[j] = parent2.genes[j];
            }

            nextGeneration[i*2] = child1;
            nextGeneration[i*2 + 1] = child2;
        }

        return nextGeneration;
    }

    public Individual[] uniformCrossover(Individual[] parents) {
        Individual[] nextGeneration = new Individual[(POPULATION_SIZE)];
        Random rng = new Random();

        for (int i = 0; i < POPULATION_SIZE/2; i++) {
            Individual parent1 = parents[i*2];
            Individual parent2 = parents[i*2 + 1];

            Individual child1 = new Individual();
            Individual child2 = new Individual();

            for (int j = 0; j < parent1.genes.length; j++) {
                if (rng.nextBoolean()) {
                    child1.genes[j] = parent1.genes[j];
                    child2.genes[j] = parent2.genes[j];
                }
                else {
                    child1.genes[j] = parent2.genes[j];
                    child2.genes[j] = parent1.genes[j];
                }
            }

            nextGeneration[i*2] = child1;
            nextGeneration[i*2 + 1] = child2;
        }

        return nextGeneration;
    }

    public Individual[] elitism(Individual[] nextGeneration) {

        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < population.length; i++) {
            if (maxFit <= population[i].calculateFitness()) {
                maxFit = population[i].calculateFitness();
                maxFitIndex = i;
            }
        }

        int secondMaxFit = Integer.MIN_VALUE;
        int secondMaxFitIndex = 0;
        for (int i = 0; i < population.length; i++) {
            if (secondMaxFit <= population[i].calculateFitness() &&
                    i != maxFitIndex) {
                secondMaxFit = population[i].calculateFitness();
                secondMaxFitIndex = i;
            }
        }

        nextGeneration[POPULATION_SIZE-2] = population[maxFitIndex];
        nextGeneration[POPULATION_SIZE-1] = population[secondMaxFitIndex];
        return nextGeneration;
    }

    // Find the max fitness in a population
    public int getMaxFitness() {
        int maxFitness = 0;

        for (int i = 0; i < population.length; i++) {
            Individual ind = population[i];
            int fitness = ind.calculateFitness();
            maxFitness = Math.max(fitness, maxFitness);
        }

        return maxFitness;
    }

    // Print out the current population/generation
    public void printPopulation(int generationNumber) {
        System.out.println("Generation #" + generationNumber + ":");

        printArray(population);
    }

    // Prints out information about an Individual array
    public static void printArray(Individual[] arr) {
        int totalFitness = 0;
        int maxFitness = 0;

        for (int i = 0; i < arr.length; i++) {
            Individual ind = arr[i];
            int fitness = ind.calculateFitness();

            System.out.println("Individual " + i + ": " + Arrays.toString(ind.genes) +
                    ", fitness: " + fitness);

            totalFitness += fitness;
            maxFitness = Math.max(fitness, maxFitness);
        }

        System.out.println("Total Fitness: " + totalFitness + ", Max Fitness: " + maxFitness);
        System.out.println();
    }

    // This class defines each individual as having
    // an array for its genes
    // The class also includes a method to calculate
    // an individuals fitness
    class Individual {
        int[] genes;

        // Create an individual with 8
        // random genes
        public Individual() {
            genes = new int[GENE_SIZE];

            Random rng = new Random();

            for (int i = 0; i < genes.length; i++) {
                genes[i] = rng.nextInt(2);
            }
        }

        // Calculate fitness by adding up all of the 1's
        public int calculateFitness() {
            int fitness = 0;

            for (int gene: genes) {
                fitness += gene;
            }

            return fitness;
        }
    }

    public static void test() {
        int total = 0;
        int TRIAL_NUM = 1000;

        for (int i = 0; i < TRIAL_NUM; i++) {
            MaxOnePartTwo m = new MaxOnePartTwo();
            m.runGA();
            total += m.generationNum;
        }

        System.out.println("Average is " + (double) total / TRIAL_NUM +
                " generations to produce a maximum fitness individual");
    }

    // You shouldn't really need to change the main method
    public static void main(String[] args) {
        test();
    }
}