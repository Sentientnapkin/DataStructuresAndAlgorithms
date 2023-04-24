package Algorithms.Genetic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class MaxOne {
    // Static constants that can be adjusted if we want
    // These control some high-level parameters for our simulation
    static int POPULATION_SIZE = 10;
    static int GENE_SIZE = 8;

    // Each simulation keeps track of the current population
    // and the current generation number
    Individual[] population;
    int generationNum;

    // Creates a new MaxOne simulation
    public MaxOne() {
        population = new Individual[POPULATION_SIZE];
        generationNum = 0;

        // Populate the initial population
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual();
        }
    }

    // ***********************
    // Below are the 3 methods that we will need to implement to
    // get our genetic algorithm working:
    // * selectParentsTournament
    // * singlePointCrossover
    // * mutation
    // ***********************

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

    // Runs the entirety of the genetic algorithm simulation
    public void runGA() {
        // Print the initial population
        printPopulation(0);

        // We will run generation 1 in more detail, so we can
        // test that each method is working properly

        // Use tournament selection to select parents
        Individual[] parentsOne = selectParentsTournament();
        System.out.println("Generation 1 Parents");
        printArray(parentsOne);

        // Generate the next generation using crossover
        Individual[] nextGenerationOne = singlePointCrossover(parentsOne);
        System.out.println("Generation 1 Children, crossover");
        printArray(nextGenerationOne);

        // Edit the next generation using mutation
        nextGenerationOne = mutation(nextGenerationOne);
        System.out.println("Generation 1 Children, mutation");
        printArray(nextGenerationOne);

        // Replace the current generation with the new generation;
        population = nextGenerationOne;

//        // Uncomment this when you are ready to fully test your code
        int NUM_GENERATIONS = 20;
        for (int i = 2; i < NUM_GENERATIONS; i++) {
            Individual[] parents = selectParentsTournament();
            Individual[] nextGeneration = singlePointCrossover(parents);
            nextGeneration = mutation(nextGeneration);

            population = nextGeneration;
            printPopulation(i);
        }
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

            for (int gene : genes) {
                fitness += gene;
            }

            return fitness;
        }
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Max One Simulation
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Max One Simulation");
        System.out.println("Expected:");
        System.out.println("Results may vary, but your final generation should look something like:");

        System.out.println("Generation #19:\n" +
                        "Individual 0: [1, 1, 1, 1, 1, 1, 1, 1], fitness: 8\n" +
                        "Individual 1: [1, 1, 0, 1, 0, 1, 1, 0], fitness: 5\n" +
                        "Individual 2: [1, 1, 1, 1, 1, 0, 1, 1], fitness: 7\n" +
                        "Individual 3: [1, 1, 1, 1, 1, 1, 0, 1], fitness: 7\n" +
                        "Individual 4: [1, 0, 1, 1, 1, 0, 1, 1], fitness: 6\n" +
                        "Individual 5: [1, 1, 1, 1, 1, 0, 1, 1], fitness: 7\n" +
                        "Individual 6: [1, 0, 1, 1, 1, 1, 1, 1], fitness: 7\n" +
                        "Individual 7: [1, 1, 1, 1, 1, 1, 0, 1], fitness: 7\n" +
                        "Individual 8: [1, 1, 1, 1, 1, 1, 1, 1], fitness: 8\n" +
                        "Individual 9: [1, 1, 0, 1, 1, 1, 1, 1], fitness: 7\n" +
                        "Total Fitness: 69, Max Fitness: 8");


        System.out.println("\nGot:");
        //
        MaxOne m = new MaxOne();
        m.runGA();
    }
}