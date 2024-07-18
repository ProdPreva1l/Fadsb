package info.preva1l.fadsb.utils;

import info.preva1l.fadsb.Fadsb;

/**
 * Easy creation of Bukkit Tasks
 */
public class TaskManager {
    /**
     * Synchronous Tasks
     */
    public static class Sync {
        /**
         * Run a synchronous task once. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         */
        public static void run(Runnable runnable) {
            Fadsb.getInstance().getServer().getScheduler().runTask(Fadsb.getInstance(), runnable);
        }

        /**
         * Run a synchronous task forever with a delay between runs.
         * @param runnable The runnable, lambda supported yeh
         * @param interval Time between each run
         */
        public static void runTask(Runnable runnable, long interval) {
            Fadsb.getInstance().getServer().getScheduler().runTaskTimer(Fadsb.getInstance(), runnable, 0L, interval);
        }

        /**
         * Run a synchronous task once with a delay. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         * @param delay Time before running.
         */
        public static void runLater(Runnable runnable, long delay) {
            Fadsb.getInstance().getServer().getScheduler().runTaskLater(Fadsb.getInstance(), runnable, delay);
        }
    }

    /**
     * Asynchronous tasks
     */
    public static class Async {
        /**
         * Run an asynchronous task once. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         */
        public static void run(Runnable runnable) {
            Fadsb.getInstance().getServer().getScheduler().runTaskAsynchronously(Fadsb.getInstance(), runnable);
        }

        /**
         * Run an asynchronous task forever with a delay between runs.
         * @param runnable The runnable, lambda supported yeh
         * @param interval Time between each run
         */
        public static void runTask(Runnable runnable, long interval) {
            Fadsb.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Fadsb.getInstance(), runnable, 0L, interval);
        }

        /**
         * Run an asynchronous task once with a delay. Helpful when needing to run some sync code in an async loop
         * @param runnable The runnable, lambda supported yeh
         * @param delay Time before running.
         */
        public static void runLater(Runnable runnable, long delay) {
            Fadsb.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Fadsb.getInstance(), runnable, delay);
        }
    }
}